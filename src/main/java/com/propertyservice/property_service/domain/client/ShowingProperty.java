package com.propertyservice.property_service.domain.client;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.property.PropertyTransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "showing_properties")
public class ShowingProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showing_property_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_transaction_type_id", nullable = false)
    private PropertyTransactionType showingPropertyTransactionType;


    @Builder
    public ShowingProperty(Client client, Property property, PropertyTransactionType showingPropertyTransactionType) {
        this.client = client;
        this.property = property;
        this.showingPropertyTransactionType = showingPropertyTransactionType;
    }
}
