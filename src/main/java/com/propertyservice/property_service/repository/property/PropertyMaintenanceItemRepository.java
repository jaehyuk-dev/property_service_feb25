package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.property.PropertyMaintenanceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyMaintenanceItemRepository extends JpaRepository<PropertyMaintenanceItem, Long> {
    List<PropertyMaintenanceItem> findAllByProperty(Property property);
}
