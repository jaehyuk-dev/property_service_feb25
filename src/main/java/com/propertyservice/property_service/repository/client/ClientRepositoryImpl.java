package com.propertyservice.property_service.repository.client;

import com.propertyservice.property_service.domain.client.enums.ClientStatus;
import com.propertyservice.property_service.dto.client.ClientSummaryDto;
import com.propertyservice.property_service.dto.client.QClientSummaryDto;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.propertyservice.property_service.domain.client.QClient.client;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ClientSummaryDto> searchClientSummaryList(SearchCondition condition, Long officeId) {
        return queryFactory
                .select(new QClientSummaryDto(
                        client.id,       // clientId
                        client.name,               // clientName
                        getClientStatusLabel(client.status),
                        client.phoneNumber,        // clientPhoneNumber
                        client.picUser.name,           // picManager (담당자)
                        client.source,             // clientSource
                        client.expectedMoveInDate          // moveInDate
                ))
                .from(client)
                .where(
                        client.pocOffice.id.eq(officeId),
                        searchByType(condition.getSearchType(), condition.getKeyword())
                )
                .fetch();
    }

    private BooleanExpression searchByType(String searchType, String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null; // 검색어가 없으면 필터 미적용
        }

        return switch (searchType) {
            case "전체" -> client.picUser.name.containsIgnoreCase(keyword)
                    .or(client.name.containsIgnoreCase(keyword))
                    .or(client.phoneNumber.containsIgnoreCase(keyword));
            case "담당자" -> client.picUser.name.containsIgnoreCase(keyword);
            case "고객" -> client.name.containsIgnoreCase(keyword);
            case "고객 전화번호" -> client.phoneNumber.containsIgnoreCase(keyword);
            default -> Expressions.booleanTemplate("false"); // ❗잘못된 경우 필터링
        };
    }

    private StringExpression getClientStatusLabel(EnumPath<ClientStatus> status) {
        return new CaseBuilder()
                .when(status.eq(ClientStatus.CONSULTING)).then(ClientStatus.CONSULTING.getLabel())
                .when(status.eq(ClientStatus.CONTRACT_SCHEDULED)).then(ClientStatus.CONTRACT_SCHEDULED.getLabel())
                .when(status.eq(ClientStatus.MOVING_IN)).then(ClientStatus.MOVING_IN.getLabel())
                .when(status.eq(ClientStatus.CONTRACT_COMPLETED)).then(ClientStatus.CONTRACT_COMPLETED.getLabel())
                .otherwise("알 수 없음"); // ✅ 예외 처리
    }
}
