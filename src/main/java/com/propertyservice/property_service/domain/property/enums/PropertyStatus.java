package com.propertyservice.property_service.domain.property.enums;

import lombok.Getter;

@Getter
public enum PropertyStatus {
    VACANT(101, "공실"),
    CONTRACTING(102, "계약 중"),
    OCCUPIED(103, "거주 중");

    private final int value;
    private final String label;

    PropertyStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static PropertyStatus fromValue(int value) {
        for (PropertyStatus status : PropertyStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PropertyStatus value: " + value);
    }
}
