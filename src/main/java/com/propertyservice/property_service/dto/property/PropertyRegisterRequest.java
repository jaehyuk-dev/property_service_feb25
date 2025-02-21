package com.propertyservice.property_service.dto.property;

import com.propertyservice.property_service.dto.common.TransactionTypeDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PropertyRegisterRequest {
    @NotNull
    @Schema(description = "건물 id", example = "1")
    private Long buildingId;

    @NotNull
    @NotBlank
    @Schema(description = "임대인 이름", example = "김임대")
    private String ownerName;
    @NotNull
    @NotBlank
    @Schema(description = "임대인 전화번호", example = "010-1234-1234")
    private String ownerPhoneNumber;
    @Schema(description = "임대인 관계", example = "사장님")
    private String ownerRelation;

    @NotNull
    @NotBlank
    @Schema(description = "호 실", example = "B02호")
    private String roomNumber;
    @NotNull
    @NotBlank
    @Schema(description = "매물 유형", example = "오피스텔")
    private String propertyType;
    @NotNull
    @Schema(description = "매물 상태 코드 (공실: 101, 계약 중: 102, 거주 중 : 103", example = "101")
    private int propertyStatusCode;

    @Schema(description = "해당 층", example = "지하 1층")
    private String propertyFloor;
    @Schema(description = "방/욕실 개수", example = "방 3 / 욕실 1")
    private String roomBathCount;
    @Schema(description = "주실 기준 방향", example = "남동향")
    private String mainRoomDirection;

    @Schema(description = "전용면적", example = "102.0")
    private Double exclusiveArea;
    @Schema(description = "공급면적", example = "99.0")
    private Double supplyArea;

    @Schema(description = "사용 승인일", example = "20241212")
    private String approvalDate;

    @Schema(description = "입주일", example = "")
    private String moveInDate;
    @Schema(description = "퇴실일", example = "")
    private String moveOutDate;

    @Schema(description = "입주 가능일", example = "20251212")
    private String availableMoveInDate;

    @NotNull
    @ArraySchema(
            arraySchema = @Schema(description = "매물 거래 유형 목록"),
            schema = @Schema(implementation = TransactionTypeDto.class)
    )
    private List<TransactionTypeDto> transactionTypeList;

    @Schema(description = "매물 관리비 가격", example = "100000")
    private BigDecimal maintenancePrice;
    @Schema(description = "매물 관리비 항목 코드 목록 (수도: 71, 전기: 72, 인터넷: 73, 난방: 74", example = "[71, 72]")
    private List<Integer> maintenanceItemCodeList;

    @Schema(description = "매물 옵션 코드 목록 (에어컨: 91, 세탁기: 92, 냉장고: 93, 가스레인지: 94", example = "[91, 92, 93, 94]")
    private List<Integer> optionItemCodeList;

    @Schema(description = "매물 난방 방식 코드 (개별: 81, 중앙: 82, 심야: 83)", example = "81")
    private int heatingTypeCode;

    @Schema(description = "매물 특이사항", example = "매물 특이사항 등록")
    private String remark;

    @Schema(description = "매물 대표 이미지 인데스 번호", example = "0")
    private int propertyMainPhotoIndex;
    @Schema(description = "매물 이미지 url 목록")
    private List<String> photoList;

}
