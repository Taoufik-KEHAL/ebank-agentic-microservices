package ma.enset.ebankservice.entity;


import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant un compte dans la banque.
 *
 * @author Taoufik KEHAL
 **/

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class BankAccount {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime createdAt;
    private BigDecimal balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Long customerId;

}
