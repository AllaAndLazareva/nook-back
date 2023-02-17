package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.OwnerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "phone_number")
    @Pattern(regexp = "^(\\+375)\\d{9}$", message = "Phone number is +375.........")
    private String phoneNumber;
}
