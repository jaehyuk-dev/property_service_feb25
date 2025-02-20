package com.propertyservice.property_service.domain.property.enums;

import lombok.Getter;

@Getter
public enum OptionItemType {
    WATER(91, "에어컨"),
    ELECTRICITY(92, "세탁기"),
    INTERNET(93, "냉장고"),
    HEATING(94, "가스레인지");

    private final int value;
    private final String label;

    OptionItemType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static OptionItemType fromValue(int value) {
        for (OptionItemType type : OptionItemType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid MaintenanceItemType value: " + value);
    }
}
