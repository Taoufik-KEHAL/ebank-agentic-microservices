package ma.enset.customerservice.service;


import lombok.RequiredArgsConstructor;
import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.exception.CustomerNotFoundException;
import ma.enset.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Taoufik KEHAL
 **/
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }


}

