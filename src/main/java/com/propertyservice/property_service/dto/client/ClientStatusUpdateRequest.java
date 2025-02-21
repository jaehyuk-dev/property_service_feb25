package com.propertyservice.property_service.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClientStatusUpdateRequest {
    @NotNull
    @Schema(description = "고객 id", example = "3")
    private Long clientId;
    @NotNull
    @Schema(description = "변경할 고객 상태 (상담 중: 21, 계약 예정: 22, 입주 예정: 23, 계약 완료: 24)", example = "22")
    private int newClientStatusCode;

    @Schema(description = "계약 예정 시 선택된 보여줄 매물 id")
    private Long showingPropertyId;

    @Schema(description = "계약 완료 시 중개 수수료")
    private BigDecimal commissionFee;
    @Schema(description = "계약 완료 시 입주 일")
    private String moveInDate;
    @Schema(description = "계약 완료 시 퇴실 일")
    private String moveOutDate;
}
