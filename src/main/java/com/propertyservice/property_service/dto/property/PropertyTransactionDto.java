package com.propertyservice.property_service.dto.property;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PropertyTransactionDto {
    private String transactionType;
    private String price;

    @Builder
    public PropertyTransactionDto(String transactionType, String price) {
        this.transactionType = transactionType;
        this.price = price;
    }
}
