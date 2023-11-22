package org.maveric.quarkus.panache.model;

import lombok.Data;
import org.maveric.quarkus.panache.enums.InterestCompoundPeriod;
import org.maveric.quarkus.panache.enums.Type;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateSavingsMessageDto implements Serializable {

    Long savingsAccountId;

    String status;

    Long customerId;

    BigDecimal minOpeningBalance;

    InterestCompoundPeriod interestCompoundingPeriod;

    Boolean allowOverDraft;

    Type type;

    String createdAt;

    String city;
}
