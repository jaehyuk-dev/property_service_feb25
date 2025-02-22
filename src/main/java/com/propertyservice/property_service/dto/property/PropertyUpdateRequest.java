package com.propertyservice.property_service.dto.property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyUpdateRequest {
    @NotNull
    @Schema(description = "매물 id", example = "1")
    private Long propertyId;

    @NotNull
    @NotBlank
    @Schema(description = "임대인 이름", example = "김임대")
    private String ownerName;
    @NotNull
    @NotBlank
    @Schema(description = "임대인 전화번호", example = "010-1234-5678")
    private String ownerPhoneNumber;
    @Schema(description = "임대인 관계", example = "사모")
    private String ownerRelation;

    @NotNull
    @NotBlank
    @Schema(description = "호 실", example = "203호")
    private String roomNumber;
    @NotNull
    @NotBlank
    @Schema(description = "매물 유형", example = "투룸")
    private String propertyType;

    @Schema(description = "매물 층 수", example = "3층")
    private String propertyFloor;
    @Schema(description = "주실 기준 방향", example = "남향")
    private String mainRoomDirection;
    @Schema(description = "방/욕실 개수", example = "방2 / 화2")
    private String roomBathCount;

    @Schema(description = "전용 면적", example = "63.0")
    private Double exclusiveArea;
    @Schema(description = "공급 면적", example = "63.0")
    private Double supplyArea;
    @Schema(description = "사용 승인일", example = "20240101")
    private String approvalDate;

    @Schema(description = "난방 방식 (개별: 81, 중앙: 82, 심야: 83)", example = "82")
    private int heatingTypeCode;

    @Schema(description = "입주 가능일", example = "20240101")
    private String availableMoveInDate;
}
