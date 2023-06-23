package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.NookType;
import by.soykin.nook.nookback.jpa.entities.enums.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "nook")
@Getter
@Setter
public class Nook {

    @Id
    private String id;

    private String description;

    @Column(name = "quantity_rooms")
    @Enumerated(EnumType.STRING)
    private Room quantityRooms;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NookType type;

    @ManyToOne
    @JoinTable(name = "address_nook",
            joinColumns = @JoinColumn(name = "nook"),
            inverseJoinColumns = @JoinColumn(name = "address"))
    private Address address;


    @OneToMany
    @JoinTable(name = "nook_images",
            joinColumns = @JoinColumn(name = "nook"),
            inverseJoinColumns = @JoinColumn(name = "images"))
    private List<Image> images;



}
