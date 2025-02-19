package com.propertyservice.property_service.dto.client;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ClientSummaryDto {
    private Long clientId;
    private String clientName;
    private String clientStatus;
    private String clientPhoneNumber;
    private String picUser;
    private String clientSource;
    private LocalDate clientExpectedMoveInDate;

    @QueryProjection
    public ClientSummaryDto(Long clientId, String clientName, String clientStatus, String clientPhoneNumber, String picUser, String clientSource, LocalDate clientExpectedMoveInDate) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientStatus = clientStatus;
        this.clientPhoneNumber = clientPhoneNumber;
        this.picUser = picUser;
        this.clientSource = clientSource;
        this.clientExpectedMoveInDate = clientExpectedMoveInDate;
    }
}
