package com.propertyservice.property_service.service;

import com.propertyservice.property_service.dto.file.FileUploadDto;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.utils.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    public FileUploadDto uploadBuildingImages(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = FileStorageUtil.saveBuildingImageFile(file);
                    fileNames.add(fileName);
                    fileUrls.add("/building/" + fileName);
                } catch (IOException e) {
                    throw  new BusinessException(ErrorCode.IMAGE_ERROR);
                }
            }
        }

        return new FileUploadDto(fileNames, fileUrls);
    }

    public FileUploadDto uploadPropertyImages(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            // 1) 이미지 파일 여부 검사
            validateImageFile(file);

            if (!file.isEmpty()) {
                try {
                    String fileName = FileStorageUtil.savePropertyImageFile(file);
                    fileNames.add(fileName);
                    fileUrls.add("/property/" + fileName);
                } catch (IOException e) {
                    throw  new BusinessException(ErrorCode.IMAGE_ERROR);
                }
            }
        }

        return new FileUploadDto(fileNames, fileUrls);
    }

    /**
     * 이미지 파일 확장자를 검증하는 메서드.
     * 필요에 따라 MIME 타입도 함께 검증 가능.
     */
    private void validateImageFile(MultipartFile file) {
        // 확장자로 검증하는 간단한 예시
        // 더 강력한 보안을 위해서는 MIME 타입도 함께 확인하거나 Magic Number를 확인하는 방식을 고려할 수도 있습니다.
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new BusinessException(ErrorCode.INVALID_NO_IMAGE_NAME);
        }

        // 허용할 확장자 목록
        List<String> allowedExts = Arrays.asList(
                "jpg",
                "jpeg",
                "png",
                "gif",
                "bmp",
                "webp",
                "heic",
                "heif"
        );

        // 실제 확장자 추출 (소문자 처리)
        String ext = getFileExtension(originalFilename).toLowerCase();

        if (!allowedExts.contains(ext)) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_FILE);
        }
    }

    /**
     * 간단히 파일 확장자를 추출하는 유틸 메서드 예시
     */
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return ""; // 확장자가 없는 경우
        }
        return filename.substring(dotIndex + 1);
    }
}
