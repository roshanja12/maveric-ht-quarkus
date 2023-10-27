package org.maveric.quarkus.panache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Draft Details request dto")
public class DraftDetailsRequestDto {
    @Schema(required = true)
    private Boolean allowOverDraft;
    @Schema(required = true)
    private BigDecimal overDraftLimit;
}
