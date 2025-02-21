package com.propertyservice.property_service.repository.client;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.property.enums.PropertyStatus;
import com.propertyservice.property_service.dto.client.QShowingPropertyDto;
import com.propertyservice.property_service.dto.client.ShowingPropertyDto;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.propertyservice.property_service.domain.client.QShowingProperty.showingProperty;
import static com.propertyservice.property_service.domain.property.QBuilding.building;
import static com.propertyservice.property_service.domain.property.QProperty.property;
import static com.propertyservice.property_service.domain.property.QPropertyTransactionType.propertyTransactionType;

@Repository
@RequiredArgsConstructor
public class ShowingPropertyRepositoryImpl implements ShowingPropertyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ShowingPropertyDto> searchShowingPropertyByClientId(Long clientId) {
        return queryFactory
                .select(
                        new QShowingPropertyDto(
                                showingProperty.id,
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
                .from(showingProperty)
                .leftJoin(property).on(showingProperty.property.eq(property))
                .leftJoin(building).on(property.building.eq(building))
                .leftJoin(propertyTransactionType).on(property.eq(propertyTransactionType.property))
                .where(
                        showingProperty.client.id.eq(clientId),
                        property.propertyStatus.eq(PropertyStatus.OCCUPIED).not(),
                        showingProperty.property.propertyStatus.eq(PropertyStatus.OCCUPIED).not()
                )
                .fetch();
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
