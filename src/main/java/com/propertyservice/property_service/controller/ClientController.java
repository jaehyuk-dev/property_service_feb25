package com.propertyservice.property_service.controller;

import com.propertyservice.property_service.dto.client.ClientDetailResponse;
import com.propertyservice.property_service.dto.client.ClientRegisterRequest;
import com.propertyservice.property_service.dto.client.ClientSummaryDto;
import com.propertyservice.property_service.dto.common.ApiResponseDto;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.dto.common.SuccessResponseDto;
import com.propertyservice.property_service.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
@Tag(name="Client")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "고객 등록", description = "고객을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/")
    public ResponseEntity<ApiResponseDto<String>> registerClient(@Validated @RequestBody ClientRegisterRequest request) {
        clientService.registerClient(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "고객 요약 목록", description = "고객 요약 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/list")
    public ResponseEntity<ApiResponseDto<List<ClientSummaryDto>>> searchClientSummaryInfoList(SearchCondition condition) {
        return ResponseEntity.ok(new SuccessResponseDto<>(clientService.searchClientSummaryInfoList(condition)));
    }

    @Operation(summary = "고객 상세 정보 조회", description = "고객의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResponseDto<ClientDetailResponse>> searchClientDetailInfo(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(clientService.searchClientDetailInfo(clientId)));
    }

}
