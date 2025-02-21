package com.propertyservice.property_service.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class DateTimeUtil {
    private static final DateTimeFormatter FORMAT_YEAR_MONTH = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter FORMAT_YEAR_MONTH_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMAT_YEAR_MONTH_DAY_HOUR_MIN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final DateTimeFormatter FORMAT_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMAT_YYYYMMDDTTMM = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    // YYYY-MM 포맷
    public String formatYearMonth(LocalDateTime dateTime) {
        return dateTime.format(FORMAT_YEAR_MONTH);
    }

    // YYYY-MM-DD 포맷
    public String formatYearMonthDay(LocalDateTime dateTime) {
        return dateTime.format(FORMAT_YEAR_MONTH_DAY);
    }

    // YYYY-MM-DD HH:MM 포맷
    public String formatYearMonthDayHourMin(LocalDateTime dateTime) {
        return dateTime.format(FORMAT_YEAR_MONTH_DAY_HOUR_MIN);
    }

    // YYYYMMDD -> LocalDate 변환
    public Optional<LocalDate> parseYYYYMMDD(String dateStr) {
        try {
            return Optional.of(LocalDate.parse(dateStr, FORMAT_YYYYMMDD));
        } catch (Exception e) {
            System.err.println("Invalid date format: " + dateStr);
            return Optional.empty();
        }
    }

    // YYYYMMDD -> LocalDateTime 변환
    public static Optional<LocalDateTime> parseYYYYMMDDTTMM(String dateTimeStr) {
        try {
            return Optional.of(LocalDateTime.parse(dateTimeStr, FORMAT_YYYYMMDDTTMM));
        } catch (Exception e) {
            System.err.println("Invalid date format: " + dateTimeStr);
            return Optional.empty();
        }
    }
}
