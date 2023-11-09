package org.maveric.quarkus.panache;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;
import org.maveric.quarkus.panache.resources.TransactionResource;
import org.maveric.quarkus.panache.services.TransactionService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
@QuarkusTest
public class TransactionResourceTest {

    private TransactionRequestDto transactionRequestDto;

  @BeforeEach
    void setUp()
  {
      transactionRequestDto =new TransactionRequestDto();
      transactionRequestDto.setAmount(new BigDecimal("100"));
      transactionRequestDto.setAccountId(1L);
  }

  @Test
    void test_withdraw(){
      given()
              .contentType(ContentType.JSON)
              .body(transactionRequestDto)
              .when()
              .put()
              .then()
              .statusCode(HttpResponseStatus.OK.code());


  }




}
