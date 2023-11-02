package org.maveric.quarkus.panache.services;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.exceptionHandler.SavingDetailsNotFoundException;
import org.maveric.quarkus.panache.model.SavingAccount;
import org.maveric.quarkus.panache.repository.SavingAccountRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static jakarta.xml.bind.DatatypeConverter.parseInt;
import static org.maveric.quarkus.panache.common.SavingAccountConstant.*;
import static org.maveric.quarkus.panache.common.UtilsMethods.getResponseStructure;
import static org.maveric.quarkus.panache.common.UtilsMethods.isNumeric;

/* @author meleto sofiya */
@Slf4j
@ApplicationScoped
public class SavingAccountServices {

    public static final String QUERY_NUMERIC=" WHERE  customerId = ?1 OR customerPhone = ?1 OR ORDER BY savingsAccountId DESC ";

    public static final String QUERY_NOT_NUMERIC=" WHERE customerName = ?1 OR customerEmail = ?1  ORDER BY savingsAccountId DESC ";

    @Inject
    SavingAccountRepository savingAccountRepository;


    @Transactional
    public ResponseDto UpdateAccountsDetails(UpdateAccountsRequestDto updateAccountsRequestDto) {
        log.info("SavingAccountServices :: getSavingAccount :: started time " + Instant.now());
        log.info("Request  ::  {}", updateAccountsRequestDto);
        ResponseDto responseDto = null;
        try {
            SavingAccount savingAccount = savingAccountRepository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId());

            if (savingAccount == null) {
                throw new SavingDetailsNotFoundException("Saving account details not found for this id " + updateAccountsRequestDto.getSavingAccountId());
            }

            if (updateAccountsRequestDto.getIsAllowOverDraft() == null) {
                savingAccount.setStatus(updateAccountsRequestDto.getStatus());
            } else {
                savingAccount.setIsAllowOverDraft(updateAccountsRequestDto.getIsAllowOverDraft());
                savingAccount.setOverDraftLimit(updateAccountsRequestDto.getOverDraftLimit());
            }
            savingAccountRepository.persist(savingAccount);
            responseDto = getResponseStructure(SUCCESS_MSG, HttpResponseStatus.OK.code(),
                    UPDATED_SUCCESS_RESPONSE_MSG, null, GET_ACCOUNTS_PATH);

            log.info("Response :: {} ", responseDto);
            log.info("SavingAccountServices calling :: getSavingAccount :: end time " + Instant.now());
        } catch (Exception e) {
            log.error("error :: " + e.getMessage());
            log.info("SavingAccountServices :: getSavingAccount :: end time " + Instant.now());
            throw e;
        }
        return responseDto;

    }

    public ResponseDto getSavingAccount(Integer pageNumber, Integer pageSize, String search) {
        log.info("SavingAccountServices :: getSavingAccount :: started time " + Instant.now());
        log.info("Request param :: page {}, size {}", pageNumber, pageSize);
        System.out.println("inside src saving repository="+savingAccountRepository);
        ResponseDto responseDto = null;
        try {
            String query = null;
            PanacheQuery<SavingAccount> queryResult = null;
            Integer page = pageNumber;
            Integer index = page;
            if (page != 0) {
                index = page - 1;
            }

            Integer size = pageSize;

            if (index != 0) {
                index *= size;
            }
            if (isNumeric(search)) {
                //  WHERE  customerId = ?2 OR customerPhone = ?2 OR ORDER BY savingsAccountId DESC
                System.out.println("****inside where block");
                query = QUERY_NUMERIC;
                System.out.println("**search="+parseInt(search));
                queryResult = savingAccountRepository.find(query,parseInt(search));
            } else {
                System.out.println("****insid eorder by query block");
                query = QUERY_NOT_NUMERIC;
                queryResult = savingAccountRepository.find(query,search);
                //queryResult = savingAccountRepository.find(query.trim());
                System.out.println("query result="+queryResult);
            }

            log.info("Request param :: page {}, size {}", page, size);
            System.out.println("*** index="+index+",size="+size);
            List<SavingAccount> savingAccountList = queryResult.page(Page.of(index, size)).list();
         //   ArgumentMatcher<Page>pageMatcher=(pageArg)->(pageArg.index==page.index) && (pageArg.size==page.size);
            //        Mockito.when( query.page(Mockito.any(Page.class))).thenReturn(query);

            if (savingAccountList.size() == 0) {
                throw new SavingDetailsNotFoundException("Saving accounts details not found");
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("totalPages", queryResult.pageCount());
            responseData.put("totalItems", queryResult.count());
            responseData.put("currentPage", pageNumber);
            responseData.put("savingAccounts", savingAccountList);

            responseDto = getResponseStructure(SUCCESS_MSG, HttpResponseStatus.OK.code(),
                    GET_SUCCESS_RESPONSE_MSG, responseData, GET_ACCOUNTS_PATH);
            log.info("Response :: {} ", responseDto);
            log.info("SavingAccountServices calling :: getSavingAccount :: end time " + Instant.now());

        } catch (Exception e) {
            log.info("error :: " + e.getMessage());
            log.info("SavingAccountServices :: getSavingAccount :: end time " + Instant.now());
            throw e;
        }
        return responseDto;
    }


}
