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
import org.maveric.quarkus.panache.common.UtilsMethods;
import org.maveric.quarkus.panache.dtos.*;
import org.maveric.quarkus.panache.enums.SavingsAccountStatus;
import org.maveric.quarkus.panache.exceptionHandler.CustomerProxyException;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountDetailsNotFoundException;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountException;
import org.maveric.quarkus.panache.exceptionHandler.UnsupportedFileTypeException;
import org.maveric.quarkus.panache.model.SavingsAccount;
import org.maveric.quarkus.panache.proxy.CustomerProxy;
import org.maveric.quarkus.panache.repository.SavingsAccountRepository;
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

  @RestClient
  @Inject
  CustomerProxy proxy;
  @Inject
  ModelMapper modelMapper;
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
    } catch (SavingsAccountDetailsNotFoundException e) {
      log.error("error :: " + e.getMessage());
      throw e;
    } catch (Exception e) {
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

  @Transactional
  public SavingsAccountResponseDto createAccount(FileUpload file, SavingsAccountRequestDto savingsAccountRequestDto) throws SQLException, IOException, CustomerProxyException {
    log.info("Attempting to create an SavingAccount ");
    SavingsAccount savingsAccount = createAccountObject(file, savingsAccountRequestDto);
    SavingsAccountResponseDto responseDto = null;
    try {
      savingAccountRepository.persist(savingsAccount);
      responseDto = modelMapper.map(savingsAccount, SavingsAccountResponseDto.class);
      log.info("Saving account created successfully.");
      return responseDto;
    } catch (Exception e) {
      log.error("Error creating saving account: " + e.getMessage(), e);
      throw new SavingsAccountException("Not able to create Savings Account");
    }
  }

   private SavingsAccount createAccountObject(FileUpload file, SavingsAccountRequestDto savingsAccountRequestDto) throws IOException, SQLException, CustomerProxyException {
     log.info("Attempting to create an SavingAccount object");
     CustomerDto customerDto=validateCustomerProxy(savingsAccountRequestDto.getCustomerId());
     validateFile(file);
     Blob blob = createDocumentBlob(file);
     SavingsAccount savingsAccount  = UtilsMethods.toSavingAccount(savingsAccountRequestDto, file.fileName(), blob);
    savingsAccount.setCustomerName(customerDto.getFirstName()+" "+customerDto.getLastName());
    savingsAccount.setCustomerPhone(customerDto.getPhoneNumber());
    savingsAccount.setCustomerEmail(customerDto.getEmail());
     if (!savingsAccountRequestDto.getIsAllowOverDraft() && savingsAccountRequestDto.getOverDraftLimit() != null) {
       log.error("Attempted to set overdraft limit when not allowed.");
       throw new SavingsAccountException("Not able to add OverDraftLimit");
     }

     return savingsAccount;
   }

  private Blob createDocumentBlob(FileUpload file) throws SQLException, IOException {
    try {
      log.info("Attempting to create document blob");
      Path filePath = file.filePath();
      byte[] fileBytes = Files.readAllBytes(filePath);
      return new SerialBlob(fileBytes);
    } catch (Exception e) {
      log.error("Error creating document blob: " + e.getMessage());
      throw new SavingsAccountException("Uploaded file not in the required format");
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

  private CustomerDto validateCustomerProxy(Long customerId) throws CustomerProxyException {
    try {
      log.info("Validating the Customer by connecting to Customer Service");
      Response response = proxy.getCustomerByCustomerId(customerId);
      ResponseDto responseDto=response.readEntity(ResponseDto.class);
      CustomerDto customerDto= modelMapper.map(responseDto.getData(),CustomerDto.class);
      log.info("Validating the Customer by connecting to Customer Service");
      return customerDto;
    } catch (Exception e) {
      log.error("Validating the Customer failed");
      throw new CustomerProxyException("customer not found for id=" + customerId, e);
    }
  }

}


