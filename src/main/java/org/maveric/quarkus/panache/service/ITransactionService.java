package org.maveric.quarkus.panache.service;

import org.maveric.quarkus.panache.dtos.TransactionResponseDto;

import java.util.List;


public interface ITransactionService {

List<TransactionResponseDto> getTransactions(Long savingsAccountId, int pageNumber, int pageSize );
}
