package com.propertyservice.property_service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import java.util.regex.Pattern;

@UtilityClass
public class FileStorageUtil {

    private static final String UPLOAD_BUILDING_DIR = "src/main/resources/static/building/";
    private static final String UPLOAD_PROPERTY_DIR = "src/main/resources/static/property/";

    public static String saveBuildingImageFile(MultipartFile file) throws IOException {
        return saveFile(file, UPLOAD_BUILDING_DIR);
    }

    public static String savePropertyImageFile(MultipartFile file) throws IOException {
        return saveFile(file, UPLOAD_PROPERTY_DIR);
    }

    private static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 1) 원본 파일명
        String originalFilename = file.getOriginalFilename();

        // 2) 파일명 정리(한글/공백/특수문자 등 제거)
        //    예: 한글/공백/특수문자는 모두 '_'로 치환하는 간단한 방식
        //    필요하다면 정규식 등을 바꿔서 원하는 문자만 남길 수 있음
        String sanitized = sanitizeFilename(originalFilename);

        // 3) UUID + 정리된 이름 조합
        String fileName = UUID.randomUUID() + "_" + sanitized;

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName; // 저장된 파일 이름 반환
    }

    /**
     * 한글 / 공백 / 특수문자를 '_' 로 치환하는 예시
     * 필요하다면 정규식 패턴을 바꿔서 더 세밀하게 처리 가능
     */
    private static String sanitizeFilename(String filename) {
        if (filename == null) {
            return "unknown";
        }
        // 원하는 문자셋 (영문, 숫자, 점, 언더바, 대시)만 허용, 나머지는 _
        // 여기서는 '영문/숫자/._-'만 허용
        String pattern = "[^a-zA-Z0-9._-]";
        // 공백, 한글, 특수문자는 '_' 로 치환
        return filename.replaceAll(pattern, "_");
    }
}
