package com.propertyservice.property_service.repository.revenue;

import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.revenue.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    List<Revenue> findAllByProperty(Property property);
}
