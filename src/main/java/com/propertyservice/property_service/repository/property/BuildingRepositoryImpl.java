package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.dto.property.BuildingSummaryDto;
import com.propertyservice.property_service.dto.property.QBuildingSummaryDto;
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
}
