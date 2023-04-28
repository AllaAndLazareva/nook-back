package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
@Entity
@Table(name = "cost")
@Getter
@Setter
@NoArgsConstructor
public class Cost {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

//    @Enumerated(EnumType.STRING)
//    private Currency currency;

    @Column(name = "cost_in_byn")
    private BigDecimal costInBYN;

    @Column(name = "cost_in_usd")
    private BigDecimal costInUSD;

}
