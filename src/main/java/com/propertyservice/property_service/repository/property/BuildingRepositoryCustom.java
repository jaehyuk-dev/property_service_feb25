package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.dto.property.BuildingSummaryDto;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<BuildingSummaryDto> searchBuildingSummaryList(String searchWord, Long officeId);
}
