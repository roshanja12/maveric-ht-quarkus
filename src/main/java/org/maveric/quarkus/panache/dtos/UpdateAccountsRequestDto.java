package org.maveric.quarkus.panache.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.maveric.quarkus.panache.enums.SavingAccountStatus;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="it is used to update status and draft details ")
public class UpdateAccountsRequestDto {

    @Schema(required = true)
    @Min(value = 1)
    private Long savingAccountId;
    private Boolean isAllowOverDraft;
    private BigDecimal overDraftLimit;
    private SavingAccountStatus status;

}
