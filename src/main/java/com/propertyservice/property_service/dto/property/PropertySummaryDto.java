package com.propertyservice.property_service.dto.property;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class PropertySummaryDto {
    private Long propertyId;
    private String propertyStatus;
    private String propertyType;
    private String propertyAddress;
    private Double propertyExclusiveArea;

    @Setter
    private List<PropertyTransactionDto> propertyTransactionList;

    private String propertyMainPhotoUrl;



    @QueryProjection
    public PropertySummaryDto(Long propertyId, String propertyStatus, String propertyType, String propertyAddress, Double propertyExclusiveArea, String propertyMainPhotoUrl) {
        this.propertyId = propertyId;
        this.propertyStatus = propertyStatus;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.propertyExclusiveArea = propertyExclusiveArea;
        this.propertyMainPhotoUrl = propertyMainPhotoUrl;
    }
}
