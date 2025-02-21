package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.domain.schedule.Schedule;
import com.propertyservice.property_service.domain.schedule.enums.ScheduleType;
import com.propertyservice.property_service.dto.schedule.ScheduleCompleteRequest;
import com.propertyservice.property_service.dto.schedule.ScheduleRegisterRequest;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.client.ClientRepository;
import com.propertyservice.property_service.repository.schedule.ScheduleRepository;
import com.propertyservice.property_service.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final OfficeService officeService;
    private final ClientRepository clientRepository;

    @Transactional
    public void registerSchedule(ScheduleRegisterRequest request) {
        Client client = clientRepository.findById(request.getScheduleClientId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CLIENT_NOT_FOUND)
        );
        scheduleRepository.save(
                Schedule.builder()
                        .picUser(officeService.getCurrentUserEntity())
                        .date(DateTimeUtil.parseYYYYMMDDTTMM(request.getScheduleDateTime()).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ))
                        .client(client)
                        .scheduleType(ScheduleType.fromValue(request.getScheduleTypeCode()))
                        .remark(request.getScheduleRemark())
                        .isCompleted(false)
                        .build()
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    @Transactional
    public void updateScheduleCompleted(ScheduleCompleteRequest request) {
        scheduleRepository.findById(request.getScheduleId()).orElseThrow(
                () -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND)
        ).updateScheduleCompleted(request.isComplete());
    }
}
