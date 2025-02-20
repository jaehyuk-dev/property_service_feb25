package com.propertyservice.property_service.dto.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageDto {
    private final Long imageId;
    private final boolean isMain;
    private final String imageUrl;

    @Builder
    public ImageDto(Long imageId, boolean isMain, String imageUrl) {
        this.imageId = imageId;
        this.isMain = isMain;
        this.imageUrl = imageUrl;
    }
}
