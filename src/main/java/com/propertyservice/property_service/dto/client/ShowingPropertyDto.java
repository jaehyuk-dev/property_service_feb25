package com.propertyservice.property_service.dto.client;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;


@Getter
public class ShowingPropertyDto {
    private Long showingPropertyId;
    private Long propertyId;
    private String propertyStatus;
    private String propertyType;
    private String propertyAddress;
    private String propertyTransactionType;
    private int propertyTransactionTypeCode;
    private String propertyPrice;

    @QueryProjection
    public ShowingPropertyDto(Long showingPropertyId, Long propertyId, String propertyStatus, String propertyType, String propertyAddress, String propertyTransactionType, int propertyTransactionTypeCode, String propertyPrice) {
        this.showingPropertyId = showingPropertyId;
        this.propertyId = propertyId;
        this.propertyStatus = propertyStatus;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.propertyTransactionType = propertyTransactionType;
        this.propertyTransactionTypeCode = propertyTransactionTypeCode;
        this.propertyPrice = propertyPrice;
    }
}
