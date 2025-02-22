package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.property.PropertyOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyOptionRepository extends JpaRepository<PropertyOption, Long> {
    List<PropertyOption> findAllByProperty(Property property);
}
