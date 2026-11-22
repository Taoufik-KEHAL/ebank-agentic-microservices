package ma.enset.ebankservice.controller;

import ma.enset.ebankservice.entity.AccountType;
import ma.enset.ebankservice.entity.BankAccount;
import ma.enset.ebankservice.exception.BankAccountNotFoundException;
import ma.enset.ebankservice.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de la couche web de BankAccountRestController, avec un service simulé.
 *
 * @author Taoufik KEHAL
 */
@WebMvcTest(BankAccountRestController.class)
class BankAccountRestControllerTest {

    private static final String ACCOUNT_ID = "a1b2c3d4-0000-0000-0000-000000000001";
    private static final String UNKNOWN_ID = "inconnu-0000";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankAccountService bankAccountService;

    private BankAccount currentAccount;
    private BankAccount savingAccount;

    @BeforeEach
    void setUp() {
        currentAccount = BankAccount.builder()
                .id(ACCOUNT_ID)
                .createdAt(LocalDateTime.now())
                .balance(new BigDecimal("15000.00"))
                .currency("MAD")
                .accountType(AccountType.CURRENT_ACCOUNT)
                .customerId(1L)
                .build();

        savingAccount = BankAccount.builder()
                .id("a1b2c3d4-0000-0000-0000-000000000002")
                .createdAt(LocalDateTime.now())
                .balance(new BigDecimal("50000.00"))
                .currency("MAD")
                .accountType(AccountType.SAVING_ACCOUNT)
                .customerId(1L)
                .build();
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        // Given
        when(bankAccountService.getAllAccounts()).thenReturn(List.of(currentAccount, savingAccount));

        // When / Then
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].accountType").value("CURRENT_ACCOUNT"))
                .andExpect(jsonPath("$[1].accountType").value("SAVING_ACCOUNT"));
    }

    @Test
    void shouldReturnEmptyListWhenNoAccounts() throws Exception {
        // Given
        when(bankAccountService.getAllAccounts()).thenReturn(List.of());

        // When / Then
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnAccountWhenIdExists() throws Exception {
        // Given
        when(bankAccountService.getAccountById(ACCOUNT_ID)).thenReturn(currentAccount);

        // When / Then
        mockMvc.perform(get("/accounts/{id}", ACCOUNT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ACCOUNT_ID))
                .andExpect(jsonPath("$.accountType").value("CURRENT_ACCOUNT"))
                .andExpect(jsonPath("$.balance").value(15000.0))
                .andExpect(jsonPath("$.currency").value("MAD"))
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void shouldReturn404WhenAccountNotFound() throws Exception {
        // Given
        when(bankAccountService.getAccountById(UNKNOWN_ID))
                .thenThrow(new BankAccountNotFoundException(UNKNOWN_ID));

        // When / Then
        mockMvc.perform(get("/accounts/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Compte introuvable : " + UNKNOWN_ID));
    }
}