package com.propertyservice.property_service.domain.property.enums;

import jakarta.persistence.AttributeConverter;

public class OptionItemTypeConverter implements AttributeConverter<OptionItemType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OptionItemType optionItemType) {
        return (optionItemType != null) ? optionItemType.getValue() : null;
    }

    @Override
    public OptionItemType convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? OptionItemType.fromValue(dbData) : null;
    }
}
