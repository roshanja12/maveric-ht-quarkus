package org.maveric.quarkus.panache.dtos;

import lombok.Data;
import org.maveric.quarkus.panache.model.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionResponseDto {

    private Instant date;
    private String description;
    private BigDecimal amount;
    private BigDecimal balance;
    private TransactionType type;

}
