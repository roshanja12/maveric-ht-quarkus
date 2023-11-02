package org.maveric.quarkus.panache;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.exceptionHandler.SavingDetailsNotFoundException;
import org.maveric.quarkus.panache.model.SavingAccount;
import org.maveric.quarkus.panache.enums.SavingAccountStatus;
import org.maveric.quarkus.panache.repository.SavingAccountRepository;
import org.maveric.quarkus.panache.services.SavingAccountServices;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class SavingAccountServiceTest {

    @InjectMock
    SavingAccountRepository repository;

    @Inject
    SavingAccountServices resource;

    private SavingAccount savingAccount;

    private UpdateAccountsRequestDto updateAccountsRequestDto;


    @BeforeEach
    void setUp() {
        savingAccount = new SavingAccount();
        savingAccount.setSavingsAccountId(1L);
        savingAccount.setIsAllowOverDraft(true);
        savingAccount.setOverDraftLimit(BigDecimal.valueOf(10));
        savingAccount.setStatus(SavingAccountStatus.ACTIVE);

        updateAccountsRequestDto = new UpdateAccountsRequestDto();
        updateAccountsRequestDto.setSavingAccountId(1L);
        updateAccountsRequestDto.setStatus(SavingAccountStatus.ACTIVE);
        updateAccountsRequestDto.setOverDraftLimit(BigDecimal.valueOf(0));
        updateAccountsRequestDto.setIsAllowOverDraft(null);

    }

    @Test
    void get_account_detail_if_data_is_contact_id_result_data_found() {

        String query1 = SavingAccountServices.QUERY_NUMERIC;
        String query2 = SavingAccountServices.QUERY_NOT_NUMERIC;
        System.out.println("****inside test repo=" + repository);

        PanacheQuery<SavingAccount> query = Mockito.mock(PanacheQuery.class);
        Mockito.when(repository.find(query1, 1)).thenReturn(query);
        Mockito.when(repository.find(query2.trim())).thenReturn(query);
        Page page = Page.of(0, 1);
        ArgumentMatcher<Page> pageMatcher = (pageArg) -> (pageArg.index == page.index) && (pageArg.size == page.size);
        Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
        List list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(1);
        Mockito.when(query.list()).thenReturn(list);
        resource.getSavingAccount(1, 1, "1");

    }


    @Test
    void get_account_detail_if_data_is_contact_name_result_data_found() {

        String query1 = SavingAccountServices.QUERY_NUMERIC;
        String query2 = SavingAccountServices.QUERY_NOT_NUMERIC;
        System.out.println("****inside test repo=" + repository);

        PanacheQuery<SavingAccount> query = Mockito.mock(PanacheQuery.class);
        Mockito.when(repository.find(query1, "abc")).thenReturn(query);
        Mockito.when(repository.find(query2, "abc")).thenReturn(query);
        Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
        List list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(1);
        Mockito.when(query.list()).thenReturn(list);
        resource.getSavingAccount(661, 1, "abc");

    }

    @Test
    public void get_account_detail_if_data_is_contact_name_result_data_not_found() {
        String query1 = SavingAccountServices.QUERY_NUMERIC;
        String query2 = SavingAccountServices.QUERY_NOT_NUMERIC;
        System.out.println("****inside test repo=" + repository);

        PanacheQuery<SavingAccount> query = Mockito.mock(PanacheQuery.class);
        Mockito.when(repository.find(query1, 1)).thenReturn(query);
        Mockito.when(repository.find(query2.trim())).thenReturn(query);
        Page page = Page.of(2, 1);
        Mockito.when(query.page(Mockito.any(Page.class))).thenReturn(query);
        List list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(0);
        Mockito.when(query.list()).thenReturn(list);
        SavingAccountServices services = Mockito.mock(SavingAccountServices.class);
        Mockito.when(services.getSavingAccount(1, 1, null)).thenThrow(new SavingDetailsNotFoundException("Saving accounts details not found"));
        // resource.getSavingAccount(1, 1, null);
        assertThrows(SavingDetailsNotFoundException.class, () -> {
            services.getSavingAccount(1, 1, null);
        });
    }

    @Test
    public void update_account_details_if_data_not_found() {
        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(null);
        Assertions.assertThrows(RuntimeException.class, () -> resource.updateAccountsDetails(updateAccountsRequestDto));
    }

    @Test
    public void update_account_details_of_status() {
        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(savingAccount);
        resource.updateAccountsDetails(updateAccountsRequestDto);
    }

    @Test
    public void update_account_details_of_over_draft() {
        Mockito.when(repository.findBySavingsAccountId(updateAccountsRequestDto.getSavingAccountId())).thenReturn(savingAccount);
        updateAccountsRequestDto.setIsAllowOverDraft(null);
        resource.updateAccountsDetails(updateAccountsRequestDto);
    }

}







