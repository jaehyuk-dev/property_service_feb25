package com.propertyservice.property_service.dto.property;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyRegisterRequest {
    @Schema(description = "건물 id", example = "1")
    private Long buildingId;

    private String ownerName;
    private String ownerPhoneNumber;
    private String ownerRelation;

    private String roomNumber;
    private String propertyType;

    private int propertyStatusCode;
}
