package com.propertyservice.property_service.service;

import com.propertyservice.property_service.dto.common.FileUploadDto;
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
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    @Value("${server.base-url}") // 서버 URL을 application.properties에서 가져옴
    private String baseUrl;


    public FileUploadDto uploadFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = FileStorageUtil.saveBuildingImageFile(file);
                    fileNames.add(fileName);
                    fileUrls.add(baseUrl + "/images/building/" + fileName);
                } catch (IOException e) {
//                    return new FileUploadDto(new ArrayList<>(), new ArrayList<>());
                    throw  new BusinessException(ErrorCode.IMAGE_ERROR);
                }
            }
        }

        return new FileUploadDto(fileNames, fileUrls);
    }
}
