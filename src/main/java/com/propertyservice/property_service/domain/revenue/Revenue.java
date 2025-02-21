package com.propertyservice.property_service.domain.revenue;

import com.propertyservice.property_service.domain.client.Client;
import com.propertyservice.property_service.domain.client.ShowingProperty;
import com.propertyservice.property_service.domain.property.Property;
import com.propertyservice.property_service.domain.property.PropertyTransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "revenues")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenue_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_transaction_type_id", nullable = false)
    private PropertyTransactionType transactionType;

    @Column(name = "commision_fee")
    private BigDecimal commissionFee;

    @Builder
    public Revenue(Client client, Property property, PropertyTransactionType transactionType, BigDecimal commissionFee) {
        this.client = client;
        this.property = property;
        this.transactionType = transactionType;
        this.commissionFee = commissionFee;
    }
}
