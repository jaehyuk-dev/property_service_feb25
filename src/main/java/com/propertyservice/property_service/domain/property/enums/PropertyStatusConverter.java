package com.propertyservice.property_service.domain.property.enums;

import jakarta.persistence.AttributeConverter;

public class PropertyStatusConverter implements AttributeConverter<PropertyStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PropertyStatus propertyStatus) {
        return (propertyStatus != null) ? propertyStatus.getValue() : null;
    }

    @Override
    public PropertyStatus convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? PropertyStatus.fromValue(dbData) : null;
    }
}
