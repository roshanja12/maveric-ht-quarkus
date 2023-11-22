package org.maveric.quarkus.panache;

import io.netty.handler.codec.http.HttpResponseStatus;

import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;

import org.maveric.quarkus.panache.resources.TransactionResource;
import org.maveric.quarkus.panache.services.TransactionService;
import org.mockito.Mockito;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.maveric.quarkus.panache.common.ApiConstants.SAVING_ACCOUNTS_URL_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest

public class TransactionResourceTest {

    @Mock
    private TransactionService transactionService;
    @InjectMock
    private TransactionResource transactionResource;
    private TransactionRequestDto transactionRequestDto;

    @BeforeEach
    void setUp() {
        transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAmount(new BigDecimal("100"));
        transactionRequestDto.setAccountId(1L);
    }

    @Test
    void test_deposit() {
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequestDto)
                .when()
                .put(SAVING_ACCOUNTS_URL_PATH + "/deposits")
                .then()
                .statusCode(HttpResponseStatus.OK.code());


    }


    @Test
    void test_withdraw_api() {
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequestDto)
                .when()
                .put(SAVING_ACCOUNTS_URL_PATH + "/withdraws")
                .then()
                .statusCode(HttpResponseStatus.OK.code());


    }


    @Test
    void test_get_transactions() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("savingsAccountId", 1L)
                .param("pageNumber", 1)
                .param("pageSize", 10)
                .when()
                .get(SAVING_ACCOUNTS_URL_PATH + "/{savingsAccountId}/transactions")
                .then()
                .statusCode(200);


    }


  @Test
    void test_withdraw(){
      TransactionRequestDto requestDto = new TransactionRequestDto();
      Mockito.doNothing().when(transactionService).withdraw(requestDto);
      Response response = transactionResource.withdraw(requestDto);
      assertEquals(200, response.getStatus());
      ResponseDto responseEntity = (ResponseDto) response.getEntity();
      assertEquals("success", responseEntity.getStatus());
      assertEquals("Withdraw Successful", responseEntity.getMessage());
      assertEquals(201, responseEntity.getCode());
      assertEquals(null, responseEntity.getError());
      assertEquals("/api/v1/accounts/saving", responseEntity.getPath());
      verify(transactionService).withdraw(requestDto);
  }

    @Test
    void testGetTransactionHistories() {
        // Mock data
        Long savingsAccountId = 1L;
        int pageNumber = 1;
        int pageSize = 10;
        List<TransactionResponseDto> mockTransactionList = Collections.singletonList(new TransactionResponseDto(/* provide necessary data */));
        when(transactionService.getTransactions(anyLong(), eq(pageNumber), eq(pageSize)))
                .thenReturn(mockTransactionList);
        Response response = transactionResource.getTransactionHistories(savingsAccountId, pageNumber, pageSize);
        assertEquals(200, response.getStatus());
        List<TransactionResponseDto> responseEntity = (List<TransactionResponseDto>) response.getEntity();
        assertEquals(mockTransactionList, responseEntity);
        verify(transactionService).getTransactions(savingsAccountId, pageNumber, pageSize);
    }

}
