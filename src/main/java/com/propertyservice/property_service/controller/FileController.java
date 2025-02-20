package com.propertyservice.property_service.controller;

import com.propertyservice.property_service.dto.common.ApiResponseDto;
import com.propertyservice.property_service.dto.common.SuccessResponseDto;
import com.propertyservice.property_service.dto.file.FileUploadDto;
import com.propertyservice.property_service.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Tag(name="File")
public class FileController {

    private final FileService fileService;

    @Operation(
            summary = "건물 이미지 파일 목록 업로드",
            description = "건물 이미지를 업로드하고, 저장된 파일 URL을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업로드 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileUploadDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (파일이 없음)",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "/images/building", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<FileUploadDto>> uploadBuildingImages(
            @RequestParam("buildingImageList")
            @Size(max = 3, message = "건물 이미지는 최대 3개까지만 업로드 가능합니다.")
            List<MultipartFile> files
    ) {
         return ResponseEntity.ok(new SuccessResponseDto<>(fileService.uploadBuildingImages(files)));
    }

    @Operation(
            summary = "매물 이미지 파일 목록 업로드",
            description = "매물 이미지를 업로드하고, 저장된 파일 URL을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업로드 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileUploadDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (파일이 없음)",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "/images/property", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<FileUploadDto>> uploadPropertyImages(
            @RequestParam("propertyImageList")
            @Size(max = 5, message = "매물 이미지는 최대 5개까지만 업로드 가능합니다.")
            List<MultipartFile> files
    ) {
        return ResponseEntity.ok(new SuccessResponseDto<>(fileService.uploadPropertyImages(files)));
    }
}
