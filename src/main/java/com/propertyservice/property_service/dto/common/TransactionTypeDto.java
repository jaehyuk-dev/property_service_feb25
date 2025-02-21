package com.propertyservice.property_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionTypeDto {
    @NotNull
    @Schema(description = "거래 유형 코드 (월세: 61, 전세:62, 단기: 64", example = "61")
    private int transactionCode;
    @NotNull
    @Schema(description = "가격 1 (보증금 or 전세금)", example = "100000000")
    private BigDecimal price1;
    @Schema(description = "가격 2 (월세)", example = "500000")
    private BigDecimal price2;
}
