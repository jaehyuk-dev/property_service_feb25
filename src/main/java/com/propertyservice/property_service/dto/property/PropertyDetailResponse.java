package com.propertyservice.property_service.dto.property;

import com.propertyservice.property_service.dto.common.ImageDto;
import com.propertyservice.property_service.dto.common.RemarkDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class PropertyDetailResponse {
    private Long propertyId;

    private String picUser;
    private String propertyStatus;

    private Long buildingId;

    private String ownerName;
    private String ownerPhoneNumber;
    private String ownerRelation;

    private String roomNumber;
    private String propertyType;
    private String propertyFloor;
    private String roomBathCount;
    private String mainRoomDirection;

    private Double exclusiveArea;
    private Double supplyArea;

    private LocalDate approvalDate;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private LocalDate availableMoveInDate;

    private String heatingType;

    private BigDecimal maintenancePrice;
    private List<String> maintenaceItemList;

    private List<String> optionItemList;

    private List<PropertyTransactionTypeDto> propertyTransactionList;

    private List<RemarkDto> propertyRemarkList;

    private List<ImageDto> propertyImageList;

//    @Builder
//    public PropertyDetailResponse(String ownerName, String ownerPhoneNumber, String ownerRelation, String roomNumber, String propertyType, String propertyFloor, String roomBathCount, String mainRoomDirection, Double exclusiveArea, Double supplyArea, LocalDate approvalDate, LocalDate moveInDate, LocalDate moveOutDate, LocalDate availableMoveInDate, String heatingType, BigDecimal maintenancePrice, List<String> maintenaceItemList, List<String> optionItemList, List<PropertyTransactionTypeDto> propertyTransactionList, List<RemarkDto> propertyRemarkList, List<ImageDto> propertyImageList) {
//        this.ownerName = ownerName;
//        this.ownerPhoneNumber = ownerPhoneNumber;
//        this.ownerRelation = ownerRelation;
//        this.roomNumber = roomNumber;
//        this.propertyType = propertyType;
//        this.propertyFloor = propertyFloor;
//        this.roomBathCount = roomBathCount;
//        this.mainRoomDirection = mainRoomDirection;
//        this.exclusiveArea = exclusiveArea;
//        this.supplyArea = supplyArea;
//        this.approvalDate = approvalDate;
//        this.moveInDate = moveInDate;
//        this.moveOutDate = moveOutDate;
//        this.availableMoveInDate = availableMoveInDate;
//        this.heatingType = heatingType;
//        this.maintenancePrice = maintenancePrice;
//        this.maintenaceItemList = maintenaceItemList;
//        this.optionItemList = optionItemList;
//        this.propertyTransactionList = propertyTransactionList;
//        this.propertyRemarkList = propertyRemarkList;
//        this.propertyImageList = propertyImageList;
//    }

    @Builder
    public PropertyDetailResponse(Long propertyId, String picUser, String propertyStatus, Long buildingId, String ownerName, String ownerPhoneNumber, String ownerRelation, String roomNumber, String propertyType, String propertyFloor, String roomBathCount, String mainRoomDirection, Double exclusiveArea, Double supplyArea, LocalDate approvalDate, LocalDate moveInDate, LocalDate moveOutDate, LocalDate availableMoveInDate, String heatingType, BigDecimal maintenancePrice, List<String> maintenaceItemList, List<String> optionItemList, List<PropertyTransactionTypeDto> propertyTransactionList, List<RemarkDto> propertyRemarkList, List<ImageDto> propertyImageList) {
        this.propertyId = propertyId;
        this.picUser = picUser;
        this.propertyStatus = propertyStatus;
        this.buildingId = buildingId;
        this.ownerName = ownerName;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.ownerRelation = ownerRelation;
        this.roomNumber = roomNumber;
        this.propertyType = propertyType;
        this.propertyFloor = propertyFloor;
        this.roomBathCount = roomBathCount;
        this.mainRoomDirection = mainRoomDirection;
        this.exclusiveArea = exclusiveArea;
        this.supplyArea = supplyArea;
        this.approvalDate = approvalDate;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
        this.availableMoveInDate = availableMoveInDate;
        this.heatingType = heatingType;
        this.maintenancePrice = maintenancePrice;
        this.maintenaceItemList = maintenaceItemList;
        this.optionItemList = optionItemList;
        this.propertyTransactionList = propertyTransactionList;
        this.propertyRemarkList = propertyRemarkList;
        this.propertyImageList = propertyImageList;
    }
}
