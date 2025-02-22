package com.propertyservice.property_service.controller;

import com.propertyservice.property_service.dto.client.ClientRegisterRequest;
import com.propertyservice.property_service.dto.common.*;
import com.propertyservice.property_service.dto.property.*;
import com.propertyservice.property_service.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponseDto<String>> registerBuilding(@Validated @RequestBody BuildingRegisterRequest request) {
        propertyService.registerBuilding(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "건물 목록 조회", description = "건물 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/building/list")
    public ResponseEntity<ApiResponseDto<List<BuildingSummaryDto>>> searchBuildingSummaryList(@RequestParam(value = "searchWord", defaultValue = "") String searchWord) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchBuildingSummaryList(searchWord)));
    }
    // todo 빌딩 대표 이미지 추가 작업

    @Operation(summary = "건물 상세 조회", description = "건물 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/building/{buildingId}")
    public ResponseEntity<ApiResponseDto<BuildingDetailResponse>> searchBuildingDetail(@PathVariable(value = "buildingId") Long buildingId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchBuildingDetail(buildingId)));
    }

    @Operation(summary = "건물 상세 특이사항 조회", description = "건물 상세 특이사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/building/{buildingId}/remark-list")
    public ResponseEntity<ApiResponseDto<List<RemarkDto>>> searchBuildingRemarkList(@PathVariable(value = "buildingId") Long buildingId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchBuildingRemarkList(buildingId)));
    }

    @Operation(summary = "건물 상세 이미지 조회", description = "건물 상세 이미지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/building/{buildingId}/image-list")
    public ResponseEntity<ApiResponseDto<List<ImageDto>>> searchBuildingImageList(@PathVariable(value = "buildingId") Long buildingId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchBuildingImageList(buildingId)));
    }

    @Operation(summary = "건물 정보 수정", description = "건물 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/building")
    public ResponseEntity<ApiResponseDto<String>> updateBuilding(@Validated @RequestBody BuildingUpdateRequest request) {
        propertyService.updateBuilding(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "건물 특이사항 등록", description = "건물 특이사항을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/building/remark")
    public ResponseEntity<ApiResponseDto<String>> registerBuildingRemark(@Validated @RequestBody BuildingRemarkRequest request) {
        propertyService.registerBuildingRemark(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "건물 특이사항 삭제", description = "건물 특이사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/building/remark/{buildingRemarkId}")
    public ResponseEntity<ApiResponseDto<String>> deleteBuildingRemark(@PathVariable(value = "buildingRemarkId") Long buildingRemarkId) {
        propertyService.deleteBuildingRemark(buildingRemarkId);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "건물 이미지 목록 수정", description = "건물 이미지 목록을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/building/photo-list")
    public ResponseEntity<ApiResponseDto<String>> updateBuildingImage(@Validated @RequestBody BuildingImageRequest request) {
        propertyService.updateBuildingPhotoList(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "매물 등록", description = "매물을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/")
    public ResponseEntity<ApiResponseDto<String>> registerProperty(@Validated @RequestBody PropertyRegisterRequest request) {
        propertyService.registerProperty(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "매물 간략 조회", description = "매물의 간략 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/recap-list")
    public ResponseEntity<ApiResponseDto<List<PropertyRecapDto>>> searchPropertyRecapList(@ModelAttribute SearchCondition condition) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchPropertyRecapList(condition)));
    }

    @Operation(summary = "매물 요약 조회", description = "매물의 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/summary-list")
    public ResponseEntity<ApiResponseDto<List<PropertySummaryDto>>> searchPropertySummaryList(@ModelAttribute SearchCondition condition) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchPropertySummaryList(condition)));
    }

    @Operation(summary = "매물 상세 정보 조회", description = "매물의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{propertyId}")
    public ResponseEntity<ApiResponseDto<PropertyDetailResponse>> searchPropertySummaryList(@PathVariable(value = "propertyId") Long propertyId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchPropertyDetail(propertyId)));
    }
    
    @Operation(summary = "매물 정보 수정", description = "매물 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/")
    public ResponseEntity<ApiResponseDto<String>> updatePropertyDetail(@Validated @RequestBody PropertyUpdateRequest request) {
        propertyService.updatePropertyDetail(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "매물 특이사항 등록", description = "매물 특이사항을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/remark")
    public ResponseEntity<ApiResponseDto<String>> registerPropertyRemark(@Validated @RequestBody PropertyRemarkRequest request) {
        propertyService.registerPropertyRemark(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "매물 특이사항 삭제", description = "매물 특이사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/remark/{propertyRemarkId}")
    public ResponseEntity<ApiResponseDto<String>> deletePropertyRemark(@PathVariable(value = "propertyRemarkId") Long propertyRemarkId) {
        propertyService.deletePropertyRemark(propertyRemarkId);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "매물 상세 특이사항 조회", description = "매물 상세 특이사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/building/{propertyId}/remark-list")
    public ResponseEntity<ApiResponseDto<List<RemarkDto>>> searchPropertyRemarkList(@PathVariable(value = "propertyId") Long propertyId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.searchPropertyRemarkList(propertyId)));
    }

    @Operation(summary = "매물 이미지 목록 수정", description = "매물 이미지 목록을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/photo-list")
    public ResponseEntity<ApiResponseDto<String>> updatePropertyPhotoList(@Validated @RequestBody  PropertyImageRequest request) {
        propertyService.updatePropertyPhotoList(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

    @Operation(summary = "매물 상태 변경 가능 여부 확인.", description = "매물의 상태가 \"거주 중\" 일때 매출 장부에 등록되어있는 매물인지 확인 함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{propertyId}/status")
    public ResponseEntity<ApiResponseDto<Boolean>> canUpdatePropertyStatus(@PathVariable(value = "propertyId") Long propertyId) {
        return ResponseEntity.ok(new SuccessResponseDto<>(propertyService.canUpdatePropertyStatus(propertyId)));
    }

    // 매물 상태 변경
    @Operation(summary = "매물 상태 수정", description = "매물의 상태를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Checked Error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Uncheck Error",
                    content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/status")
    public ResponseEntity<ApiResponseDto<String>> updatePropertyStatus(@Validated @RequestBody  PropertyStatusUpdateRequest request) {
        propertyService.updatePropertyStatus(request);
        return ResponseEntity.ok(new SuccessResponseDto<>("success"));
    }

}
