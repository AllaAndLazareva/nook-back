package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.NookType;
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

    private int square;

    @Column(name = "quantity_rooms")
    private int quantityRooms;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NookType type;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;



}
