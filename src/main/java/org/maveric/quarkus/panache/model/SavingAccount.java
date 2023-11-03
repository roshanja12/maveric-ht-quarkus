package org.maveric.quarkus.panache.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maveric.quarkus.panache.enums.InterestCompoundPeriod;
import org.maveric.quarkus.panache.enums.SavingAccountStatus;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SavingAccount {

    @Id
    @GeneratedValue
    @Column(name = "savings_account_id")
    private Long savingsAccountId;
    @Column(name = "customer_id",nullable = false)
    private Long customerId;

    @Column(name = "min_opening_balance",nullable = false)
    private BigDecimal minOpeningBalance;
    @Column(name = "interest_compound_period",nullable = false)
    @Enumerated(EnumType.STRING)
    private InterestCompoundPeriod interestCompoundPeriod;
    @Column(name = "allow_overdraft")
    private Boolean isAllowOverDraft;
    @Column(name = "over_draft_limit")
    private BigDecimal overDraftLimit;
    private Blob document;
    @Column(name = "document_name")
    private String documentName;
    @Column(name = "created_date")
    private Instant createdDate;
    @Column(name = "updated_date")
    private Instant updatedDate;
    @Column(name = "account_balance",nullable = false)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private SavingAccountStatus status;
    @OneToMany(mappedBy = "savingAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}
