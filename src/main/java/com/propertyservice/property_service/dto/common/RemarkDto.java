package com.propertyservice.property_service.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RemarkDto {
    private long remarkId;
    private String remark;
    private String createdBy;
    private LocalDate createdAt;

    @Builder
    public RemarkDto(LocalDate createdAt, String createdBy, String remark, long remarkId) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.remark = remark;
        this.remarkId = remarkId;
    }
}
