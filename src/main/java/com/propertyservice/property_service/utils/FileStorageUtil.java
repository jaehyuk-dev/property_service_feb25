package com.propertyservice.property_service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@UtilityClass
public class FileStorageUtil {

    private static final String UPLOAD_BUILDING_DIR = "uploads/building/";

    public static String saveBuildingImageFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_BUILDING_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName; // 저장된 파일 이름 반환
    }
}
