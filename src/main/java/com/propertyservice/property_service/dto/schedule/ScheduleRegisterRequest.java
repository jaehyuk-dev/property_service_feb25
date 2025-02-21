package com.propertyservice.property_service.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRegisterRequest {

    @NotNull
    @Schema(description = "일정을 등록할 고객 id", example = "3")
    private Long scheduleClientId;
    @NotNull
    @Schema(description = "일정 일시 (YYYYMMDDTTMM)", example = "202501011355")
    private String scheduleDateTime;
    @NotNull
    @Schema(description = "일정 타입 (상담: 41, 계약: 42, 입주: 43", example = "41")
    private int scheduleTypeCode;
    @Schema(description = "일정 특이사항", example = "일정 특이사항입니다.")
    private String scheduleRemark;
}
