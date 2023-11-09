package org.maveric.quarkus.panache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maveric.quarkus.panache.enums.InterestCompoundPeriod;
import org.maveric.quarkus.panache.enums.SavingsAccountStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountResponseDto {

  private Long savingsAccountId;
  private Long customerId;
  private String customerName;
  private String customerEmail;
  private Long customerPhone;
  private BigDecimal minOpeningBalance;

  private InterestCompoundPeriod interestCompoundPeriod;

  private Boolean allowOverDraft;
  private BigDecimal overDraftLimit;
  private String documentName;

  private Instant createdDate;
  private Instant updatedDate;

  private Long balance;
  private SavingsAccountStatus status;

}
