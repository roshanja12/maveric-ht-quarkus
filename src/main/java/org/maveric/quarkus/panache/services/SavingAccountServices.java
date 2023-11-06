package org.maveric.quarkus.panache.services;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.SavingAccountRequestDto;
import org.maveric.quarkus.panache.dtos.SavingAccountResponseDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.enums.SavingAccountStatus;
import org.maveric.quarkus.panache.exceptionHandler.CustomerProxyException;
import org.maveric.quarkus.panache.exceptionHandler.SavingDetailsNotFoundException;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountCreationException;
import org.maveric.quarkus.panache.exceptionHandler.UnsupportedFileTypeException;
import org.maveric.quarkus.panache.model.SavingAccount;
import org.maveric.quarkus.panache.proxy.CustomerProxy;
import org.maveric.quarkus.panache.repository.SavingAccountRepository;
import org.modelmapper.ModelMapper;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.xml.bind.DatatypeConverter.parseInt;
import static org.maveric.quarkus.panache.common.SavingAccountConstant.*;
import static org.maveric.quarkus.panache.common.UtilsMethods.*;

/* @author meleto sofiya */
@Slf4j
@ApplicationScoped
public class SavingAccountServices {
    @RestClient
    @Inject
    private CustomerProxy proxy;

    public static final String QUERY_NUMERIC = " WHERE  customerId = ?1 OR customerPhone = ?1  ORDER BY savingsAccountId DESC ";

    public static final String QUERY_NOT_NUMERIC = " WHERE customerName = ?1 OR customerEmail = ?1  ORDER BY savingsAccountId DESC ";

    public static final String NON_SEARCH_QUERY = " ORDER BY savingsAccountId DESC ";
    SavingAccountRepository savingAccountRepository;

    public SavingAccountServices(SavingAccountRepository savingAccountRepository) {
        this.savingAccountRepository = savingAccountRepository;
    }

    @Transactional
    public ResponseDto updateAccountsDetails(UpdateAccountsRequestDto updateAccountsRequestDto) {
        log.info("Request  ::  {}", updateAccountsRequestDto);
        ResponseDto responseDto = null;
        try {
            SavingAccount savingAccount = savingAccountRepository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId());

            if (savingAccount == null) {
                log.error("Saving account detail not present in db");
                throw new SavingDetailsNotFoundException("Saving account details not found for this id " + updateAccountsRequestDto.getSavingAccountId());
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
            PanacheQuery<SavingAccount> queryResult = null;
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

            List<SavingAccount> savingAccountList = queryResult.page(Page.of(index, size)).list();
            if (savingAccountList.isEmpty()) {
                log.error("Saving account details not present in db");
                throw new SavingDetailsNotFoundException("Saving accounts details not found");
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

    public SavingAccountResponseDto createAccount(FileUpload file, SavingAccountRequestDto savingAccountRequestDto) throws SQLException, IOException, CustomerProxyException {

        SavingAccount savingAccount = createAccountObject(file, savingAccountRequestDto);

        SavingAccountResponseDto responseDto = null;
        try {
            savingAccountRepository.persist(savingAccount);
            ModelMapper modelMapper = new ModelMapper();
            responseDto = modelMapper.map(savingAccount, SavingAccountResponseDto.class);
            log.info("Saving account created successfully.");
            return responseDto;
        } catch (Exception e) {
            log.error("Error creating saving account: " + e.getMessage());
            throw e;
        }

    }

    private SavingAccount createAccountObject(FileUpload file, SavingAccountRequestDto savingAccountRequestDto) throws IOException, SQLException, CustomerProxyException {
        SavingAccount savingAccount = null;
        validateCustomerProxy(savingAccountRequestDto.getCustomerId());
        validateFile(file);
        log.info("Attempting to create an SavingAccount object");
        try {
            ModelMapper modelMapper = new ModelMapper();
            savingAccount = modelMapper.map(savingAccountRequestDto, SavingAccount.class);
            savingAccount.setStatus(SavingAccountStatus.ACTIVE);
            savingAccount.setCreatedDate(Instant.now());
            savingAccount.setUpdatedDate(Instant.now());
            savingAccount.setDocument(createDocumentBlob(file));
            savingAccount.setDocumentName(file.fileName());
            if (!savingAccountRequestDto.getIsAllowOverDraft() && savingAccountRequestDto.getOverDraftLimit() != null) {
                log.error("Attempted to set overdraft limit when not allowed.");
                throw new SavingsAccountCreationException("Not able to add OverDraftLimit");
            }
        } catch (Exception e) {
            log.error("Error creating an Account Object: " + e.getMessage());
            throw e;
        }
        return savingAccount;
    }

    private Blob createDocumentBlob(FileUpload file) throws SQLException, IOException {
        try {
            log.info("Attempting to create document blob");
            Path filePath = file.filePath();
            byte[] fileBytes = Files.readAllBytes(filePath);
            return new SerialBlob(fileBytes);
        } catch (Exception e) {
            log.error("Error creating document blob: " + e.getMessage());
            throw e;
        }
    }

    private void validateFile(FileUpload file) throws IOException {
        try {
            log.info("Validating the uploaded File");
            if (file == null || file.fileName().equalsIgnoreCase("")) {
                log.error("File is null or empty");
                throw new UnsupportedFileTypeException("File cannot be null");
            }
            if (!file.fileName().toLowerCase().endsWith(".jpeg") && !file.fileName().toLowerCase().endsWith(".jpg")) {
                log.error("Unsupported file type");
                throw new UnsupportedFileTypeException("File Type Not Supported");
            }
            if (Files.size(file.filePath()) > 1024 * 1024) {
                log.error("File size exceeds maximum limit");
                throw new UnsupportedFileTypeException("File Size Exceeds Maximum Limit");
            }
        } catch (Exception e) {
            log.error("Error validating file: " + e.getMessage());
            throw e;
        }

    }

    private void validateCustomerProxy(Long customerId) throws CustomerProxyException {
        try {
            log.info("Validating the Customer by connecting to Customer Service");
            Response response = proxy.getCustomerByCustomerId(customerId);
        } catch (Exception e) {
            log.error("Validating the Customer failed");
            throw new CustomerProxyException("customer not found for id=" + customerId, e);
        }
    }

}
