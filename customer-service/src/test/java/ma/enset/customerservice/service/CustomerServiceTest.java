package ma.enset.customerservice.service;

import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires de CustomerService, sans Spring ni base de données.
 *
 * @author Taoufik KEHAL
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() {
        // Given
        Customer c1 = Customer.builder().id(1L).name("Taoufik").email("taoufik@gmail.com").build();
        Customer c2 = Customer.builder().id(2L).name("Sara").email("sara@gmail.com").build();
        when(customerRepository.findAll()).thenReturn(List.of(c1, c2));

        // When
        List<Customer> customers = customerService.getAllCustomers();

        // Then
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(Customer::getName).containsExactly("Taoufik", "Sara");
        verify(customerRepository).findAll();
    }

    @Test
    void shouldReturnCustomerWhenIdExists() {
        // Given
        Customer customer = Customer.builder().id(1L).name("Taoufik").email("taoufik@gmail.com").build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // When
        Customer result = customerService.getCustomerById(1L);

        // Then
        assertThat(result.getName()).isEqualTo("Taoufik");
        assertThat(result.getEmail()).isEqualTo("taoufik@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Given
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client introuvable : 99");
    }
}