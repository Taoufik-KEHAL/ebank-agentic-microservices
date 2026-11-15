package ma.enset.customerservice.controller;

import lombok.RequiredArgsConstructor;
import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Taoufik KEHAL
 **/

@RestController
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> findAll(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer findById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}
