package ma.enset.ebankservice.config;

import ma.enset.ebankservice.entity.AccountType;
import ma.enset.ebankservice.entity.BankAccount;
import ma.enset.ebankservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Insère des comptes bancaires de test au démarrage du service.
 *
 * @author Taoufik KEHAL
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAccounts(BankAccountRepository bankAccountRepository) {
        return args -> bankAccountRepository.saveAll(List.of(
                account(1L, AccountType.CURRENT_ACCOUNT, "15000.00"),
                account(1L, AccountType.SAVING_ACCOUNT, "50000.00"),
                account(2L, AccountType.CURRENT_ACCOUNT, "8000.00"),
                account(3L, AccountType.CURRENT_ACCOUNT, "22000.00")
        ));
    }

    private static BankAccount account(Long customerId, AccountType type, String balance) {
        return BankAccount.builder()
                .customerId(customerId)
                .accountType(type)
                .balance(new BigDecimal(balance))
                .currency("MAD")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
