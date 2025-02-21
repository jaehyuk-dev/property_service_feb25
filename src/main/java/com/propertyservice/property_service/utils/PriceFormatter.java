package com.propertyservice.property_service.utils;

import com.propertyservice.property_service.domain.common.eums.TransactionType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@UtilityClass
public class PriceFormatter {

    public String format(BigDecimal price1, BigDecimal price2, TransactionType transactionType) {
        String formattedPrice1 = formatPrice(price1);
        String formattedPrice2 = (price2 != null) ? formatPrice(price2) : "0원";

        if (transactionType == TransactionType.JEONSE) {
            return formattedPrice1;
        } else {
            return formattedPrice1 + " / " + formattedPrice2 + " 원";
        }
    }

    private String formatPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) return "0원";

        BigDecimal billion = new BigDecimal("100000000"); // 억 단위
        BigDecimal million = new BigDecimal("10000"); // 만 단위

        BigDecimal[] billionResult = price.divideAndRemainder(billion);
        BigDecimal[] millionResult = billionResult[1].divideAndRemainder(million);

        BigDecimal billionPart = billionResult[0];
        BigDecimal millionPart = millionResult[0];

        StringBuilder result = new StringBuilder();

        if (billionPart.compareTo(BigDecimal.ZERO) > 0) {
            result.append(billionPart.intValue()).append("억");
        }
        if (millionPart.compareTo(BigDecimal.ZERO) > 0) {
            if (!result.isEmpty()) result.append(" ");
            result.append(millionPart.intValue()).append("만");
        }

        // '억'이나 '만' 단위가 없는 경우 원 단위로 표시
        if (result.isEmpty()) {
            result.append(price.intValue());
        }

        return result.toString();
    }
}
