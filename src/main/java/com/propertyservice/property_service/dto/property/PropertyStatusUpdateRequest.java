package com.propertyservice.property_service.dto.property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyStatusUpdateRequest {
    @NotNull
    @Schema(description = "매물 id", example = "5")
    private Long propertyId;
    @NotNull
    @Schema(description = "매물 상태 코드 (공실: 101, 계약 중: 102, 거주 중: 103", example = "102")
    private int newPropertyStatusCode;

    @Schema(description = "계약 완료 시 입주 일", example = "20240201")
    private String moveInDate;
    @Schema(description = "계약 완료 시 퇴실 일", example = "20241231")
    private String moveOutDate;
}
