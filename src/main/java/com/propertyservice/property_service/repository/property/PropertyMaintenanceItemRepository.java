package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.PropertyMaintenanceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyMaintenanceItemRepository extends JpaRepository<PropertyMaintenanceItem, Long> {
}
