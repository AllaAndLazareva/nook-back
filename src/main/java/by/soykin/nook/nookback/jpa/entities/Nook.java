package by.soykin.nook.nookback.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nook")
@Getter
@Setter
public class Nook {

    @Id
    private String id;

    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NookType type;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
