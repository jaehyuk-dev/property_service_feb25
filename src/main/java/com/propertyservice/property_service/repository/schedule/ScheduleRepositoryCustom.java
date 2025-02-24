package com.propertyservice.property_service.repository.schedule;

import com.propertyservice.property_service.dto.schedule.ScheduleDto;
import com.propertyservice.property_service.dto.schedule.ScheduleEventDto;

import java.time.YearMonth;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<ScheduleDto> searchScheduleList(String selectedDate, Long officeId);
    List<ScheduleEventDto> searchEventsByMonth(YearMonth yearMonth, Long officeId);
}
