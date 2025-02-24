package com.propertyservice.property_service.dto.schedule;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleEventDto {
    private LocalDate date;
    private List<String> types; // 일정 유형 목록 (상담, 계약, 입주 등)

    @QueryProjection
    public ScheduleEventDto(LocalDate date, List<String> types) {
        this.date = date;
        this.types = types;
    }
}
