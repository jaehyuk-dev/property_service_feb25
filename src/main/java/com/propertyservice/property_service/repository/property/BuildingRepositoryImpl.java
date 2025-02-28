package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.property.enums.BuildingType;
import com.propertyservice.property_service.dto.property.BuildingSummaryDto;
import com.propertyservice.property_service.dto.property.QBuildingSummaryDto;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.propertyservice.property_service.domain.property.QBuilding.building;
import static com.propertyservice.property_service.domain.property.QBuildingPhoto.buildingPhoto;

@Repository
@RequiredArgsConstructor
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BuildingSummaryDto> searchBuildingSummaryList(String searchWord, Long officeId) {
        return queryFactory
                .select(
                        new QBuildingSummaryDto(
                                building.id,
                                building.name,
                                building.zoneCode
                                        .prepend("(")           // "(" + zoneCode
                                        .concat(") ")          // + ") "
                                        .concat(building.address),  // + building.address
                                getTransactionTypeLabel(building.buildingType),
                                buildingPhoto.photoUrl
                        )
                )
                .from(building)
                .leftJoin(buildingPhoto).on(building.eq(buildingPhoto.building).and(buildingPhoto.isMain.isTrue()))
                .where(
                        building.pocOffice.id.eq(officeId),
                        building.address.containsIgnoreCase(searchWord)
                                .or(building.jibunAddress.containsIgnoreCase(searchWord))
                                .or(building.zoneCode.containsIgnoreCase(searchWord))
                )
                .fetch();
    }

    private StringExpression getTransactionTypeLabel(EnumPath<BuildingType> buildingType) {
        return new CaseBuilder()
                .when(buildingType.eq(BuildingType.RESIDENTIAL)).then(BuildingType.RESIDENTIAL.getLabel())
                .when(buildingType.eq(BuildingType.NON_RESIDENTIAL)).then(BuildingType.NON_RESIDENTIAL.getLabel())
                .otherwise("알 수 없음"); // ✅ 예외 처리
    }

}
