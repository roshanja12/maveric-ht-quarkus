package org.maveric.quarkus.panache.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    private Instant date;
    private String description;
    private BigDecimal amount;
    private BigDecimal balance;
    private TransactionType type;
    @JoinColumn(name = "saving_account_id")
    @ManyToOne
    private SavingAccount savingAccount;
}
