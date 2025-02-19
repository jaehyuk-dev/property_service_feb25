package com.propertyservice.property_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCondition {
    @Schema(description = "검색 타입", example = "전체")
    private String searchType;  // 검색 타입
    @Schema(description = "검색 키워드", example = "")
    private String keyword;     // 검색 키워드
}
