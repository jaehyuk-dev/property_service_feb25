package com.propertyservice.property_service.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRemarkRequest {
    @NotNull
    @Schema(description = "고객 id", example = "3")
    private Long clientId;
    @NotNull
    @NotBlank
    @Schema(description = "고객 특이사항", example = "추가된 특이사항 입니다.")
    private String clientRemark;
}
