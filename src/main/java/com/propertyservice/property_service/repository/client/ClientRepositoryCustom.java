package com.propertyservice.property_service.repository.client;

import com.propertyservice.property_service.dto.client.ClientSummaryDto;
import com.propertyservice.property_service.dto.common.SearchCondition;

import java.util.List;

public interface ClientRepositoryCustom {
    List<ClientSummaryDto> searchClientSummaryList(SearchCondition condition, Long officeId);
}
