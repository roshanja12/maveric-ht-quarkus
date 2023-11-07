package org.maveric.quarkus.panache.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;
import org.maveric.quarkus.panache.dtos.TransactionResponseDto;
import org.maveric.quarkus.panache.enums.TransactionType;
import org.maveric.quarkus.panache.exceptionHandler.InsufficientFundsException;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountDetailsNotFoundException;
import org.maveric.quarkus.panache.model.SavingsAccount;
import org.maveric.quarkus.panache.model.Transaction;
import org.maveric.quarkus.panache.repository.SavingsAccountRepository;
import org.maveric.quarkus.panache.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class TransactionService {
    @Inject
    private TransactionRepository transactionRepository;
    @Inject
    private SavingsAccountRepository savingAccountRepository;
    @Inject
    private ModelMapper mapper;



    @Transactional
    public boolean deposit(TransactionRequestDto requestDto) {
        Long accountId = requestDto.getAccountId();
        BigDecimal amount = requestDto.getAmount();
        SavingsAccount account = savingAccountRepository.findBySavingsAccountId(requestDto.getAccountId());
        if (account == null) {
            throw new SavingsAccountDetailsNotFoundException("Account not found");
        }
        Transaction transaction = new Transaction();
        transaction.setSavingAccount(account);
        transaction.setAmount(amount);
        transaction.setDescription("Bengaluru");
        transaction.setDate(Instant.now());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setBalance(account.getBalance().add(amount));
        transactionRepository.persist(transaction);
        account.setBalance(transaction.getBalance());
        savingAccountRepository.persist(account);
        System.out.println("Executing");
       return true;
    }
    @Transactional
    public boolean withdraw(TransactionRequestDto requestDto) {
        Long accountId = requestDto.getAccountId();
        BigDecimal amount = requestDto.getAmount();
        SavingsAccount account = savingAccountRepository.findBySavingsAccountId(requestDto.getAccountId());
        if (Objects.isNull(account)) {
            throw new SavingsAccountDetailsNotFoundException("Account not found");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        Transaction transaction = new Transaction();
        transaction.setSavingAccount(account);
        transaction.setAmount(amount);
        transaction.setDescription("Bengaluru");
        transaction.setDate(Instant.now());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setBalance(account.getBalance().subtract(amount));
        transactionRepository.persist(transaction);

        account.setBalance(transaction.getBalance());
        savingAccountRepository.persist(account);
        return true;
    }


    public List<TransactionResponseDto> getTransactions(Long savingsAccountId, int pageNumber, int pageSize) {
        SavingsAccount account = savingAccountRepository.findBySavingsAccountId(savingsAccountId);
        if (Objects.isNull(account)) {
            throw new SavingsAccountDetailsNotFoundException("Id Not Found " + savingsAccountId);
        }
        List<Transaction> transactions = this.transactionRepository.findBySavingAccount(savingsAccountId, pageNumber, pageSize);
        List<TransactionResponseDto> transactionResponseList = transactions.stream()
                .map(transaction -> this.mapper.map(transaction, TransactionResponseDto.class))
                .collect(Collectors.toList());
        return transactionResponseList;

    }
}

