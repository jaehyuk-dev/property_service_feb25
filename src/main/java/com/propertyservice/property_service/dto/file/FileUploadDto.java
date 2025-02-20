package com.propertyservice.property_service.dto.file;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileUploadDto {
    private List<String> fileNames;
    private List<String> fileUrls;

    public FileUploadDto(List<String> fileNames, List<String> fileUrls) {
        this.fileNames = fileNames;
        this.fileUrls = fileUrls;
    }
}
