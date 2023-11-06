package org.maveric.quarkus.panache.services;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountDetailsNotFoundException;
import org.maveric.quarkus.panache.model.SavingsAccount;
import org.maveric.quarkus.panache.repository.SavingsAccountRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.xml.bind.DatatypeConverter.parseInt;
import static org.maveric.quarkus.panache.common.ApiConstants.SAVING_ACCOUNTS_URL_PATH;
import static org.maveric.quarkus.panache.common.SavingsAccountConstant.*;
import static org.maveric.quarkus.panache.common.UtilsMethods.*;

/* @author meleto sofiya */
@Slf4j
@ApplicationScoped
public class SavingsAccountServices {

    public static final String QUERY_NUMERIC = " WHERE  customerId = ?1 OR customerPhone = ?1  ORDER BY savingsAccountId DESC ";

    public static final String QUERY_NOT_NUMERIC = " WHERE customerName = ?1 OR customerEmail = ?1  ORDER BY savingsAccountId DESC ";

    public static final String NON_SEARCH_QUERY = " ORDER BY savingsAccountId DESC ";
    SavingsAccountRepository savingAccountRepository;

    public SavingsAccountServices(SavingsAccountRepository savingAccountRepository) {
        this.savingAccountRepository = savingAccountRepository;
    }

    @Transactional
    public ResponseDto updateAccountsDetails(UpdateAccountsRequestDto updateAccountsRequestDto) {
        log.info("Request  ::  {}", updateAccountsRequestDto);
        ResponseDto responseDto = null;
        try {
            SavingsAccount savingAccount = savingAccountRepository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId());

            if (savingAccount == null) {
                log.error("Saving account detail not present in db");
                throw new SavingsAccountDetailsNotFoundException("Saving account details not found for this id " + updateAccountsRequestDto.getSavingAccountId());
            }

            if (updateAccountsRequestDto.getIsAllowOverDraft() == null) {
                savingAccount.setStatus(updateAccountsRequestDto.getStatus());
            } else {
                savingAccount.setIsAllowOverDraft(updateAccountsRequestDto.getIsAllowOverDraft());
                savingAccount.setOverDraftLimit(updateAccountsRequestDto.getOverDraftLimit());
            }
            savingAccount.setUpdatedDate(Instant.now());
            savingAccountRepository.persist(savingAccount);
            responseDto = getResponseStructure(SUCCESS_MSG, HttpResponseStatus.OK.code(),
                    UPDATED_SUCCESS_RESPONSE_MSG, null, SAVING_ACCOUNTS_URL_PATH);

            log.info("Response :: {} ", responseDto);
        } catch (Exception e) {
            log.error("error :: " + e.getMessage());
            throw e;
        }
        return responseDto;

    }

    public ResponseDto getSavingAccount(Integer pageNumber, Integer size, String search) {
        log.info("Request param :: page {}, size {}", pageNumber, size);
        ResponseDto responseDto = null;
        try {
            String query = null;
            PanacheQuery<SavingsAccount> queryResult = null;
            Integer page = pageNumber;
            Integer index = getPageIndexValue(page, size);
            log.info(search);
            if (search == null) {
                query = NON_SEARCH_QUERY;
                queryResult = savingAccountRepository.find(query);
            } else if (isNumeric(search)) {
                query = QUERY_NUMERIC;
                queryResult = savingAccountRepository.find(query, parseInt(search));
            } else {
                query = QUERY_NOT_NUMERIC;
                queryResult = savingAccountRepository.find(query, search);
            }

            List<SavingsAccount> savingAccountList = queryResult.page(Page.of(index, size)).list();
            if (savingAccountList.isEmpty()) {
                log.error("Saving account details not present in db");
                throw new SavingsAccountDetailsNotFoundException("Saving accounts details not found");
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("totalPages", queryResult.pageCount());
            responseData.put("totalItems", queryResult.count());
            responseData.put("currentPage", pageNumber);
            responseData.put("savingAccounts", savingAccountList);

            responseDto = getResponseStructure(SUCCESS_MSG, HttpResponseStatus.OK.code(),
                    GET_SUCCESS_RESPONSE_MSG, responseData, SAVING_ACCOUNTS_URL_PATH);
            log.info("Response :: {} ", responseDto);

        } catch (Exception e) {
            log.error("error :: " + e.getMessage());
            throw e;
        }
        return responseDto;
    }


    public ResponseDto getSavingAccountDetailBasedOnAccountId(Long accountId) {

        log.info("Request  ::  accountId {}", accountId);
        ResponseDto responseDto = null;
        try {
            SavingsAccount savingAccount = savingAccountRepository.findBySavingsAccountId(accountId);

            if (savingAccount == null) {
                log.error("Saving account detail not present in db");
                throw new SavingsAccountDetailsNotFoundException("Saving account details not found for this id " + accountId);
            }

            responseDto = getResponseStructure(SUCCESS_MSG, HttpResponseStatus.OK.code(),
                    UPDATED_SUCCESS_RESPONSE_MSG, savingAccount, SAVING_ACCOUNTS_URL_PATH);
            log.info("Response :: {} ", responseDto);
        }
        catch (SavingsAccountDetailsNotFoundException e) {
            log.error("error :: " + e.getMessage());
            throw e;
        }
        catch (Exception e) {
            log.error("error :: " + e.getMessage());
            throw e;
        }
        return responseDto;
    }

    public ResponseDto getSavingAccountDetailBasedOnCustomerId(Long customerId) {

        log.info("Request  ::  customerId {}", customerId);
        ResponseDto responseDto = null;
        try {
            SavingsAccount savingAccount = savingAccountRepository.findByCustomerId(customerId);

            if (savingAccount == null) {
                log.error("Saving account detail not present in db");
                throw new SavingsAccountDetailsNotFoundException("Saving account details not found for this customerId " + customerId);
            }

            responseDto = getResponseStructure(SUCCESS_MSG, HttpResponseStatus.OK.code(),
                    UPDATED_SUCCESS_RESPONSE_MSG, savingAccount, SAVING_ACCOUNTS_URL_PATH);
            log.info("Response :: {} ", responseDto);
        } catch (Exception e) {
            log.error("error :: " + e.getMessage());
            throw e;
        }
        return responseDto;
    }


}
