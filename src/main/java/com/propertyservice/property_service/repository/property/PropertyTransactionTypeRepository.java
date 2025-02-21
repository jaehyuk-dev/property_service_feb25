package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.PropertyTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTransactionTypeRepository extends JpaRepository<PropertyTransactionType, Long> {
}
