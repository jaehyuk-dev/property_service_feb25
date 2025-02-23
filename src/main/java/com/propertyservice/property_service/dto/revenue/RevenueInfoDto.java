package com.propertyservice.property_service.dto.revenue;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RevenueInfoDto {
    private int revenueCount;
    private String totalCommissionFee;
    private List<RevenueDto> revenueDtoList;

    @Builder
    public RevenueInfoDto(int revenueCount, String totalCommissionFee, List<RevenueDto> revenueDtoList) {
        this.revenueCount = revenueCount;
        this.totalCommissionFee = totalCommissionFee;
        this.revenueDtoList = revenueDtoList;
    }
}
