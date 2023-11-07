package org.maveric.quarkus.panache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.exceptionHandler.SavingDetailsNotFoundException;
import org.maveric.quarkus.panache.services.TransactionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.maveric.quarkus.panache.dtos.TransactionResponseDto;
import org.maveric.quarkus.panache.model.SavingAccount;
import org.maveric.quarkus.panache.model.Transaction;
import org.maveric.quarkus.panache.repository.SavingAccountRepository;
import org.maveric.quarkus.panache.repository.TransactionRepository;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionServiceImplTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactions() {
        Long savingsAccountId = 123L;
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setSavingsAccountId(1L);
        savingAccount.setSavingsAccountId(savingsAccountId);
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionResponseDto> expectedResponse = new ArrayList<>();
        TransactionResponseDto sampleResponse = new TransactionResponseDto();
        expectedResponse.add(sampleResponse);


        when(savingAccountRepository.findBySavingsAccountId(savingsAccountId)).thenReturn(savingAccount);
        when(transactionRepository.findBySavingAccount(savingsAccountId, 1, 10)).thenReturn(transactions);
        List<TransactionResponseDto> actualResponse = transactionService.getTransactions(savingsAccountId, 1, 10);

        // Assertions
//        assertEquals(expectedResponse.size(), actualResponse.size());
        // Add more assertions based on your specific test case
    }

    @Test
    public void testGetTransactionsWithInvalidSavingAccountId() {
        Long savingsAccountId = 456L;
        when(savingAccountRepository.findBySavingsAccountId(savingsAccountId)).thenReturn(null);

        try {
            transactionService.getTransactions(savingsAccountId, 1, 10);
        } catch (SavingDetailsNotFoundException ex) {

        }
    }
}
