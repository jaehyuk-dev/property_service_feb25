package com.propertyservice.property_service.repository.revenue;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.dto.revenue.QRevenueDto;
import com.propertyservice.property_service.dto.revenue.RevenueDto;
import com.propertyservice.property_service.dto.revenue.RevenueInfoDto;
import com.propertyservice.property_service.dto.revenue.RevenueSearchCondition;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.utils.DateTimeUtil;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.propertyservice.property_service.domain.client.QClient.client;
import static com.propertyservice.property_service.domain.office.QOffice.office;
import static com.propertyservice.property_service.domain.property.QBuilding.building;
import static com.propertyservice.property_service.domain.property.QProperty.property;
import static com.propertyservice.property_service.domain.property.QPropertyTransactionType.propertyTransactionType;
import static com.propertyservice.property_service.domain.revenue.QRevenue.revenue;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class RevenueRepositoryImpl implements RevenueRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RevenueDto> searchRevenueList(RevenueSearchCondition condition) {
        return queryFactory
                .select(
                        new QRevenueDto(
                                revenue.id,
                                client.picUser.name,
                                client.name,
                                getTransactionTypeLabel(propertyTransactionType.transactionType),
                                getPropertyPrice(propertyTransactionType.transactionType, propertyTransactionType.price1, propertyTransactionType.price2), // ✅ 가격 추가
                                building.zoneCode
                                        .prepend("(")           // "(" + zoneCode
                                        .concat(") ")          // + ") "
                                        .concat(building.address)
                                        .concat(" ")
                                        .concat(property.roomNumber),
                                property.ownerName,
                                formatPrice(revenue.commissionFee)
                                        .concat(" 원"),
                                property.moveInDate,
                                property.moveOutDate
                        )
                )
                .from(office)
                .leftJoin(revenue).on(office.eq(revenue.pocOffice))
                .leftJoin(property).on(property.id.eq(revenue.property.id))
                .leftJoin(building).on(building.eq(property.building))
                .leftJoin(propertyTransactionType).on(propertyTransactionType.property.eq(property))
                .leftJoin(client).on(client.eq(revenue.client))
                .where(
                        searchByType(condition.getSearchType(), condition.getKeyword()),
                        revenue.createdDate.between(DateTimeUtil.parseYYYYMMDD(condition.getStartDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).atStartOfDay(), DateTimeUtil.parseYYYYMMDD(condition.getEndDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).plusDays(1).atStartOfDay()),
                        transactionTypeEquals(condition.getTransactionTypeCode()),
                        clientSourceContains(condition.getClientSource())
                )
                .fetch();
    }

    @Override
    public RevenueInfoDto searchRevenueInfo(RevenueSearchCondition condition) {
        List<RevenueDto> revenueList = queryFactory
                .select(
                        new QRevenueDto(
                                revenue.id,
                                client.picUser.name,
                                client.name,
                                getTransactionTypeLabel(propertyTransactionType.transactionType),
                                getPropertyPrice(propertyTransactionType.transactionType, propertyTransactionType.price1, propertyTransactionType.price2), // ✅ 가격 추가
                                building.zoneCode
                                        .prepend("(")           // "(" + zoneCode
                                        .concat(") ")          // + ") "
                                        .concat(building.address)
                                        .concat(" ")
                                        .concat(property.roomNumber),
                                property.ownerName,
                                formatPrice(revenue.commissionFee)
                                        .concat(" 원"),
                                property.moveInDate,
                                property.moveOutDate
                        )
                )
                .from(office)
                .leftJoin(revenue).on(office.eq(revenue.pocOffice))
                .leftJoin(property).on(property.id.eq(revenue.property.id))
                .leftJoin(building).on(building.eq(property.building))
                .leftJoin(propertyTransactionType).on(propertyTransactionType.property.eq(property))
                .leftJoin(client).on(client.eq(revenue.client))
                .where(
                        searchByType(condition.getSearchType(), condition.getKeyword()),
                        revenue.createdDate.between(DateTimeUtil.parseYYYYMMDD(condition.getStartDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).atStartOfDay(), DateTimeUtil.parseYYYYMMDD(condition.getEndDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).plusDays(1).atStartOfDay()),
                        transactionTypeEquals(condition.getTransactionTypeCode()),
                        clientSourceContains(condition.getClientSource())
                )
                .fetch();

        String totalCommissionFee = queryFactory
                .select(
                        Expressions.stringTemplate(
                                "CASE " +
                                        "WHEN {0} >= 100000000 THEN " +
                                        "CONCAT(" +
                                        "CAST(FLOOR({0} / 100000000) AS text), '억', " +
                                        "CASE WHEN FLOOR(({0} - (FLOOR({0} / 100000000) * 100000000)) / 10000) > 0 " +
                                        "THEN CONCAT(' ', CAST(FLOOR(({0} - (FLOOR({0} / 100000000) * 100000000)) / 10000) AS text), '만') " +
                                        "ELSE '' END, ' 원'" +
                                        ") " +
                                        "WHEN {0} >= 10000 THEN " +
                                        "CONCAT(" +
                                        "CAST(FLOOR({0} / 10000) AS text), '만 원'" +
                                        ") " +
                                        "ELSE CONCAT(CAST(FLOOR({0}) AS text), ' 원') " +
                                        "END",
                                revenue.commissionFee.sum()
                        )
                )
                .from(revenue)
                .where(
                        searchByType(condition.getSearchType(), condition.getKeyword()),
                        revenue.createdDate.between(DateTimeUtil.parseYYYYMMDD(condition.getStartDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).atStartOfDay(), DateTimeUtil.parseYYYYMMDD(condition.getEndDate()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).plusDays(1).atStartOfDay()),
                        transactionTypeEquals(condition.getTransactionTypeCode()),
                        clientSourceContains(condition.getClientSource())
                )
                .fetchOne();

        if (totalCommissionFee == null) {
            totalCommissionFee = "0 원";
        }

        return RevenueInfoDto.builder()
                .revenueCount(revenueList.size())
                .totalCommissionFee(totalCommissionFee)
                .revenueDtoList(revenueList)
                .build();
    }

    private BooleanExpression transactionTypeEquals(int transactionTypeCode) {
        if (transactionTypeCode == 60) { // 전체
            return null;
        }

        try {
            TransactionType transactionType = TransactionType.fromValue(transactionTypeCode);
            return propertyTransactionType.transactionType.eq(transactionType);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_TRANSACTION_TYPE);
        }
    }

    private BooleanExpression clientSourceContains(String clientSource) {
        return clientSource.equals("전체") ? null : client.source.containsIgnoreCase(clientSource);
    }

    private BooleanExpression searchByType(String searchType, String keyword) {
        if (!hasText(keyword)) {
            return null; // 검색어가 없으면 필터 미적용
        }

        return switch (searchType) {
            case "전체" -> property.picUser.name.containsIgnoreCase(keyword)
                    .or(property.ownerName.containsIgnoreCase(keyword))
                    .or(building.zoneCode.containsIgnoreCase(keyword))
                    .or(building.address.containsIgnoreCase(keyword))
                    .or(building.jibunAddress.containsIgnoreCase(keyword))
                    .or(client.name.containsIgnoreCase(keyword));
            case "담당자" -> property.picUser.name.containsIgnoreCase(keyword);
            case "임대인" -> property.ownerName.containsIgnoreCase(keyword);
            case "주소" -> building.zoneCode.containsIgnoreCase(keyword)
                    .or(building.address.containsIgnoreCase(keyword))
                    .or(building.jibunAddress.containsIgnoreCase(keyword));
            case "고객" -> client.name.containsIgnoreCase(keyword);
            default -> Expressions.booleanTemplate("false"); // ❗잘못된 경우 필터링
        };
    }

    private StringExpression getTransactionTypeLabel(EnumPath<TransactionType> transactionType) {
        return new CaseBuilder()
                .when(transactionType.eq(TransactionType.MONTHLY)).then(TransactionType.MONTHLY.getLabel())
                .when(transactionType.eq(TransactionType.JEONSE)).then(TransactionType.JEONSE.getLabel())
                .when(transactionType.eq(TransactionType.SHORTTERM)).then(TransactionType.SHORTTERM.getLabel())
                .otherwise("알 수 없음"); // ✅ 예외 처리
    }

    private StringExpression getPropertyPrice(EnumPath<TransactionType> transactionType, NumberPath<BigDecimal> price1, NumberPath<BigDecimal> price2) {
        return new CaseBuilder()
                .when(transactionType.eq(TransactionType.MONTHLY))
                .then(formatPrice(price1).concat(" / ").concat(formatPrice(price2)).concat(" 원")) // 월세: 보증금 / 월세 원
                .when(transactionType.eq(TransactionType.SHORTTERM))
                .then(formatPrice(price1).concat(" / ").concat(formatPrice(price2)).concat(" 원")) // 단기: 보증금 / 월세 원
                .when(transactionType.eq(TransactionType.JEONSE))
                .then(formatPrice(price1).concat(" 원")) // 전세: 전세금 원
                .otherwise(""); // 예외 처리
    }

    /**
     * 가격을 "억"과 "만" 단위로 변환하는 함수 (BigDecimal 지원)
     * 원 단위는 표시하지 않음
     */
    private StringExpression formatPrice(NumberPath<BigDecimal> price) {
        return Expressions.stringTemplate(
                "CASE " +
                        "WHEN {0} >= 100000000 THEN " +
                        "CONCAT(" +
                        "CAST(FLOOR({0} / 100000000) AS text), '억', " +
                        "CASE WHEN FLOOR(({0} - (FLOOR({0} / 100000000) * 100000000)) / 10000) > 0 " +
                        "THEN CONCAT(' ', CAST(FLOOR(({0} - (FLOOR({0} / 100000000) * 100000000)) / 10000) AS text), '만') " +
                        "ELSE '' END" +
                        ") " +
                        "WHEN {0} >= 10000 THEN " +
                        "CONCAT(" +
                        "CAST(FLOOR({0} / 10000) AS text), '만'" +
                        ") " +
                        "ELSE CAST(FLOOR({0}) AS text) " +
                        "END",
                price
        );
    }

}
