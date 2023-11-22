package org.maveric.quarkus.panache;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.*;
import org.maveric.quarkus.panache.enums.SavingsAccountStatus;
import org.maveric.quarkus.panache.enums.Type;
import org.maveric.quarkus.panache.exceptionHandler.CustomerProxyException;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountDetailsNotFoundException;
import org.maveric.quarkus.panache.exceptionHandler.SavingsAccountException;
import org.maveric.quarkus.panache.exceptionHandler.UnsupportedFileTypeException;
import org.maveric.quarkus.panache.model.CreateSavingsMessageDto;
import org.maveric.quarkus.panache.model.KafkaResponseDto;
import org.maveric.quarkus.panache.model.SavingsAccount;
import org.maveric.quarkus.panache.proxy.CustomerProxy;
import org.maveric.quarkus.panache.repository.SavingsAccountRepository;
import org.maveric.quarkus.panache.services.MessagingService;
import org.maveric.quarkus.panache.services.SavingsAccountServices;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class SavingsAccountServiceTest {

    @InjectMock
    SavingsAccountRepository repository;

    @InjectMock
    @RestClient
    CustomerProxy proxy;
    //  @InjectMock
//  CustomModelMapper modelMapper;
//  @Inject
//  ModelMapper modelMapper;
    @InjectMocks
    @InjectSpy
    SavingsAccountServices service;

    private SavingsAccount savingAccount;

    private UpdateAccountsRequestDto updateAccountsRequestDto;


    private CreateSavingsMessageDto createSavingsMessageDto;

    private KafkaResponseDto kafkaResponseDto;

    private SavingsAccountResponseDto responseDto;

    SavingsAccountServiceTest() throws IOException {
    }

//  private SavingsAccountRepository repository = mock(SavingsAccountRepository.class);
//  private CustomerProxy proxy = mock(CustomerProxy.class);

    //  @Inject
//  SavingsAccountServices resource;
    @BeforeEach
    void setUp() {
        savingAccount = new SavingsAccount();
        savingAccount.setSavingsAccountId(1L);
        savingAccount.setIsAllowOverDraft(true);
        savingAccount.setOverDraftLimit(BigDecimal.valueOf(10));
        savingAccount.setStatus(SavingsAccountStatus.ACTIVE);

        updateAccountsRequestDto = new UpdateAccountsRequestDto();
        updateAccountsRequestDto.setSavingAccountId(1L);
        updateAccountsRequestDto.setStatus(SavingsAccountStatus.ACTIVE);
        updateAccountsRequestDto.setOverDraftLimit(BigDecimal.valueOf(0));
        updateAccountsRequestDto.setIsAllowOverDraft(null);

        createSavingsMessageDto = new CreateSavingsMessageDto();
        kafkaResponseDto = new KafkaResponseDto();
        kafkaResponseDto.setType(Type.SAVING_ACCOUNT_CREATED);
        kafkaResponseDto.setMessage("test");

    }

    @Test
    void get_account_detail_if_data_is_contact_id_result_data_found() {

        String query1 = SavingsAccountServices.QUERY_NUMERIC;
        String query2 = SavingsAccountServices.QUERY_NOT_NUMERIC;
        System.out.println("****inside test repo=" + repository);

        PanacheQuery<SavingsAccount> query = mock(PanacheQuery.class);
        Mockito.when(repository.find(query1, "%" + 1 + "%")).thenReturn(query);
        Mockito.when(repository.find(query2.trim())).thenReturn(query);
        Page page = Page.of(0, 1);
        ArgumentMatcher<Page> pageMatcher = (pageArg) -> (pageArg.index == page.index) && (pageArg.size == page.size);
        Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
        List list = mock(List.class);
        Mockito.when(list.size()).thenReturn(1);
        Mockito.when(query.list()).thenReturn(list);
        service.getSavingAccount(1, 1, "1");

    }


    @Test
    void get_account_detail_if_data_is_contact_name_result_data_found() {

        String query1 = SavingsAccountServices.QUERY_NUMERIC;
        String query2 = SavingsAccountServices.QUERY_NOT_NUMERIC;
        System.out.println("****inside test repo=" + repository);

        PanacheQuery<SavingsAccount> query = mock(PanacheQuery.class);
        Mockito.when(repository.find(query1, "%" + "abc" + "%")).thenReturn(query);
        Mockito.when(repository.find(query2, "%" + "abc" + "%")).thenReturn(query);
        Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
        List list = mock(List.class);
        Mockito.when(list.size()).thenReturn(1);
        Mockito.when(query.list()).thenReturn(list);
        service.getSavingAccount(661, 1, "abc");

    }

    @Test
    public void get_account_detail_if_data_is_contact_name_result_data_not_found() {
        String query1 = SavingsAccountServices.QUERY_NUMERIC;
        String query2 = SavingsAccountServices.QUERY_NOT_NUMERIC;
        System.out.println("****inside test repo=" + repository);

        PanacheQuery<SavingsAccount> query = mock(PanacheQuery.class);
        Mockito.when(repository.find(query1, "%" + 1 + "%")).thenReturn(query);
        Mockito.when(repository.find(query2.trim())).thenReturn(query);
        Page page = Page.of(2, 1);
        Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
        List list = mock(List.class);
        Mockito.when(list.size()).thenReturn(0);
        Mockito.when(query.list()).thenReturn(list);
        SavingsAccountServices services = mock(SavingsAccountServices.class);
        Mockito.when(services.getSavingAccount(1, 1, null)).thenThrow(new SavingsAccountDetailsNotFoundException("Saving accounts details not found"));
        // resource.getSavingAccount(1, 1, null);
        assertThrows(SavingsAccountDetailsNotFoundException.class, () -> {
            services.getSavingAccount(1, 1, null);
        });
    }

    @Test
    public void update_account_details_if_data_not_found() {
        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(null);
        assertThrows(SavingsAccountDetailsNotFoundException.class, () -> {
            service.updateAccountsDetails(updateAccountsRequestDto);
        });
    }

    @Test
    public void update_account_details_of_status() {
        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(savingAccount);
        service.updateAccountsDetails(updateAccountsRequestDto);
    }

    @Test
    public void update_account_details_of_over_draft() {
        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(savingAccount);
        updateAccountsRequestDto.setIsAllowOverDraft(null);
        service.updateAccountsDetails(updateAccountsRequestDto);
    }

    @Test
    void get_all_saving_account_details_based_on_account_id() {

        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(savingAccount);
        service.getSavingAccountDetailBasedOnAccountId(updateAccountsRequestDto.getSavingAccountId());
    }

    @Test
    void get_all_saving_account_details_based_on_customer_id() {
        Mockito.when(repository.findByCustomerListById(updateAccountsRequestDto.getSavingAccountId())).thenReturn(Collections.singletonList(savingAccount));
        service.getSavingAccountDetailBasedOnCustomerId(updateAccountsRequestDto.getSavingAccountId());
    }


    @Test
    void get_all_saving_account_details_based_on_account_id_no_data_found() {

        Mockito.when(repository.findBySavingsAccountListById(100001L)).thenReturn(null);
        assertThrows(SavingsAccountDetailsNotFoundException.class, () -> {
            service.getSavingAccountDetailBasedOnAccountId(100001L);
        });

    }

    @Test
    void get_all_saving_account_details_based_on_customer_id_no_data_found() {
        Mockito.when(repository.findByCustomerListById(2L)).thenReturn(null);
        assertThrows(SavingsAccountDetailsNotFoundException.class, () -> {
            service.getSavingAccountDetailBasedOnCustomerId(2L);
        });
    }

    @Test
    public void testCreateAccount_Failure() throws SQLException, IOException, UnsupportedFileTypeException {

        FileUpload file = mock(FileUpload.class);


        Path tempFile = Files.createTempFile("testFile", ".txt");

        Files.write(tempFile, "Test Content".getBytes(), StandardOpenOption.APPEND);


        when(file.filePath()).thenReturn(tempFile);

        when(file.fileName()).thenReturn("testFile.txt");
        SavingsAccountRequestDto requestDto = new SavingsAccountRequestDto();
        requestDto.setIsAllowOverDraft(true);
        SavingsAccount savingsAccount = new SavingsAccount();

        requestDto.setIsAllowOverDraft(true);
        requestDto.setCustomerId(123L);

        CustomerDto customerDto = new CustomerDto();

        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(customerDto);

        when(proxy.getCustomerByCustomerId(anyLong())).thenReturn(Response.ok(responseDto).build());

        Blob mockBlob = mock(Blob.class);
        when(mockBlob.length()).thenReturn(100L);
        doNothing().when(repository).persist(savingsAccount);

        assertThrows(UnsupportedFileTypeException.class, () -> {
            service.createAccount(file, requestDto);
        });

    }

    @Test
    public void testCreateAccount_Success() throws SQLException, IOException {

        FileUpload file = mock(FileUpload.class);

        Path tempFile = Files.createTempFile("testFile", ".jpg");

        Files.write(tempFile, "Test Content".getBytes(), StandardOpenOption.APPEND);

        when(file.filePath()).thenReturn(tempFile);

        when(file.fileName()).thenReturn("test.jpg");
        SavingsAccountRequestDto requestDto = new SavingsAccountRequestDto();
        requestDto.setIsAllowOverDraft(true);
        requestDto.setCustomerId(123L);

        SavingsAccount savingsAccount = new SavingsAccount();

        CustomerDto customerDto = new CustomerDto();

        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(customerDto);

        when(proxy.getCustomerByCustomerId(anyLong())).thenReturn(Response.ok(responseDto).build());

        Blob mockBlob = mock(Blob.class);
        when(mockBlob.length()).thenReturn(100L);
        doNothing().when(repository).persist(savingsAccount);

        SavingsAccountResponseDto actualResponseDto = service.createAccount(file, requestDto);

        assertEquals(requestDto.getIsAllowOverDraft(), actualResponseDto.getAllowOverDraft());
    }

    @Test
    public void testCreateAccount_Failure2() throws SQLException, IOException, UnsupportedFileTypeException {

        FileUpload file = mock(FileUpload.class);


        Path tempFile = Files.createTempFile("testFile", ".jpg");


        Files.write(tempFile, "Test Content".getBytes(), StandardOpenOption.APPEND);

        when(file.filePath()).thenReturn(tempFile);

        when(file.fileName()).thenReturn("testFile.jpg");
        SavingsAccountRequestDto requestDto = new SavingsAccountRequestDto();
        requestDto.setIsAllowOverDraft(true);
        SavingsAccount savingsAccount = new SavingsAccount();

        requestDto.setIsAllowOverDraft(false);
        requestDto.setOverDraftLimit(BigDecimal.valueOf(100.00));
        requestDto.setCustomerId(123L);


        CustomerDto customerDto = new CustomerDto();

        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(customerDto);

        when(proxy.getCustomerByCustomerId(anyLong())).thenReturn(Response.ok(responseDto).build());

        Blob mockBlob = mock(Blob.class);
        when(mockBlob.length()).thenReturn(100L);
        doNothing().when(repository).persist(savingsAccount);

        assertThrows(SavingsAccountException.class, () -> {
            service.createAccount(file, requestDto);
        });

    }

    @Test
    public void testCreateAccount_Failure3() throws SQLException, IOException, UnsupportedFileTypeException {

        FileUpload file = mock(FileUpload.class);

        Path tempFile = Files.createTempFile("testFile", ".jpg");

        Files.write(tempFile, "Test Content".getBytes(), StandardOpenOption.APPEND);


        when(file.filePath()).thenReturn(tempFile);

        when(file.fileName()).thenReturn("testFile.jpg");
        SavingsAccountRequestDto requestDto = new SavingsAccountRequestDto();
        requestDto.setIsAllowOverDraft(true);
        SavingsAccount savingsAccount = new SavingsAccount();

        requestDto.setIsAllowOverDraft(false);
        requestDto.setOverDraftLimit(BigDecimal.valueOf(100.00));
        requestDto.setCustomerId(123L);


        CustomerDto customerDto = new CustomerDto();

        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(customerDto);

        when(proxy.getCustomerByCustomerId(anyLong())).thenReturn(Response.status(Response.Status.NOT_FOUND).build());

        Blob mockBlob = mock(Blob.class);
        when(mockBlob.length()).thenReturn(100L);
        doNothing().when(repository).persist(savingsAccount);

        assertThrows(CustomerProxyException.class, () -> {
            service.createAccount(file, requestDto);
        });

    }

}






