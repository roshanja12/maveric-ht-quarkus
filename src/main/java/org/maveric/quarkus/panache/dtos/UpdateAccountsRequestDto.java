package org.maveric.quarkus.panache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="it is used to update ")
public class UpdateAccountsRequestDto {

    @Schema(required = true)
    private Integer savingAccountId;
    private Boolean allowOverDraft;
    private BigDecimal overDraftLimit;
    private String status;

}
