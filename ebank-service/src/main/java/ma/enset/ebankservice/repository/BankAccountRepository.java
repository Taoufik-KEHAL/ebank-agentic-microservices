package ma.enset.ebankservice.repository;

import ma.enset.ebankservice.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taoufik KEHAL
 **/
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
