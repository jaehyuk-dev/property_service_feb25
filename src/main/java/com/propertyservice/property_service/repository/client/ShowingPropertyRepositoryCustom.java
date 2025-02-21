package com.propertyservice.property_service.repository.client;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.dto.client.ShowingPropertyDto;

import java.util.List;

public interface ShowingPropertyRepositoryCustom {
    List<ShowingPropertyDto> searchShowingPropertyByClient(Client client);
}
