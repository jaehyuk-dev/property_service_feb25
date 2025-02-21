package com.propertyservice.property_service.dto.schedule;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleDto {
    private Long scheduleId;
    private String picUserName;
    private String clientName;
    private String scheduleType;
    private String scheduleRemark;
    private boolean isCompleted;

    @Builder
    public ScheduleDto(Long scheduleId, String picUserName, String clientName, String scheduleType, String scheduleRemark, boolean isCompleted) {
        this.scheduleId = scheduleId;
        this.picUserName = picUserName;
        this.clientName = clientName;
        this.scheduleType = scheduleType;
        this.scheduleRemark = scheduleRemark;
        this.isCompleted = isCompleted;
    }
}
