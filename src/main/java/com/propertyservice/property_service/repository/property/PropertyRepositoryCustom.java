package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.dto.property.PropertyRecapDto;
import com.propertyservice.property_service.dto.property.PropertySummaryDto;

import java.util.List;

public interface PropertyRepositoryCustom {
    List<PropertyRecapDto> searchPropertyRecapList(SearchCondition condition, Long officeId);
    List<PropertySummaryDto> searchPropertySummaryList(SearchCondition condition, Long officeId);
}
