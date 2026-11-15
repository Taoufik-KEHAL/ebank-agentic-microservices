package ma.enset.customerservice.config;

import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Taoufik KEHAL
 **/
@Configuration
public class DataInitializer {


        @Bean
        public CommandLineRunner initCustomers(CustomerRepository customerRepository) {
            return args -> {
                customerRepository.save(Customer.builder().name("Taoufik").email("taoufik@gmail.com").build());
                customerRepository.save(Customer.builder().name("Tarik").email("tarik@gmail.com").build());
                customerRepository.save(Customer.builder().name("Sara").email("sara@gmail.com").build());
            };
        }

    }
