package com.propertyservice.property_service.dto.property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingUpdateRequest {

    @Schema(description = "건물 id", example = "1")
    private Long buildingId;

    @Schema(description = "건물 이름", example = "빌딩1")
    private String buildingName;

    @NotNull
    @NotBlank
    @Schema(description = "건물 우편번호", example = "12345")
    private String buildingZoneCode;
    @NotNull
    @NotBlank
    @Schema(description = "건물 주소", example = "경기 성남시 분당구 판교역로 166")
    private String buildingAddress;
    @NotNull
    @NotBlank
    @Schema(description = "건물 지번 주소", example = "경기 성남시 분당구 백현동 532")
    private String buildingJibunAddress;
    @NotNull
    @Schema(description = "준공 연도", example = "2024")
    private int buildingCompletedYear;
    @NotNull
    @Schema(description = "건물 용도 (주거용: 51, 비 주거용: 52)", example = "51")
    private int buildingTypeCode;

    @NotNull
    @NotBlank
    @Schema(description = "건물 층 수", example = "지하4층 / 13층")
    private String buildingFloorCount;
    @NotNull
    @Schema(description = "건물 주차 대수", example = "14")
    private int buildingParkingAreaCount;
    @NotNull
    @Schema(description = "건물 엘레베이터 개수", example = "1")
    private int buildingElevatorCount;
    @NotNull
    @NotBlank
    @Schema(description = "건물 출입문 방향", example = "대로변쪽")
    private String buildingMainDoorDirection;
    @NotNull
    @NotBlank
    @Schema(description = "건물 공동 현관문 비밀번호", example = "없음")
    private String buildingCommonPassword;
    @NotNull
    @Schema(description = "위반 건축물 여부", example = "false")
    private boolean buildingHasIllegal;

}
