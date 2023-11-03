package org.maveric.quarkus.panache.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.maveric.quarkus.panache.dtos.TransactionResponseDto;
import org.maveric.quarkus.panache.exception.SavingAccountIdNotFound;
import org.maveric.quarkus.panache.model.SavingAccount;
import org.maveric.quarkus.panache.model.Transaction;
import org.maveric.quarkus.panache.repository.SavingAccountRepository;
import org.maveric.quarkus.panache.repository.TransactionRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionServiceImpl implements ITransactionService{
    @Inject
    private TransactionRepository transactionRepository;

    @Inject
    private SavingAccountRepository savingAccountRepository;
    @Inject
    private ModelMapper mapper;
        @Override
        public List<TransactionResponseDto> getTransactions(Long savingsAccountId,int pageNumber, int pageSize) {
            SavingAccount account = savingAccountRepository.findBySavingsAccountId(savingsAccountId);
            if (Objects.isNull(account)) {
                    throw new SavingAccountIdNotFound("Id Not Found "+savingsAccountId);
            } else {

                List<Transaction> transactions = this.transactionRepository.findBySavingAccount(savingsAccountId, pageNumber, pageSize);
                List<TransactionResponseDto> transactionResponseList = transactions.stream()
                        .map(transaction -> this.mapper.map(transaction, TransactionResponseDto.class))
                        .collect(Collectors.toList());

                return transactionResponseList;
            }
        }
    }

