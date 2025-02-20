package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.property.Building;
import com.propertyservice.property_service.domain.property.BuildingPhoto;
import com.propertyservice.property_service.domain.property.BuildingRemark;
import com.propertyservice.property_service.domain.property.enums.BuildingType;
import com.propertyservice.property_service.dto.common.ImageDto;
import com.propertyservice.property_service.dto.common.RemarkDto;
import com.propertyservice.property_service.dto.file.FileUploadDto;
import com.propertyservice.property_service.dto.property.*;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.office.OfficeUserRepository;
import com.propertyservice.property_service.repository.property.BuildingPhotoRepository;
import com.propertyservice.property_service.repository.property.BuildingRemarkRepository;
import com.propertyservice.property_service.repository.property.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PropertyService {

    private final BuildingRepository buildingRepository;
    private final BuildingRemarkRepository buildingRemarkRepository;
    private final BuildingPhotoRepository buildingPhotoRepository;
    private final OfficeService officeService;
    private final OfficeUserRepository officeUserRepository;

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

    public List<BuildingSummaryDto> searchBuildingSummaryList(String searchWord){
        return buildingRepository.searchBuildingSummaryList(searchWord, officeService.getCurrentUserEntity().getOffice().getId());
    }

    public BuildingDetailResponse searchBuildingDetail(Long buildingId){
        Building building = buildingRepository.findById(buildingId).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        List<RemarkDto> buildingRemarkList = searchBuildingRemarkList(buildingId);
        List<ImageDto> buildingImageList = searchBuildingImageList(buildingId);

        return BuildingDetailResponse.builder()
                .buildingId(building.getId())
                .buildingName(building.getName())
                .buildingZoneCode(building.getZoneCode())
                .buildingAddress(building.getAddress())
                .buildingJibunAddress(building.getJibunAddress())
                .buildingCompletedYear(building.getCompletionYear())
                .buildingTypeName(building.getBuildingType().getLabel())
                .buildingFloorCount(building.getFloorCount())
                .buildingParkingAreaCount(building.getParkingAreaCount())
                .buildingElevatorCount(building.getElevatorCount())
                .buildingMainDoorDirection(building.getMainDoorDirection())
                .buildingCommonPassword(building.getCommonPassword())
                .buildingHasIllegal(building.getHasIllegal())
                .buildingRemarkList(buildingRemarkList)
                .buildingImageList(buildingImageList)
                .build();
    }

    public List<ImageDto> searchBuildingImageList(Long buildingId) {
        Building building = buildingRepository.findById(buildingId).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        List<ImageDto> buildingImageList = new ArrayList<>();
        for (BuildingPhoto buildingPhoto : buildingPhotoRepository.findAllByBuilding(building)) {
            buildingImageList.add(
                    ImageDto.builder()
                            .imageId(buildingPhoto.getId())
                            .isMain(buildingPhoto.getIsMain())
                            .imageUrl(buildingPhoto.getPhotoUrl())
                            .build()
            );
        }
        return buildingImageList;
    }

    public List<RemarkDto> searchBuildingRemarkList(Long buildingId) {
        Building building = buildingRepository.findById(buildingId).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        List<RemarkDto> buildingRemarkList = new ArrayList<>();
        for (BuildingRemark buildingRemark : buildingRemarkRepository.findAllByBuilding(building)) {
            buildingRemarkList.add(
                    RemarkDto.builder()
                            .remarkId(buildingRemark.getId())
                            .remark(buildingRemark.getRemark())
                            .createdAt(buildingRemark.getCreatedDate().toLocalDate())
                            .createdBy(Objects.requireNonNull(officeUserRepository.findById(buildingRemark.getCreatedByUserId()).orElse(null)).getName())
                            .build()
            );
        }
        return buildingRemarkList;
    }

    @Transactional
    public void updateBuilding(BuildingUpdateRequest request){
        Building building = buildingRepository.findById(request.getBuildingId()).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        building.updateBuilding(
                request.getBuildingName(),
                request.getBuildingZoneCode(),
                request.getBuildingAddress(),
                request.getBuildingJibunAddress(),
                request.getBuildingCompletedYear(),
                BuildingType.fromValue(request.getBuildingTypeCode()),
                request.getBuildingFloorCount(),
                request.getBuildingParkingAreaCount(),
                request.getBuildingElevatorCount(),
                request.getBuildingMainDoorDirection(),
                request.getBuildingCommonPassword(),
                request.isBuildingHasIllegal()
        );
    }

    @Transactional
    public void registerBuildingRemark(BuildingRemarkRequest buildingRemarkRequest){
        Building building = buildingRepository.findById(buildingRemarkRequest.getBuildingId()).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        buildingRemarkRepository.save(
                BuildingRemark.builder()
                        .building(building)
                        .remark(buildingRemarkRequest.getBuildingRemark())
                        .build()
        );
    }

    @Transactional
    public void deleteBuildingRemark(Long buildingRemarkId){
        buildingRemarkRepository.deleteById(buildingRemarkId);
    }

    @Transactional
    public void updateBuildingPhotoList(BuildingImageRequest request){
        Building building = buildingRepository.findById(request.getBuildingId()).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        buildingPhotoRepository.deleteAllByBuilding(building);

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
