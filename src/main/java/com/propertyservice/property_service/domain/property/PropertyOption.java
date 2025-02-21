package com.propertyservice.property_service.domain.property;

import com.propertyservice.property_service.domain.property.enums.OptionItemType;
import com.propertyservice.property_service.domain.property.enums.OptionItemTypeConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "property_options")
public class PropertyOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_option_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Convert(converter = OptionItemTypeConverter.class)
    @Column(name = "option_item_type")
    private OptionItemType optionItemType;

    @Builder
    public PropertyOption(OptionItemType optionItemType, Property property) {
        this.optionItemType = optionItemType;
        this.property = property;
    }
}
