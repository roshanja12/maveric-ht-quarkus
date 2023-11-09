package org.maveric.quarkus.panache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountDetailsNotFoundException;
import org.maveric.quarkus.panache.model.SavingsAccount;
import org.maveric.quarkus.panache.repository.SavingsAccountRepository;
import org.maveric.quarkus.panache.services.TransactionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.maveric.quarkus.panache.dtos.TransactionResponseDto;
import org.maveric.quarkus.panache.model.Transaction;
import org.maveric.quarkus.panache.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private SavingsAccountRepository savingAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(transactionRepository,savingAccountRepository);
    }
    @Test
    void testDeposit(){
        TransactionRequestDto requestDto=new TransactionRequestDto();
        requestDto.setAccountId(1L);
        requestDto.setAmount(new BigDecimal("500"));
        SavingsAccount mockAccount=new SavingsAccount();
        mockAccount.setSavingsAccountId(1L);
        mockAccount.setBalance(new BigDecimal("1000"));
        when(savingAccountRepository.findBySavingsAccountId(requestDto.getAccountId())).thenReturn(mockAccount);
        transactionService.deposit(requestDto);
        assertEquals(new BigDecimal("1500"),mockAccount.getBalance());
        Mockito.verify(savingAccountRepository).persist(mockAccount);
        Mockito.verify(transactionRepository).persist(Mockito.any(Transaction.class));

    }

    @Test
    public void testWithdraw() {
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAccountId(2L);
        requestDto.setAmount(new BigDecimal("200.00"));
        SavingsAccount expectedSavingAccount = new SavingsAccount();
        expectedSavingAccount.setSavingsAccountId(2L);
        expectedSavingAccount.setBalance(new BigDecimal("500.00"));
        when(savingAccountRepository.findBySavingsAccountId(requestDto.getAccountId())).thenReturn(expectedSavingAccount);
        transactionService.withdraw(requestDto);
        assertEquals(new BigDecimal("300.00"),expectedSavingAccount.getBalance());
        Mockito.verify(transactionRepository).persist(Mockito.any(Transaction.class));
        Mockito.verify(savingAccountRepository).persist(expectedSavingAccount);
    }

    @Test
    public void testDepositAccountNotFound(){
        TransactionRequestDto requestDto= new TransactionRequestDto();
        requestDto.setAccountId(999L);
        requestDto.setAmount(new BigDecimal("100"));
        when(savingAccountRepository.findBySavingsAccountId(requestDto.getAccountId())).thenReturn(null);

    }


    @Test
    public void testGetTransactions() {
        Long savingsAccountId = 123L;
        SavingsAccount savingAccount = new SavingsAccount();
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
        } catch (SavingsAccountDetailsNotFoundException ex) {

        }
    }
}
