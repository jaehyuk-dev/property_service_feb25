package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.property.PropertyTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyTransactionTypeRepository extends JpaRepository<PropertyTransactionType, Long> {
    PropertyTransactionType findByProperty(Property property);

    List<PropertyTransactionType> findAllByProperty(Property property);
}
