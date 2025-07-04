package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.client.enums.ClientStatus;
import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.property.*;
import com.propertyservice.property_service.domain.property.enums.*;
import com.propertyservice.property_service.domain.revenue.Revenue;
import com.propertyservice.property_service.dto.common.ImageDto;
import com.propertyservice.property_service.dto.common.RemarkDto;
import com.propertyservice.property_service.dto.common.SearchCondition;
import com.propertyservice.property_service.dto.common.TransactionTypeDto;
import com.propertyservice.property_service.dto.file.FileUploadDto;
import com.propertyservice.property_service.dto.property.*;
import com.propertyservice.property_service.error.ErrorCode;
import com.propertyservice.property_service.error.exception.BusinessException;
import com.propertyservice.property_service.repository.office.OfficeRepository;
import com.propertyservice.property_service.repository.office.OfficeUserRepository;
import com.propertyservice.property_service.repository.property.*;
import com.propertyservice.property_service.repository.revenue.RevenueRepository;
import com.propertyservice.property_service.utils.DateTimeUtil;
import com.propertyservice.property_service.utils.PriceFormatter;
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

    private final OfficeService officeService;
    private final OfficeUserRepository officeUserRepository;

    private final BuildingRepository buildingRepository;
    private final BuildingRemarkRepository buildingRemarkRepository;
    private final BuildingPhotoRepository buildingPhotoRepository;

    private final PropertyRepository propertyRepository;
    private final PropertyMaintenanceItemRepository propertyMaintenanceItemRepository;
    private final PropertyOptionRepository propertyOptionRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRemarkRepository propertyRemarkRepository;
    private final PropertyTransactionTypeRepository propertyTransactionTypeRepository;
    private final OfficeRepository officeRepository;
    private final RevenueRepository revenueRepository;

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

        for(int i = 0; i < request.getPhotoUrlList().size(); i++){
            buildingPhotoRepository.save(
                    BuildingPhoto.builder()
                            .building(building)
                            .isMain(i == request.getBuildingMainPhotoIndex())
                            .photoUrl(request.getPhotoUrlList().get(i))
                            .build()
            );
        }
    }

    @Transactional
    public void registerProperty(PropertyRegisterRequest request){
        Building building = buildingRepository.findById(request.getBuildingId()).orElseThrow(
                () -> new BusinessException(ErrorCode.BUILDING_NOT_FOUND)
        );

        Property property = propertyRepository.save(
                Property.builder()
                        .picUser(officeService.getCurrentUserEntity())
                        .building(building)

                        .ownerName(request.getOwnerName())
                        .ownerPhoneNumber(request.getOwnerPhoneNumber())
                        .ownerRelation(request.getOwnerRelation())

                        .roomNumber(request.getRoomNumber())
                        .propertyType(request.getPropertyType())
                        .propertyStatus(PropertyStatus.fromValue(request.getPropertyStatusCode()))

                        .propertyFloor(request.getPropertyFloor())
                        .roomBathCount(request.getRoomBathCount())
                        .mainRoomDirection(request.getMainRoomDirection())

                        .exclusiveArea(request.getExclusiveArea())
                        .supplyArea(request.getSupplyArea())

                        .approvalDate(DateTimeUtil.parseYYYYMMDD(request.getApprovalDate()).orElse(null))

                        .moveInDate(DateTimeUtil.parseYYYYMMDD(request.getMoveInDate()).orElse(null))
                        .moveOutDate(DateTimeUtil.parseYYYYMMDD(request.getMoveOutDate()).orElse(null))

                        .availableMoveInDate(DateTimeUtil.parseYYYYMMDD(request.getMoveInDate()).orElse(null))

                        .heatingType(HeatingType.fromValue(request.getHeatingTypeCode()))
                        .maintenancePrice(request.getMaintenancePrice())
                        .build()
        );

        propertyRemarkRepository.save(
                PropertyRemark.builder()
                        .property(property)
                        .remark(request.getRemark())
                        .build()
        );

        for (TransactionTypeDto transactionTypeDto : request.getTransactionTypeList()) {
            propertyTransactionTypeRepository.save(
                    PropertyTransactionType.builder()
                            .property(property)
                            .transactionType(TransactionType.fromValue(transactionTypeDto.getTransactionCode()))
                            .price1(transactionTypeDto.getPrice1())
                            .price2(transactionTypeDto.getPrice2())
                            .build()
            );
        }

        for (Integer i : request.getMaintenanceItemCodeList()) {
            propertyMaintenanceItemRepository.save(
                    PropertyMaintenanceItem.builder()
                            .property(property)
                            .maintenanceItem(MaintenanceItemType.fromValue(i))
                            .build()
            );
        }

        for (Integer i : request.getOptionItemCodeList()) {
            propertyOptionRepository.save(
                    PropertyOption.builder()
                            .property(property)
                            .optionItemType(OptionItemType.fromValue(i))
                            .build()
            );
        }

        for(int i = 0; i < request.getPhotoList().size(); i++){
            propertyPhotoRepository.save(
                    PropertyPhoto.builder()
                            .property(property)
                            .isMain(i == request.getPropertyMainPhotoIndex())
                            .photoUrl(request.getPhotoList().get(i))
                            .build()
            );
        }
    }

    public List<PropertyRecapDto> searchPropertyRecapList(SearchCondition condition) {
        return propertyRepository.searchPropertyRecapList(condition, officeService.getCurrentUserEntity().getOffice().getId());
    }

    public List<PropertySummaryDto> searchPropertySummaryList(SearchCondition condition) {
        List<PropertySummaryDto> propertySummaryDtoList = propertyRepository.searchPropertySummaryList(condition, officeService.getCurrentUserEntity().getOffice().getId());

        for (PropertySummaryDto propertySummaryDto : propertySummaryDtoList) {
            List<PropertyTransactionDto> propertyTransactionDtoList = new ArrayList<>();

            List<PropertyTransactionType> allByProperty = propertyTransactionTypeRepository.findAllByProperty((
                    propertyRepository.findById(propertySummaryDto.getPropertyId()).orElseThrow(
                            () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
                    )
            ));

            allByProperty.forEach(propertyTransactionType -> {
                propertyTransactionDtoList.add(
                        PropertyTransactionDto.builder()
                                .transactionType(propertyTransactionType.getTransactionType().getLabel())
                                .price(PriceFormatter.format(
                                        propertyTransactionType.getPrice1(),
                                        propertyTransactionType.getPrice2(),
                                        propertyTransactionType.getTransactionType()
                                ))
                                .build()
                );
            });
            propertySummaryDto.setPropertyTransactionList(propertyTransactionDtoList);
        }

        return propertySummaryDtoList;
    }

    public PropertyDetailResponse searchPropertyDetail(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );

        // 관리비 항목 조회
        List<String> maintenanceItemList = new ArrayList<>();
        propertyMaintenanceItemRepository.findAllByProperty(property).forEach(maintenanceItem -> {
            maintenanceItemList.add(maintenanceItem.getMaintenanceItem().getLabel());
        });
        // 옵션 항목 조회
        List<String> optionItemList = new ArrayList<>();
        propertyOptionRepository.findAllByProperty(property).forEach(optionItem -> {
            optionItemList.add(optionItem.getOptionItemType().getLabel());
        });


        // 매물 가격 목록 조회
        List<PropertyTransactionTypeDto> propertyTransactionTypeDtoList = new ArrayList<>();
        propertyTransactionTypeRepository.findAllByProperty(property).forEach(propertyTransactionType -> {
            propertyTransactionTypeDtoList.add(
                    PropertyTransactionTypeDto.builder()
                            .propertyTransactionType(propertyTransactionType.getTransactionType().getLabel())
                            .price1(propertyTransactionType.getPrice1())
                            .price2(propertyTransactionType.getPrice2())
                            .build()
            );
        });


        // 매물  특이사항 목록 조회
        List<RemarkDto> propertyRemarkDtoList = searchPropertyRemarkList(propertyId);


        // 매물 이미지 목록 조회
        List<ImageDto> propertyImageDtoList = searchPropertyImageList(propertyId);


        return PropertyDetailResponse.builder()
                .propertyId(property.getId())
                .picUser(property.getPicUser().getName())
                .propertyStatus(property.getPropertyStatus().getLabel())
                .buildingId(property.getBuilding().getId())
                .ownerName(property.getOwnerName())
                .ownerPhoneNumber(property.getOwnerPhoneNumber())
                .ownerRelation(property.getOwnerRelation())
                .roomNumber(property.getRoomNumber())
                .propertyType(property.getPropertyType())
                .propertyFloor(property.getPropertyFloor())
                .roomBathCount(property.getRoomBathCount())
                .mainRoomDirection(property.getMainRoomDirection())
                .exclusiveArea(property.getExclusiveArea())
                .supplyArea(property.getSupplyArea())
                .approvalDate(property.getApprovalDate())
                .moveInDate(property.getMoveInDate())
                .moveOutDate(property.getMoveOutDate())
                .availableMoveInDate(property.getAvailableMoveInDate())
                .heatingType(property.getHeatingType().getLabel())
                .maintenancePrice(property.getMaintenancePrice())
                .maintenaceItemList(maintenanceItemList)
                .optionItemList(optionItemList)
                .propertyTransactionList(propertyTransactionTypeDtoList)
                .propertyRemarkList(propertyRemarkDtoList)
                .propertyImageList(propertyImageDtoList)
                .build();
    }

    public List<ImageDto> searchPropertyImageList(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );

        List<ImageDto> propertyImageDtoList = new ArrayList<>();
        propertyPhotoRepository.findAllByProperty(property).forEach(propertyPhoto -> {
            propertyImageDtoList.add(
                    ImageDto.builder()
                            .imageId(propertyPhoto.getId())
                            .isMain(propertyPhoto.getIsMain())
                            .imageUrl(propertyPhoto.getPhotoUrl())
                            .build()
            );
        });
        return propertyImageDtoList;
    }

    public List<RemarkDto> searchPropertyRemarkList(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );

        List<RemarkDto> propertyRemarkDtoList = new ArrayList<>();
        propertyRemarkRepository.findAllByProperty(property).forEach(propertyRemark -> {
            propertyRemarkDtoList.add(
                    RemarkDto.builder()
                            .remarkId(propertyRemark.getId())
                            .remark(propertyRemark.getRemark())
                            .createdBy(Objects.requireNonNull(officeUserRepository.findById(propertyRemark.getCreatedByUserId()).orElse(null)).getName())
                            .createdAt(propertyRemark.getCreatedDate().toLocalDate())
                            .build()
            );
        });
        return propertyRemarkDtoList;
    }

    @Transactional
    public void updatePropertyDetail(PropertyUpdateRequest request) {
        propertyRepository.findById(request.getPropertyId()).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        ).updatePropertyDetail(
                request.getOwnerName(),
                request.getOwnerPhoneNumber(),
                request.getOwnerRelation(),
                request.getRoomNumber(),
                request.getPropertyType(),
                request.getPropertyFloor(),
                request.getRoomBathCount(),
                request.getMainRoomDirection(),
                request.getExclusiveArea(),
                request.getSupplyArea(),
                DateTimeUtil.parseYYYYMMDD(request.getApprovalDate()).orElseThrow(
                        () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                ),
                HeatingType.fromValue(request.getHeatingTypeCode()),
                DateTimeUtil.parseYYYYMMDD(request.getAvailableMoveInDate()).orElseThrow(
                        () -> new BusinessException(ErrorCode.INVALID_DATE_FORMAT)
                )
        );
    }

    @Transactional
    public void registerPropertyRemark(PropertyRemarkRequest request) {
        Property property = propertyRepository.findById(request.getPropertyId()).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );
        propertyRemarkRepository.save(
                PropertyRemark.builder()
                        .property(property)
                        .remark(request.getPropertyRemark())
                        .build()
        );
    }

    @Transactional
    public void deletePropertyRemark(Long propertyRemarkId) {
        propertyRemarkRepository.deleteById(propertyRemarkId);
    }

    @Transactional
    public void updatePropertyPhotoList(PropertyImageRequest request){
        Property property = propertyRepository.findById(request.getPropertyId()).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );

        propertyPhotoRepository.deleteAllByProperty(property);

        for(int i = 0; i < request.getPhotoUrlList().size(); i++){
            propertyPhotoRepository.save(
                    PropertyPhoto.builder()
                            .property(property)
                            .isMain(i == request.getPropertyMainPhotoIndex())
                            .photoUrl(request.getPhotoUrlList().get(i))
                            .build()
            );
        }
    }

    public boolean canUpdatePropertyStatus(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );

        List<Revenue> allByProperty = revenueRepository.findAllByProperty(property);

        return allByProperty.isEmpty();
    }

    /**
     * 매물의 상태는 공실, 계약 중, 거주 중..
     * so
     * 공실 > 계약 중 : 그냥 ㄱㄱ
     * 공실 > 거주 중 : 입주일, 퇴실일, 입주 가능일 설정
     * 계약중 > 거주 중 : 입주일, 퇴실일, 입주 가능일 설정
     * 계약중 > 공실 : 그냥 ㄱㄱ
     * 거주 중 > 계약 중 : 그냥 입주, 퇴실 입주 가능을 을 빼야함.
     * 거주 중 > 공실 : 그냥 입주, 퇴실 입주 가능을 을 빼야함.
     * @param request
     */
    @Transactional
    public void updatePropertyStatus(PropertyStatusUpdateRequest request) {
        Property property = propertyRepository.findById(request.getPropertyId()).orElseThrow(
                () -> new BusinessException(ErrorCode.PROPERTY_NOT_FOUND)
        );

        if(property.getPropertyStatus().equals(PropertyStatus.VACANT) &&
                PropertyStatus.fromValue(request.getNewPropertyStatusCode()).equals(PropertyStatus.CONTRACTING)) {
            // 공실 > 계약 중
            property.setPropertyStatus(PropertyStatus.CONTRACTING);
        } else if(property.getPropertyStatus().equals(PropertyStatus.VACANT) &&
                PropertyStatus.fromValue(request.getNewPropertyStatusCode()).equals(PropertyStatus.OCCUPIED)) {
            // 공실 > 거주 중
            property.updatePropertyStatusContractCompleted(
                    DateTimeUtil.parseYYYYMMDD(request.getMoveInDate()).orElseThrow(
                            () -> new BusinessException(ErrorCode.INVALID_DATA_FORMAT)
                    ),
                    DateTimeUtil.parseYYYYMMDD(request.getMoveOutDate()).orElseThrow(
                            () -> new BusinessException(ErrorCode.INVALID_DATA_FORMAT)
                    ),
                    DateTimeUtil.parseYYYYMMDD(request.getMoveOutDate()).orElseThrow(
                            () -> new BusinessException(ErrorCode.INVALID_DATA_FORMAT)
                    ).plusDays(1),
                    PropertyStatus.OCCUPIED
            );
        } else if(property.getPropertyStatus().equals(PropertyStatus.CONTRACTING) &&
                PropertyStatus.fromValue(request.getNewPropertyStatusCode()).equals(PropertyStatus.VACANT)) {
            // 계약 중 > 공실
            property.setPropertyStatus(PropertyStatus.VACANT);
        } else if(property.getPropertyStatus().equals(PropertyStatus.CONTRACTING) &&
                PropertyStatus.fromValue(request.getNewPropertyStatusCode()).equals(PropertyStatus.OCCUPIED)) {
            // 계약 중 > 거주 중
            // 공실 > 거주 중
            property.updatePropertyStatusContractCompleted(
                    DateTimeUtil.parseYYYYMMDD(request.getMoveInDate()).orElseThrow(
                            () -> new BusinessException(ErrorCode.INVALID_DATA_FORMAT)
                    ),
                    DateTimeUtil.parseYYYYMMDD(request.getMoveOutDate()).orElseThrow(
                            () -> new BusinessException(ErrorCode.INVALID_DATA_FORMAT)
                    ),
                    DateTimeUtil.parseYYYYMMDD(request.getMoveOutDate()).orElseThrow(
                            () -> new BusinessException(ErrorCode.INVALID_DATA_FORMAT)
                    ).plusDays(1),
                    PropertyStatus.OCCUPIED
            );
        } else if(property.getPropertyStatus().equals(PropertyStatus.OCCUPIED) &&
                PropertyStatus.fromValue(request.getNewPropertyStatusCode()).equals(PropertyStatus.VACANT)) {
            // 거주 중 > 공실
            property.updatePropertyStatusContractCompleted(
                    null,
                    null,
                    null,
                    PropertyStatus.VACANT
            );
        } else if(property.getPropertyStatus().equals(PropertyStatus.OCCUPIED) &&
                PropertyStatus.fromValue(request.getNewPropertyStatusCode()).equals(PropertyStatus.CONTRACTING)) {
            // 거주 중 > 계약 중
            property.updatePropertyStatusContractCompleted(
                    null,
                    null,
                    null,
                    PropertyStatus.CONTRACTING
            );
        }
    }
}
