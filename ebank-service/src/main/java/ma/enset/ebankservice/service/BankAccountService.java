package ma.enset.ebankservice.service;


import lombok.RequiredArgsConstructor;
import ma.enset.ebankservice.entity.BankAccount;
import ma.enset.ebankservice.exception.BankAccountNotFoundException;
import ma.enset.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount getAccountById(String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));
    }


}
