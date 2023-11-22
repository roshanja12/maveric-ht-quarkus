package org.maveric.quarkus.panache;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.SavingsAccountRequestDto;
import org.maveric.quarkus.panache.dtos.SavingsAccountResponseDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.enums.InterestCompoundPeriod;
import org.maveric.quarkus.panache.enums.SavingsAccountStatus;
import org.maveric.quarkus.panache.services.MessagingService;
import org.maveric.quarkus.panache.services.SavingsAccountServices;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.maveric.quarkus.panache.common.ApiConstants.SAVING_ACCOUNTS_URL_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
 public class SavingsAccountResourceTest {
    @InjectMock
    MessagingService messagingService;
    @InjectMock
    SavingsAccountServices services;

    private UpdateAccountsRequestDto updateAccountsRequestDto;

    private SavingsAccountRequestDto savingsAccountRequestDto;

    @BeforeEach
    void setUp() {
        updateAccountsRequestDto = new UpdateAccountsRequestDto();
        updateAccountsRequestDto.setSavingAccountId(1L);
        updateAccountsRequestDto.setStatus(SavingsAccountStatus.ACTIVE);
        updateAccountsRequestDto.setOverDraftLimit(BigDecimal.valueOf(0));
        updateAccountsRequestDto.setIsAllowOverDraft(null);

        savingsAccountRequestDto= new SavingsAccountRequestDto();
        savingsAccountRequestDto.setCustomerId(1L);
        savingsAccountRequestDto.setMinOpeningBalance(new BigDecimal("100.0"));
        savingsAccountRequestDto.setInterestCompoundPeriod(InterestCompoundPeriod.ANNUALLY);
        savingsAccountRequestDto.setOverDraftLimit(new BigDecimal("1000.0"));
        savingsAccountRequestDto.setIsAllowOverDraft(true);
    }

    @Test
     void test_update_account_details() {
        given()
                .contentType(ContentType.JSON)
                .body(updateAccountsRequestDto)
                .when()
                .put(SAVING_ACCOUNTS_URL_PATH)
                .then()
                .statusCode(HttpResponseStatus.OK.code());
    }


    @Test
    void test_create_account_details_with_no_image() {
        given()
                .formParam("image","test.jpg")
                .formParam(String.valueOf(savingsAccountRequestDto))
                .contentType(ContentType.JSON)
                .when()
                .post(SAVING_ACCOUNTS_URL_PATH)
                .then()
                .statusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
    }

    @Test
     void test_get_account_details() {
        given()
                .param("page", 1)
                .param("size", 1)
                .when()
                .get(SAVING_ACCOUNTS_URL_PATH)
                .then()
                .statusCode(200);
    }


    @Test
    public void testGetAccountDetailsBasedOnAccountId() {

        given()
                .contentType(ContentType.JSON)
                .pathParam("accountId", "1")
                .when()
                .get(SAVING_ACCOUNTS_URL_PATH+"/{accountId}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testGetAccountDetailsBasedOnCustomerId() {

        given()
                .contentType(ContentType.JSON)
                .pathParam("customerId", 1)
                .when()
                .get(SAVING_ACCOUNTS_URL_PATH+"/customer/{customerId}")
                .then()
                .statusCode(200);

    }
    @Test
    public void testCreateAccount() throws Exception {
        SavingsAccountResponseDto testResponseDto = new SavingsAccountResponseDto();
        when(services.createAccount(any(), any())).thenReturn(testResponseDto);
        doNothing().when(messagingService).savingsAccountProducer(any());
        given()
                .multiPart("image", "fileContent", "image/jpeg")
                .multiPart("savingsAccountRequestDto", "{ \"example\": \"data\" }", "application/json")
                .when().post("/api/v1/accounts/saving")
                .then().statusCode(201)
                .contentType(ContentType.JSON)
                .body("status", equalTo("success"))
                .body("message", equalTo("Savings Account Created Successfully"))
                .body("code", equalTo(201));
        verify(services, times(1)).createAccount(any(), any());
    }
}
