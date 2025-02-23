package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.client.enums.ClientStatus;
import com.propertyservice.property_service.domain.property.enums.PropertyStatus;
import com.propertyservice.property_service.domain.revenue.Revenue;
import com.propertyservice.property_service.dto.revenue.RevenueInfoDto;
import com.propertyservice.property_service.dto.revenue.RevenueSearchCondition;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.revenue.RevenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RevenueService {
    private final RevenueRepository revenueRepository;

    public RevenueInfoDto searchRevenueList(RevenueSearchCondition condition) {
        return revenueRepository.searchRevenueInfo(condition);
    }

    @Transactional
    public void deleteRevenue(Long revenueId) {
        // 매출 id 찾음
        Revenue revenue = revenueRepository.findById(revenueId).orElseThrow(
                () -> new BusinessException(ErrorCode.REVENUE_NOT_FOUND)
        );
        // 그거와 연관된 매물의 상태를 공실로 바꿈
        // 입주일 퇴실일, 입주 가능일(오늘로..)을 바꿈
        revenue.getProperty().updatePropertyStatusContractCompleted(
                null,
                null,
                LocalDate.now(),
                PropertyStatus.VACANT
        );
        // 그거와 연관된 고객의 상태를 상담 중으로 바꿈
        revenue.getClient().updateClientStatus(ClientStatus.CONSULTING);
        revenue.getClient().updateClientSelectedShowingProperty(null);
        // 해당 매출 삭제
        revenueRepository.delete(revenue);
    }
}
