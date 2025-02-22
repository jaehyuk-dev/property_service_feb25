package com.propertyservice.property_service.dto.property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyRemarkRequest {
    @NotNull
    @Schema(description = "매물 id", example = "1")
    private Long propertyId;
    @NotNull
    @NotBlank
    @Schema(description = "매물 특이사항", example = "추가된 특이사항 입니다.")
    private String propertyRemark;
}
