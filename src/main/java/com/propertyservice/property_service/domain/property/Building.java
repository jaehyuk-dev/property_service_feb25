package com.propertyservice.property_service.domain.property;

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
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "building_name", length = 255)
    private String name;

    @Column(name = "zone_code", nullable = false, length = 5)
    private String zoneCode;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "jibun_address", length = 255)
    private String jibunAddress;

    @Column(name = "completion_year", nullable = false)
    private Short completionYear;

    @Convert(converter = BuildingTypeConverter.class)
    @Column(name = "building_type", nullable = false)
    private BuildingType buildingType; // ✅ 31(주거용), 32(비주거용)

    @Column(name = "floor_count", nullable = false, length = 255)
    private String floorCount;

    @Column(name = "parking_area_count", nullable = false)
    private Short parkingAreaCount;

    @Column(name = "elevator_count", nullable = false)
    private Short elevatorCount;

    @Column(name = "main_door_direction", length = 255)
    private String mainDoorDirection;

    @Column(name = "common_password", length = 255)
    private String commonPassword;

    @Column(name = "has_illegal", nullable = false)
    private Boolean hasIllegal = false;

    @Builder
    public Building(String name, String zoneCode, String address, String jibunAddress, Short completionYear, BuildingType buildingType, String floorCount, Short parkingAreaCount, Short elevatorCount, String mainDoorDirection, String commonPassword, Boolean hasIllegal) {
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
