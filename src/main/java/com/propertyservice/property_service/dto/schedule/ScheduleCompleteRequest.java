package com.propertyservice.property_service.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCompleteRequest {
    @NotNull
    @Schema(description = "일정 id", example = "1")
    private long scheduleId;
    @NotNull
    @Schema(description = "완료 여부", example = "true")
    private boolean complete;
}

