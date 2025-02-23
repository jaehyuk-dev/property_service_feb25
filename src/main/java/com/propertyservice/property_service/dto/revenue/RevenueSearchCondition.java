package com.propertyservice.property_service.dto.revenue;

import com.propertyservice.property_service.dto.common.SearchCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevenueSearchCondition extends SearchCondition {
    @NotNull
    @NotBlank
    @Schema(description = "검색 시작일", example = "20250101")
    private String startDate;
    @NotNull
    @NotBlank
    @Schema(description = "검색 종료일", example = "20250201")
    private String endDate;

    @NotNull
    @Schema(description = "거래 유형 코드 (전체: 60, 월세: 61, 전세: 62, 단기: 63)", example = "60")
    private int transactionTypeCode;
    @NotNull
    @NotBlank
    @Schema(description = "고객 유입 경로", example = "전체")
    private String clientSource;
}
