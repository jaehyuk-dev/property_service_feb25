package com.propertyservice.property_service.dto.client;

import com.propertyservice.property_service.dto.common.RemarkDto;
import com.propertyservice.property_service.dto.schedule.ScheduleDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ClientDetailResponse {
    private Long clientId;
    private String clientPicUser;
    private String clientStatus;
    private String clientName;
    private String clientPhoneNumber;
    private String clientGender;
    private String clientSource;
    private String clientType;
    private LocalDate clientExpectedMoveInDate;
    private List<String> clientExpectedTransactionTypeList;

    private List<ScheduleDto> scheduleList;
    private List<ShowingPropertyDto> showingPropertyList;
    private List<RemarkDto> clientRemarkList;

    @Builder
    public ClientDetailResponse(Long clientId, String clientPicUser, String clientStatus, String clientName, String clientPhoneNumber, String clientGender, String clientSource, String clientType, LocalDate clientExpectedMoveInDate, List<String> clientExpectedTransactionTypeList, List<ScheduleDto> scheduleList, List<ShowingPropertyDto> showingPropertyList, List<RemarkDto> clientRemarkList) {
        this.clientId = clientId;
        this.clientPicUser = clientPicUser;
        this.clientStatus = clientStatus;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientGender = clientGender;
        this.clientSource = clientSource;
        this.clientType = clientType;
        this.clientExpectedMoveInDate = clientExpectedMoveInDate;
        this.clientExpectedTransactionTypeList = clientExpectedTransactionTypeList;
        this.scheduleList = scheduleList;
        this.showingPropertyList = showingPropertyList;
        this.clientRemarkList = clientRemarkList;
    }
}
