package ma.enset.ebankservice.exception;

import lombok.Getter;

@Getter
public class BankAccountNotFoundException extends RuntimeException {
    private final String id;

    public BankAccountNotFoundException(String id) {

        super("Compte introuvable : " + id);
        this.id = id;
    }
}




