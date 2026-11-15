package ma.enset.customerservice.exception;

import lombok.Getter;

/**
 * @author Taoufik KEHAL
 **/
@Getter
public class CustomerNotFoundException extends RuntimeException{
    private Long id;

    public CustomerNotFoundException(Long id) {
        super("Client introuvable : " + id);
        this.id = id;
    }
}
