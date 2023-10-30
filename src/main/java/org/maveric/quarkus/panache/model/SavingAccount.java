package org.maveric.quarkus.panache.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SavingAccount {

    @Id
    @GeneratedValue
    private Long savingsAccountId;
    @Column(nullable = false)
    private Long customerId;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String customerEmail;
    @Column(nullable = false)
    private Integer customerPhone;
    @Column(nullable = false)
    private BigDecimal minOpeningBalance;
    @Column(nullable = false)
    private InterestCompoundPeriod interestCompoundPeriod;
    @Column(nullable = false)
    private Boolean isAllowOverDraft;
    private BigDecimal overDraftLimit;
    private byte[] documents;
    private Instant createdDate;
    private Instant updatedDate;
    @Column(nullable = false)
    private Long balance;
    @Enumerated(EnumType.STRING)
    private SavingAccountStatus status;
    @OneToMany(mappedBy = "savingAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}
