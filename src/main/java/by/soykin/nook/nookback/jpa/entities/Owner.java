package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.OwnerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "owner")
@Getter
@Setter
public class Owner {
    @Id
    @Column
    private String id;

    @Column(name="owner_type")
    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    @Column
    private String name;

    @OneToMany
    @JoinTable(name = "owner_phone",
            joinColumns = @JoinColumn(name = "owner"),
            inverseJoinColumns = @JoinColumn(name = "phone_number"))
    private List<Phone> phoneNumber;
}
