package com.propertyservice.property_service.dto.property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PropertyImageRequest {
    @NotNull
    @Schema(description = "매물 id", example = "5")
    private Long propertyId;

    @Schema(description = "매물 대표 이미지 인데스 번호", example = "1")
    private int propertyMainPhotoIndex;
    @Schema(description = "매물 이미지 url 목록")
    private List<String> photoUrlList;
}
