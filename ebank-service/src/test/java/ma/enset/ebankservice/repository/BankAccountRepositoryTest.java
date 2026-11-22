package ma.enset.ebankservice.repository;

import ma.enset.ebankservice.entity.AccountType;
import ma.enset.ebankservice.entity.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test d'intégration JPA de BankAccountRepository.
 *
 * @author Taoufik KEHAL
 */
@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindBankAccountById() {
        // Given
        BankAccount account = BankAccount.builder()
                .createdAt(LocalDateTime.now())
                .balance(new BigDecimal("1000.00"))
                .currency("MAD")
                .accountType(AccountType.CURRENT_ACCOUNT)
                .customerId(1L)
                .build();

        // When
        BankAccount saved = bankAccountRepository.save(account);
        entityManager.flush();
        entityManager.clear();
        Optional<BankAccount> result = bankAccountRepository.findById(saved.getId());

        // Then
        assertThat(result).isPresent();
        BankAccount found = result.get();
        assertThat(found.getId()).isNotNull();
        assertThat(found.getAccountType()).isEqualTo(AccountType.CURRENT_ACCOUNT);
        assertThat(found.getBalance()).isEqualByComparingTo("1000.00");
        assertThat(found.getCurrency()).isEqualTo("MAD");
        assertThat(found.getCustomerId()).isEqualTo(1L);
    }
}