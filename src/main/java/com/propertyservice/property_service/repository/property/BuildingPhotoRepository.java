package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.Building;
import com.propertyservice.property_service.domain.property.BuildingPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingPhotoRepository extends JpaRepository<BuildingPhoto, Long>{
    List<BuildingPhoto> findAllByBuilding(Building building);

    void deleteAllByBuilding(Building building);
}
