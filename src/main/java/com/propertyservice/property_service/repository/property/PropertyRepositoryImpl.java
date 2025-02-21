package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.property.enums.PropertyStatus;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.dto.property.PropertyRecapDto;
import com.propertyservice.property_service.dto.property.QPropertyRecapDto;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.propertyservice.property_service.domain.property.QBuilding.building;
import static com.propertyservice.property_service.domain.property.QProperty.property;
import static com.propertyservice.property_service.domain.property.QPropertyTransactionType.propertyTransactionType;

@Repository
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PropertyRecapDto> searchPropertyRecapList(SearchCondition condition, Long officeId) {
        return         queryFactory
                .select(
                        new QPropertyRecapDto(
                                property.id,
                                getPropertyStatusLabel(property.propertyStatus),
                                property.propertyType,
                                building.zoneCode
                                        .prepend("(")           // "(" + zoneCode
                                        .concat(") ")          // + ") "
                                        .concat(building.address)
                                        .concat(" ")
                                        .concat(property.roomNumber),
                                getTransactionTypeLabel(propertyTransactionType.transactionType),
                                getTransactionTypeCode(propertyTransactionType.transactionType),
                                getPropertyPrice(propertyTransactionType.transactionType, propertyTransactionType.price1, propertyTransactionType.price2) // ✅ 가격 추가
                        )
                )
                .from(property)
                .leftJoin(building).on(property.building.eq(building))
                .leftJoin(propertyTransactionType).on(property.eq(propertyTransactionType.property))
                .where(
                        property.building.pocOffice.id.eq(officeId),
                        property.propertyStatus.eq(PropertyStatus.OCCUPIED).not(),
                        searchByType(condition.getSearchType(), condition.getKeyword())
                )
                .fetch();
    }

    private BooleanExpression searchByType(String searchType, String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null; // 검색어가 없으면 필터 미적용
        }

        return switch (searchType) {
            case "전체" -> property.picUser.name.containsIgnoreCase(keyword)
                    .or(property.ownerName.containsIgnoreCase(keyword))
                    .or(building.zoneCode.containsIgnoreCase(keyword))
                    .or(building.address.containsIgnoreCase(keyword))
                    .or(building.jibunAddress.containsIgnoreCase(keyword));
            case "담당자" -> property.picUser.name.containsIgnoreCase(keyword);
            case "임대인" -> property.ownerName.containsIgnoreCase(keyword);
            case "주소" -> building.zoneCode.containsIgnoreCase(keyword)
                    .or(building.address.containsIgnoreCase(keyword))
                    .or(building.jibunAddress.containsIgnoreCase(keyword));
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

    private NumberExpression<Integer> getTransactionTypeCode(EnumPath<TransactionType> transactionType) {
        return new CaseBuilder()
                .when(transactionType.eq(TransactionType.MONTHLY)).then(TransactionType.MONTHLY.getValue())
                .when(transactionType.eq(TransactionType.JEONSE)).then(TransactionType.JEONSE.getValue())
                .when(transactionType.eq(TransactionType.SHORTTERM)).then(TransactionType.SHORTTERM.getValue())
                .otherwise(60); // ✅ 예외 처리
    }

    private StringExpression getPropertyStatusLabel(EnumPath<PropertyStatus> propertyStatus) {
        return new CaseBuilder()
                .when(propertyStatus.eq(PropertyStatus.VACANT)).then(PropertyStatus.VACANT.getLabel())
                .when(propertyStatus.eq(PropertyStatus.CONTRACTING)).then(PropertyStatus.CONTRACTING.getLabel())
                .when(propertyStatus.eq(PropertyStatus.CONTRACTING)).then(PropertyStatus.CONTRACTING.getLabel())
                .otherwise("알 수 없음"); // ✅ 예외 처리
    }

    private StringExpression getPropertyPrice(EnumPath<TransactionType> transactionType, NumberPath<BigDecimal> price1, NumberPath<BigDecimal> price2) {
        return new CaseBuilder()
                .when(transactionType.eq(TransactionType.MONTHLY))
                .then(formatPrice(price1).concat(" / ").concat(formatPrice(price2))) // 월세: 보증금 / 월세
                .when(transactionType.eq(TransactionType.SHORTTERM))
                .then(formatPrice(price1).concat(" / ").concat(formatPrice(price2))) // 단기: 보증금 / 월세
                .when(transactionType.eq(TransactionType.JEONSE))
                .then(formatPrice(price1)) // 전세: 보증금만 표시
                .otherwise(""); // 예외 처리
    }

    /**
     * 가격을 "억"과 "만" 단위로 변환하는 함수 (BigDecimal 지원)
     */
    private StringExpression formatPrice(NumberPath<BigDecimal> price) {
        return Expressions.stringTemplate(
                "CASE " +
                        "WHEN {0} >= 100000000 THEN " +
                        "CONCAT(" +
                        "FLOOR({0} / 100000000), '억', " +
                        "CASE WHEN FLOOR(({0} - (FLOOR({0} / 100000000) * 100000000)) / 10000) > 0 " +
                        "THEN CONCAT(' ', FLOOR(({0} - (FLOOR({0} / 100000000) * 100000000)) / 10000), '만') " +
                        "ELSE '' END, " +
                        "CASE WHEN FLOOR({0} - (FLOOR({0} / 10000) * 10000)) > 0 " +
                        "THEN CONCAT(' ', FLOOR({0} - (FLOOR({0} / 10000) * 10000)), '원') " +
                        "ELSE ' 원' END" +
                        ") " +
                        "WHEN {0} >= 10000 THEN " +
                        "CONCAT(" +
                        "FLOOR({0} / 10000), '만', " +
                        "CASE WHEN FLOOR({0} - (FLOOR({0} / 10000) * 10000)) > 0 " +
                        "THEN CONCAT(' ', FLOOR({0} - (FLOOR({0} / 10000) * 10000)), '원') " +
                        "ELSE ' 원' END" +
                        ") " +
                        "ELSE CONCAT(FLOOR({0}), '원') " +
                        "END",
                price
        );
    }

}
