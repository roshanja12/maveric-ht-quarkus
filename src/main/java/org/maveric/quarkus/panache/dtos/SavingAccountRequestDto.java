package org.maveric.quarkus.panache.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.maveric.quarkus.panache.enums.InterestCompoundPeriod;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Saving account request dto")
public class SavingAccountRequestDto {


    @Min(value = 1)
    @Schema(required = true)
    private Long customerId;
    @Schema(required = true)
    @DecimalMin(value = "0", message = "overDraftLimit must be greater than or equal to 0")
    private BigDecimal minOpeningBalance;
    @Schema(required = true)
    @Enumerated(EnumType.STRING)
    private InterestCompoundPeriod interestCompoundPeriod;

    private Boolean isAllowOverDraft;
    @PositiveOrZero(message = "Overdraft limit must be non-negative")
    private BigDecimal overDraftLimit;
}
