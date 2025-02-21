package com.propertyservice.property_service.dto.client;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ShowingPropertyDto {
    private Long showingPropertyId;
    private Long propertyId;

    private String propertyType;
    private String propertyAddress;

    private String transactionType;
    private BigDecimal price1;
    private BigDecimal price2;

    @QueryProjection
    public ShowingPropertyDto(Long showingPropertyId, Long propertyId, String propertyType, String propertyAddress, String transactionType, BigDecimal price1, BigDecimal price2) {
        this.showingPropertyId = showingPropertyId;
        this.propertyId = propertyId;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.transactionType = transactionType;
        this.price1 = price1;
        this.price2 = price2;
    }
}
