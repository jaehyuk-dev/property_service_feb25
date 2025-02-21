package com.propertyservice.property_service.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowingPropertyRegisterRequest {
    @NotNull
    @Schema(description = "고객 id", example = "3")
    private Long clientId;
    @NotNull
    @Schema(description = "매물 id", example = "5")
    private Long propertyId;
}
