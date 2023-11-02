package org.maveric.quarkus.panache;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.model.SavingAccountStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.maveric.quarkus.panache.common.SavingAccountConstant.SAVING_ACCOUNTS_URL_PATH;
import static org.maveric.quarkus.panache.common.SavingAccountConstant.SAVING_ACCOUNTS_URL_PATH;

@QuarkusTest
 public class SavingAccountResourceTest {

    private UpdateAccountsRequestDto updateAccountsRequestDto;

    @BeforeEach
    void setUp() {
        updateAccountsRequestDto = new UpdateAccountsRequestDto();
        updateAccountsRequestDto.setSavingAccountId(1L);
        updateAccountsRequestDto.setStatus(SavingAccountStatus.ACTIVE);
        updateAccountsRequestDto.setOverDraftLimit(BigDecimal.valueOf(0));
        updateAccountsRequestDto.setIsAllowOverDraft(null);
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
     void test_get_account_details() {
        given()
                .param("page", 0)
                .param("size", 1)
                .when()
                .get(SAVING_ACCOUNTS_URL_PATH)
                .then()
                .statusCode(400);
    }

}