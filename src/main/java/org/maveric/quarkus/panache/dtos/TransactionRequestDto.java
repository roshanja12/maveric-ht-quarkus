package org.maveric.quarkus.panache.dtos;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;


@Data
@Schema(description = "It is the request to update the balance ")
public class TransactionRequestDto {
    @Schema(required = true)
    private BigDecimal amount;
    @Schema(required = true)
    private Long accountId;

}
