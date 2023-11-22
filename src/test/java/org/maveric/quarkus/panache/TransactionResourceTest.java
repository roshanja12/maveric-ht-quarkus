package org.maveric.quarkus.panache;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.maveric.quarkus.panache.common.ApiConstants.SAVING_ACCOUNTS_URL_PATH;

@QuarkusTest
public class TransactionResourceTest {

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
    void test_withdraw() {
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


}
