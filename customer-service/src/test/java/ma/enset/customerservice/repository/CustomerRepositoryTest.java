package ma.enset.customerservice.repository;

import ma.enset.customerservice.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Taoufik KEHAL
 **/

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldSaveAndFindCustomerById() {
        // Given : construire un Customer avec Customer.builder()
        Customer customer = Customer.builder()
                .name("Taoufik")
                .email("taoufik@gmail.com")
                .build();

        // When : l'enregistrer avec save(), puis le relire avec findById()
        Customer saved = customerRepository.save(customer);
        Optional<Customer> customerOptional = customerRepository.findById(saved.getId());

        // Then : vérifier que le client est présent, que son id n'est pas null,
        // et que name et email sont corrects
        assertThat(customerOptional).isPresent();
        Customer found = customerOptional.get();
        assertThat(found.getId()).isNotNull();
        assertThat(found.getName()).isEqualTo("Taoufik");
        assertThat(found.getEmail()).isEqualTo("taoufik@gmail.com");
    }
}