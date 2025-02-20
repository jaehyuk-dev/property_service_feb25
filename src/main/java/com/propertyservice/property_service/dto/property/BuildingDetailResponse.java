package com.propertyservice.property_service.dto.property;

import com.propertyservice.property_service.dto.common.ImageDto;
import com.propertyservice.property_service.dto.common.RemarkDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BuildingDetailResponse {
    private long buildingId;
    private String buildingName;
    private String buildingZoneCode;
    private String buildingAddress;
    private String buildingJibunAddress;
    private int buildingCompletedYear;
    private String buildingTypeName;
    private String buildingFloorCount;
    private int buildingParkingAreaCount;
    private int buildingElevatorCount;
    private String buildingMainDoorDirection;
    private String buildingCommonPassword;
    private boolean buildingHasIllegal;

    private List<RemarkDto> buildingRemarkList;

    private List<ImageDto> buildingImageList;

    @Builder
    public BuildingDetailResponse(long buildingId, String buildingName, String buildingZoneCode, String buildingAddress, String buildingJibunAddress, int buildingCompletedYear, String buildingTypeName, String buildingFloorCount, int buildingParkingAreaCount, int buildingElevatorCount, String buildingMainDoorDirection, String buildingCommonPassword, boolean buildingHasIllegal, List<RemarkDto> buildingRemarkList, List<ImageDto> buildingImageList) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.buildingZoneCode = buildingZoneCode;
        this.buildingAddress = buildingAddress;
        this.buildingJibunAddress = buildingJibunAddress;
        this.buildingCompletedYear = buildingCompletedYear;
        this.buildingTypeName = buildingTypeName;
        this.buildingFloorCount = buildingFloorCount;
        this.buildingParkingAreaCount = buildingParkingAreaCount;
        this.buildingElevatorCount = buildingElevatorCount;
        this.buildingMainDoorDirection = buildingMainDoorDirection;
        this.buildingCommonPassword = buildingCommonPassword;
        this.buildingHasIllegal = buildingHasIllegal;
        this.buildingRemarkList = buildingRemarkList;
        this.buildingImageList = buildingImageList;
    }
}
