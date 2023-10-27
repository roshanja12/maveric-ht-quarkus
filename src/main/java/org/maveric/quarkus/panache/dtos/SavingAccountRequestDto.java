package org.maveric.quarkus.panache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Saving account request dto")
public class SavingAccountRequestDto {
    @Schema(required = true)
    private Long customerId;
    @Schema(required = true)
    private String customerName;
    @Schema(required = true)
    private String phoneNumber;
    @Schema(required = true)
    private BigDecimal minOpeningBalance;
    @Schema(required = true)
    private String interestCompoundPeriod;
    @Schema(required = true)
    private Boolean allowOverDraft;
    @Schema(required = true)
    private BigDecimal overDraftLimit;
}