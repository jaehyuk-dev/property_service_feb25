package com.propertyservice.property_service.repository.client;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.dto.client.QShowingPropertyDto;
import com.propertyservice.property_service.dto.client.ShowingPropertyDto;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                                showingProperty.property.id,
                                showingProperty.property.propertyType,
                                building.zoneCode
                                        .prepend("(")           // "(" + zoneCode
                                        .concat(") ")          // + ") "
                                        .concat(building.address)
                                        .concat(" ")
                                        .concat(property.roomNumber),
                                getTransactionTypeLabel(propertyTransactionType.transactionType),
                                propertyTransactionType.price1,
                                propertyTransactionType.price2
                        )
                )
                .from(showingProperty)
                .leftJoin(property).on(showingProperty.property.eq(property))
                .leftJoin(building).on(property.building.eq(building))
                .leftJoin(propertyTransactionType).on(property.eq(propertyTransactionType.property))
                .where(
                        showingProperty.client.id.eq(clientId)
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
}
