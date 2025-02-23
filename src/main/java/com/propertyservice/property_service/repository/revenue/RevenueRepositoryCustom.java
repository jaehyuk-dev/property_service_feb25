package com.propertyservice.property_service.repository.revenue;

import com.propertyservice.property_service.dto.revenue.RevenueDto;
import com.propertyservice.property_service.dto.revenue.RevenueInfoDto;
import com.propertyservice.property_service.dto.revenue.RevenueSearchCondition;

import java.util.List;

public interface RevenueRepositoryCustom {
    List<RevenueDto> searchRevenueList(RevenueSearchCondition condition);

    RevenueInfoDto searchRevenueInfo(RevenueSearchCondition condition);
}
