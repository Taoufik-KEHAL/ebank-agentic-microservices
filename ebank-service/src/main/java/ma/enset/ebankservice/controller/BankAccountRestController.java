package ma.enset.ebankservice.controller;


import lombok.RequiredArgsConstructor;
import ma.enset.ebankservice.entity.BankAccount;
import ma.enset.ebankservice.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Taoufik KEHAL
 **/

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class BankAccountRestController {
    private final BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccount> findAll(){
        return bankAccountService.getAllAccounts();

    }

    @GetMapping("/{id}")
    public BankAccount findById(@PathVariable String id) {

        return bankAccountService.getAccountById(id);
    }
}
