package com.propertyservice.property_service.domain.property;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import com.propertyservice.property_service.domain.common.eums.TransactionTypeConverter;
import com.propertyservice.property_service.domain.property.enums.MaintenanceItemType;
import com.propertyservice.property_service.domain.property.enums.MaintenanceItemTypeConverter;
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
@Table(name = "property_transaction_types")
public class PropertyTransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_transaction_type_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Convert(converter = TransactionTypeConverter.class)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "price1")
    private BigDecimal price1;

    @Column(name = "price2")
    private BigDecimal price2;

    @Builder
    public PropertyTransactionType(Property property, TransactionType transactionType, BigDecimal price1, BigDecimal price2) {
        this.property = property;
        this.transactionType = transactionType;
        this.price1 = price1;
        this.price2 = price2;
    }
}
