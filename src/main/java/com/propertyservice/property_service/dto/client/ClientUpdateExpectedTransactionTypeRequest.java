package com.propertyservice.property_service.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientUpdateExpectedTransactionTypeRequest {
    @NotNull
    @Schema(description = "고객 Id", example = "1")
    private Long clientId;
    @NotNull
    @Schema(description = "고객 희망 거래 유형(61: 월세, 62: 전세, 64: 단기", example = "[61, 64]")
    private List<Integer> expectedTransactionTypeCodeList;
}
