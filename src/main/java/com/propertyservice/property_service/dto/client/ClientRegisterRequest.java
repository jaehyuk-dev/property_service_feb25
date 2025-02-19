package com.propertyservice.property_service.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Getter
@Service
public class ClientRegisterRequest {
    @NotNull
    @NotBlank
    @Schema(description = "고객 이름", example = "김고객")
    private String clientName;
    @NotNull
    @NotBlank
    @Schema(description = "고객 전화번호", example = "010-1234-1234")
    private String clientPhoneNumber;

    @NotNull
    @Schema(description = "고객 성별 코드 (31: 남성, 32: 여성)", example = "31")
    private int clientGenderCode;

    @Schema(description = "고객 유입 경로", example = "워킹")
    private String clientSource;
    @Schema(description = "고객 유형", example = "학생")
    private String clientType;

    @NotNull
    @Schema(description = "고객 희망 입주일", example = "20250101")
    private String expectedMoveInDate;

    @NotNull
    @Schema(description = "고객 희망 거래 유형(61: 월세, 62: 전세, 64: 단기", example = "[61, 64]")
    private List<Integer> expectedTransactionTypeCodeList;

    @Schema(description = "고객 특이사항", example = "고객 특이사항 1")
    private String clientRemark;
}
