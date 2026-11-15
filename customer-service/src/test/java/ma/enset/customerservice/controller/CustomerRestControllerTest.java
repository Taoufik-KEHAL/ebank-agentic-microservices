package ma.enset.customerservice.controller;
import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.exception.CustomerNotFoundException;
import ma.enset.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de la couche web de CustomerRestController, avec un service simulé.
 *
 * @author Taoufik KEHAL
 */
@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() throws Exception {

        Customer c1 = Customer.builder().id(1L).name("Taoufik").email("taoufik@gmail.com").build();
        Customer c2 = Customer.builder().id(2L).name("Sara").email("sara@gmail.com").build();
        when(customerService.getAllCustomers()).thenReturn(List.of(c1, c2));


        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Taoufik"))
                .andExpect(jsonPath("$[1].name").value("Sara"));
    }

    @Test
    void shouldReturnEmptyListWhenNoCustomers() throws Exception {

        when(customerService.getAllCustomers()).thenReturn(List.of());


        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnCustomerWhenIdExists() throws Exception {

        Customer customer = Customer.builder().id(1L).name("Taoufik").email("taoufik@gmail.com").build();
        when(customerService.getCustomerById(1L)).thenReturn(customer);


        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Taoufik"))
                .andExpect(jsonPath("$.email").value("taoufik@gmail.com"));
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() throws Exception {

        when(customerService.getCustomerById(99L)).thenThrow(new CustomerNotFoundException(99L));


        mockMvc.perform(get("/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Client introuvable : 99"));
    }
}