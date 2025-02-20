package com.propertyservice.property_service.domain.property;

import com.propertyservice.property_service.domain.office.OfficeUser;
import com.propertyservice.property_service.domain.property.enums.BuildingType;
import com.propertyservice.property_service.domain.property.enums.BuildingTypeConverter;
import com.propertyservice.property_service.domain.property.enums.PropertyStatus;
import com.propertyservice.property_service.domain.property.enums.PropertyStatusConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private OfficeUser picUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Convert(converter = PropertyStatusConverter.class)
    @Column(name = "property_status", nullable = false)
    private PropertyStatus propertyStatus; // ✅ 101(공실), 102(계약 중), 103(거주 중)

    @Column(name = "owner_name", length = 255)
    private String ownerName;

    @Column(name = "owner_phone_number", nullable = false, length = 255)
    private String ownerPhoneNumber;

    @Column(name = "owner_relation", length = 255)
    private String ownerRelation;

    @Column(name = "property_room_number", nullable = false, length = 255)
    private String roomNumber;

    @Column(name = "property_type", nullable = false, length = 255)
    private String propertyType;

    @Column(name = "property_floor", length = 255)
    private String propertyFloor;

    @Column(name = "room_bath_count", nullable = false, length = 255)
    private String roomBathCount;

    @Column(name = "main_room_direction", nullable = false, length = 64)
    private String mainRoomDirection;

    @Column(name = "exclusive_area")
    private Double exclusiveArea;

    @Column(name = "supply_area")
    private Double supplyArea;

    @Column(name = "approval_date", nullable = false)
    private LocalDate approvalDate;

    @Column(name = "move_in_date")
    private LocalDate moveInDate;

    @Column(name = "move_out_date")
    private LocalDate moveOutDate;

    @Column(name = "available_move_in_date", nullable = false)
    private LocalDate availableMoveInDate;

    @Builder
    public Property(OfficeUser picUser, Building building, PropertyStatus propertyStatus, String ownerName, String ownerPhoneNumber, String ownerRelation, String roomNumber, String propertyType, String propertyFloor, String roomBathCount, String mainRoomDirection, Double exclusiveArea, Double supplyArea, LocalDate approvalDate, LocalDate moveInDate, LocalDate moveOutDate, LocalDate availableMoveInDate) {
        this.picUser = picUser;
        this.building = building;
        this.propertyStatus = propertyStatus;
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
    }
}
