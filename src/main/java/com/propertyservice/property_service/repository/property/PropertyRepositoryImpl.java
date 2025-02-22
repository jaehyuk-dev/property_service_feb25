package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.property.enums.PropertyStatus;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.dto.property.PropertyRecapDto;
import com.propertyservice.property_service.dto.property.PropertySummaryDto;
import com.propertyservice.property_service.dto.property.QPropertyRecapDto;
import com.propertyservice.property_service.dto.property.QPropertySummaryDto;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.propertyservice.property_service.domain.property.QBuilding.building;
import static com.propertyservice.property_service.domain.property.QProperty.property;
import static com.propertyservice.property_service.domain.property.QPropertyPhoto.propertyPhoto;
import static com.propertyservice.property_service.domain.property.QPropertyTransactionType.propertyTransactionType;

@Repository
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PropertyRecapDto> searchPropertyRecapList(SearchCondition condition, Long officeId) {
        return queryFactory
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
                        building.pocOffice.id.eq(officeId),
                        searchByType(condition.getSearchType(), condition.getKeyword())
                )
                .fetch();
    }

    @Override
    public List<PropertySummaryDto> searchPropertySummaryList(SearchCondition condition, Long officeId) {
         return queryFactory
                 .select(
                         new QPropertySummaryDto(
                                 property.id,
                                 getPropertyStatusLabel(property.propertyStatus),
                                 property.propertyType,
                                 building.zoneCode
                                         .prepend("(")           // "(" + zoneCode
                                         .concat(") ")          // + ") "
                                         .concat(building.address)
                                         .concat(" ")
                                         .concat(property.roomNumber),
                                 property.exclusiveArea,
                                 propertyPhoto.photoUrl
                         )

                 )
                 .from(property)
                 .leftJoin(building).on(property.building.eq(building))
                 .leftJoin(propertyTransactionType).on(property.eq(propertyTransactionType.property))
                 .leftJoin(propertyPhoto).on(property.eq(propertyPhoto.property).and(propertyPhoto.isMain.isTrue()))
                 .where(
                         building.pocOffice.id.eq(officeId),
                         searchByType2(condition.getSearchType(), condition.getKeyword())
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

    private BooleanExpression searchByType2(String searchType, String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null; // 검색어가 없으면 필터 미적용
        }

        return switch (searchType) {
            case "전체" -> property.ownerName.containsIgnoreCase(keyword)
                    .or(property.ownerPhoneNumber.containsIgnoreCase(keyword))
                    .or(building.zoneCode.containsIgnoreCase(keyword))
                    .or(building.address.containsIgnoreCase(keyword))
                    .or(building.jibunAddress.containsIgnoreCase(keyword));
            case "임대인" -> property.ownerName.containsIgnoreCase(keyword);
            case "임대인 전화번호" -> property.ownerPhoneNumber.containsIgnoreCase(keyword);
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
