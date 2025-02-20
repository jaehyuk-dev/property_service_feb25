package com.propertyservice.property_service.dto.property;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BuildingSummaryDto {
    private long buildingId;
    private String buildingName;
    private String buildingAddress;
    private String buildingType;

    @QueryProjection
    public BuildingSummaryDto(long buildingId, String buildingName, String buildingAddress, String buildingType) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.buildingAddress = buildingAddress;
        this.buildingType = buildingType;
    }
}
