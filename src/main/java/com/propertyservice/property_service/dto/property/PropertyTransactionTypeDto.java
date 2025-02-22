package com.propertyservice.property_service.dto.property;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PropertyTransactionTypeDto {
    private String propertyTransactionType;
    private BigDecimal price1;
    private BigDecimal price2;

    @Builder
    public PropertyTransactionTypeDto(String propertyTransactionType, BigDecimal price1, BigDecimal price2) {
        this.propertyTransactionType = propertyTransactionType;
        this.price1 = price1;
        this.price2 = price2;
    }
}
