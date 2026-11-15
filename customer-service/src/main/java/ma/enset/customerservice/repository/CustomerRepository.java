package ma.enset.customerservice.repository;

import ma.enset.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Taoufik KEHAL
 **/

public interface CustomerRepository extends JpaRepository<Customer, Long>  {
}
