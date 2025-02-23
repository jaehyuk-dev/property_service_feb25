package com.propertyservice.property_service.dto.revenue;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RevenueDto {
    private Long revenueId;
    private String picManagerName;
    private String clientName;
    private String transactionType;
    private String transactionPrice;
    private String propertyAddress;
    private String propertyOwnerName;
    private String commissionFee;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;

    @QueryProjection
    public RevenueDto(Long revenueId, String picManagerName, String clientName, String transactionType, String transactionPrice, String propertyAddress, String propertyOwnerName, String commissionFee, LocalDate moveInDate, LocalDate moveOutDate) {
        this.revenueId = revenueId;
        this.picManagerName = picManagerName;
        this.clientName = clientName;
        this.transactionType = transactionType;
        this.transactionPrice = transactionPrice;
        this.propertyAddress = propertyAddress;
        this.propertyOwnerName = propertyOwnerName;
        this.commissionFee = commissionFee;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
    }
}
