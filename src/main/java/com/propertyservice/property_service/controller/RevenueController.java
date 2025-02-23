package com.propertyservice.property_service.controller;

import com.propertyservice.property_service.dto.common.ApiResponseDto;
import com.propertyservice.property_service.dto.common.SuccessResponseDto;
import com.propertyservice.property_service.dto.property.BuildingSummaryDto;
import com.propertyservice.property_service.dto.revenue.RevenueDto;
import com.propertyservice.property_service.dto.revenue.RevenueInfoDto;
import com.propertyservice.property_service.dto.revenue.RevenueSearchCondition;
import com.propertyservice.property_service.service.RevenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/revenue")
@Tag(name="Revenue")
public class RevenueController {

    private final RevenueService revenueService;

    // 매출 목록 조회
    @Operation(summary = "매출 목록 조회", description = "매출 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/list")
    public ResponseEntity<ApiResponseDto<RevenueInfoDto>> searchRevenueList(RevenueSearchCondition condition) {
        return ResponseEntity.ok(new SuccessResponseDto<>(revenueService.searchRevenueList(condition)));
    }


    // 매출 삭제
    // 매출 목록 조회
    @Operation(summary = "매출 삭제", description = "매출을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/{revenueId}")
    public ResponseEntity<ApiResponseDto<String>> searchBuildingSummaryList(@PathVariable("revenueId") Long revenueId) {
        revenueService.deleteRevenue(revenueId);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

}
