package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.property.Building;
import com.propertyservice.property_service.domain.property.BuildingPhoto;
import com.propertyservice.property_service.domain.property.BuildingRemark;
import com.propertyservice.property_service.domain.property.enums.BuildingType;
import com.propertyservice.property_service.dto.file.FileUploadDto;
import com.propertyservice.property_service.dto.property.BuildingRegisterRequest;
import com.propertyservice.property_service.repository.property.BuildingPhotoRepository;
import com.propertyservice.property_service.repository.property.BuildingRemarkRepository;
import com.propertyservice.property_service.repository.property.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PropertyService {

    private final BuildingRepository buildingRepository;
    private final BuildingRemarkRepository buildingRemarkRepository;
    private final BuildingPhotoRepository buildingPhotoRepository;
    private final OfficeService officeService;

    @Transactional
    public void registerBuilding(BuildingRegisterRequest request) {
        Building building = buildingRepository.save(
                Building.builder()
                        .pocOffice(officeService.getCurrentUserEntity().getOffice())
                        .name(request.getBuildingName())
                        .zoneCode(request.getBuildingZoneCode())
                        .address(request.getBuildingAddress())
                        .jibunAddress(request.getBuildingJibunAddress())
                        .parkingAreaCount(request.getBuildingParkingAreaCount())
                        .floorCount(request.getBuildingFloorCount())
                        .mainDoorDirection(request.getBuildingMainDoorDirection())
                        .completionYear(request.getBuildingCompletedYear())
                        .buildingType(BuildingType.fromValue(request.getBuildingTypeCode()))
                        .elevatorCount(request.getBuildingElevatorCount())
                        .hasIllegal(request.isBuildingHasIllegal())
                        .commonPassword(request.getBuildingCommonPassword())
                        .build()
        );

        buildingRemarkRepository.save(
                BuildingRemark.builder()
                        .building(building)
                        .remark(request.getBuildingRemark())
                        .build()
        );

        for(int i = 0; i < request.getPhotoList().size(); i++){
            buildingPhotoRepository.save(
                    BuildingPhoto.builder()
                            .building(building)
                            .isMain(i == request.getBuildingMainPhotoIndex())
                            .photoUrl(request.getPhotoList().get(i))
                            .build()
            );
        }
    }
}
