package com.propertyservice.property_service.controller;

import com.propertyservice.property_service.dto.client.ClientRegisterRequest;
import com.propertyservice.property_service.dto.common.ApiResponseDto;
import com.propertyservice.property_service.dto.common.SuccessResponseDto;
import com.propertyservice.property_service.dto.property.BuildingRegisterRequest;
import com.propertyservice.property_service.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
@Tag(name="Property")
public class PropertyController {
    private final PropertyService propertyService;

    @Operation(summary = "건물 등록", description = "건물을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/building")
    public ResponseEntity<ApiResponseDto<String>> registerClient(@Validated @RequestBody BuildingRegisterRequest request) {
        propertyService.registerBuilding(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }
}
