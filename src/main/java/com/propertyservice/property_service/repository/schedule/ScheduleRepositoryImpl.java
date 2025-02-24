package com.propertyservice.property_service.repository.schedule;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.schedule.enums.ScheduleType;
import com.propertyservice.property_service.dto.schedule.QScheduleDto;
import com.propertyservice.property_service.dto.schedule.ScheduleDto;
import com.propertyservice.property_service.dto.schedule.ScheduleEventDto;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.utils.DateTimeUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.propertyservice.property_service.domain.client.QClient.client;
import static com.propertyservice.property_service.domain.office.QOffice.office;
import static com.propertyservice.property_service.domain.office.QOfficeUser.officeUser;
import static com.propertyservice.property_service.domain.schedule.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScheduleDto> searchScheduleList(String selectedDate, Long officeId) {
        return queryFactory
                .select(
                        new QScheduleDto(
                                schedule.id,
                                officeUser.name,
                                client.name,
                                schedule.date,
                                getScheduleTypeLabel(schedule.scheduleType),
                                schedule.remark,
                                schedule.isCompleted
                        )
                )
                .from(schedule)
                .leftJoin(client).on(client.id.eq(schedule.client.id))
                .leftJoin(officeUser).on(officeUser.id.eq(client.id))
                .where(
                        officeUser.office.id.eq(officeId),
                        schedule.date.between(DateTimeUtil.parseYYYYMMDD(selectedDate).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).atStartOfDay(), DateTimeUtil.parseYYYYMMDD(selectedDate).orElseThrow(
                                () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                        ).plusDays(1).atStartOfDay())
                )
                .fetch();
    }

    @Override
    public List<ScheduleEventDto> searchEventsByMonth(YearMonth yearMonth, Long officeId) {
        return queryFactory
                .select(
                        schedule.date,
                        getScheduleTypeLabel(schedule.scheduleType)
                )
                .from(schedule)
                .leftJoin(officeUser).on(officeUser.id.eq(schedule.picUser.id))
                .leftJoin(office).on(office.id.eq(officeUser.office.id))
                .where(
                        office.id.eq(officeId),
                        schedule.date.between(
                                yearMonth.atDay(1).atTime(0, 0, 0),
                                yearMonth.atEndOfMonth().atTime(23, 59, 59)
                        )
                )
                .fetch().stream()
                .filter(row -> row.get(schedule.date) != null) // ✅ Null 값 필터링 추가
                .collect(Collectors.groupingBy(
                        row -> Objects.requireNonNull(row.get(schedule.date)).toLocalDate(), // ✅ Null 체크 추가하여 IDE 경고 제거
                        Collectors.mapping(row -> row.get(1, String.class), Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(entry -> new ScheduleEventDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

    }

    private StringExpression getScheduleTypeLabel(EnumPath<ScheduleType> scheduleType) {
        return new CaseBuilder()
                .when(scheduleType.eq(ScheduleType.CONSULTING)).then(ScheduleType.CONSULTING.getLabel())
                .when(scheduleType.eq(ScheduleType.CONTRACT_SCHEDULED)).then(ScheduleType.CONTRACT_SCHEDULED.getLabel())
                .when(scheduleType.eq(ScheduleType.CONTRACT_COMPLETED)).then(ScheduleType.CONTRACT_COMPLETED.getLabel())
                .otherwise("알 수 없음"); // ✅ 예외 처리
    }
}
