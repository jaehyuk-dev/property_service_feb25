package com.propertyservice.property_service.repository.property;

import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.property.PropertyRemark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRemarkRepository extends JpaRepository<PropertyRemark, Long> {

    List<PropertyRemark> findAllByProperty(Property property);
}
