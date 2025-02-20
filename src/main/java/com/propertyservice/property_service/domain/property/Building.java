package com.propertyservice.property_service.domain.property;

import com.propertyservice.property_service.domain.common.BaseEntity;
import com.propertyservice.property_service.domain.office.Office;
import com.propertyservice.property_service.domain.office.OfficeUser;
import com.propertyservice.property_service.domain.property.enums.BuildingType;
import com.propertyservice.property_service.domain.property.enums.BuildingTypeConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "building")
public class Building extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poc_office_id", nullable = false)
    private Office pocOffice;

    @Column(name = "building_name", length = 255)
    private String name;

    @Column(name = "zone_code", nullable = false, length = 5)
    private String zoneCode;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "jibun_address", length = 255)
    private String jibunAddress;

    @Column(name = "completion_year", nullable = false)
    private int completionYear;

    @Convert(converter = BuildingTypeConverter.class)
    @Column(name = "building_type", nullable = false)
    private BuildingType buildingType; // ✅ 31(주거용), 32(비주거용)

    @Column(name = "floor_count", nullable = false, length = 255)
    private String floorCount;

    @Column(name = "parking_area_count", nullable = false)
    private int parkingAreaCount;

    @Column(name = "elevator_count", nullable = false)
    private int elevatorCount;

    @Column(name = "main_door_direction", length = 255)
    private String mainDoorDirection;

    @Column(name = "common_password", length = 255)
    private String commonPassword;

    @Column(name = "has_illegal", nullable = false)
    private Boolean hasIllegal = false;

    @Builder
    public Building(Office pocOffice, String name, String zoneCode, String address, String jibunAddress, int completionYear, BuildingType buildingType, String floorCount, int parkingAreaCount, int elevatorCount, String mainDoorDirection, String commonPassword, Boolean hasIllegal) {
        this.pocOffice = pocOffice;
        this.name = name;
        this.zoneCode = zoneCode;
        this.address = address;
        this.jibunAddress = jibunAddress;
        this.completionYear = completionYear;
        this.buildingType = buildingType;
        this.floorCount = floorCount;
        this.parkingAreaCount = parkingAreaCount;
        this.elevatorCount = elevatorCount;
        this.mainDoorDirection = mainDoorDirection;
        this.commonPassword = commonPassword;
        this.hasIllegal = hasIllegal;
    }
}
