package com.propertyservice.property_service.dto.property;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyRecapDto {
    private Long propertyId;
    private String propertyStatus;
    private String propertyType;
    private String propertyAddress;
    private String propertyTransactionType;
    private int propertyTransactionTypeCode;
    private String propertyPrice;

    @QueryProjection
    public PropertyRecapDto(Long propertyId, String propertyStatus, String propertyType, String propertyAddress, String propertyTransactionType, int propertyTransactionTypeCode, String propertyPrice) {
        this.propertyId = propertyId;
        this.propertyStatus = propertyStatus;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.propertyTransactionType = propertyTransactionType;
        this.propertyTransactionTypeCode = propertyTransactionTypeCode;
        this.propertyPrice = propertyPrice;
    }
}
