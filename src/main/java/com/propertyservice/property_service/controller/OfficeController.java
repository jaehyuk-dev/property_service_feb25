package com.propertyservice.property_service.controller;

import com.propertyservice.property_service.dto.common.ApiResponseDto;
import com.propertyservice.property_service.dto.common.SuccessResponseDto;
import com.propertyservice.property_service.dto.office.*;
import com.propertyservice.property_service.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/office")
@Tag(name="Office")
public class OfficeController {

    private final OfficeService officeService;

    @Operation(summary = "중개 사무소 등록", description = "중개 사무소를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록된 중개사무소 코드",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/")
    public ResponseEntity<ApiResponseDto<OfficeRegisterResponse>> registerOffice(@Validated @RequestBody OfficeRegisterRequest request) {
        return ResponseEntity.ok(new SuccessResponseDto<>(
                officeService.registerOffice(request)
        ));
    }

    @Operation(summary = "중개 사무소 조회", description = "중개 사무소를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회된 중개 사무소 정보",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/")
    public ResponseEntity<ApiResponseDto<OfficeSearchResponse>> searchOffice(@Validated OfficeSearchRequest request) {
        return ResponseEntity.ok(new SuccessResponseDto<>(
                officeService.searchOffice(request)
        ));
    }

    @Operation(summary = "회원가입", description = "회원가입 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/user/sign-up")
    public ResponseEntity<ApiResponseDto<String>> signUpUser(@Validated @RequestBody OfficeUserSignUpRequest request) {
        officeService.signUpUser(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("Sign Up Success"));
    }
}
