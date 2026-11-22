package ma.enset.ebankservice.service;

import ma.enset.ebankservice.entity.AccountType;
import ma.enset.ebankservice.entity.BankAccount;
import ma.enset.ebankservice.exception.BankAccountNotFoundException;
import ma.enset.ebankservice.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires de BankAccountService, sans Spring ni base de données.
 *
 * @author Taoufik KEHAL
 */
@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    private static final String ACCOUNT_ID = "a1b2c3d4-0000-0000-0000-000000000001";
    private static final String UNKNOWN_ID = "inconnu-0000";

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = BankAccount.builder()
                .id(ACCOUNT_ID)
                .createdAt(LocalDateTime.now())
                .balance(new BigDecimal("1000.00"))
                .currency("MAD")
                .accountType(AccountType.CURRENT_ACCOUNT)
                .customerId(1L)
                .build();
    }

    @Test
    void shouldReturnAllAccounts() {
        // Given
        BankAccount savingAccount = BankAccount.builder()
                .id("a1b2c3d4-0000-0000-0000-000000000002")
                .balance(new BigDecimal("5000.00"))
                .currency("MAD")
                .accountType(AccountType.SAVING_ACCOUNT)
                .customerId(2L)
                .build();
        when(bankAccountRepository.findAll()).thenReturn(List.of(account, savingAccount));

        // When
        List<BankAccount> accounts = bankAccountService.getAllAccounts();

        // Then
        assertThat(accounts).hasSize(2);
        assertThat(accounts)
                .extracting(BankAccount::getAccountType)
                .containsExactly(AccountType.CURRENT_ACCOUNT, AccountType.SAVING_ACCOUNT);
        verify(bankAccountRepository).findAll();
    }

    @Test
    void shouldReturnAccountWhenIdExists() {
        // Given
        when(bankAccountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        // When
        BankAccount result = bankAccountService.getAccountById(ACCOUNT_ID);

        // Then
        assertThat(result.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(result.getBalance()).isEqualByComparingTo("1000.00");
        assertThat(result.getCustomerId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        // Given
        when(bankAccountRepository.findById(UNKNOWN_ID)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> bankAccountService.getAccountById(UNKNOWN_ID))
                .isInstanceOf(BankAccountNotFoundException.class)
                .hasMessage("Compte introuvable : " + UNKNOWN_ID)
                .hasFieldOrPropertyWithValue("id", UNKNOWN_ID);
    }
}