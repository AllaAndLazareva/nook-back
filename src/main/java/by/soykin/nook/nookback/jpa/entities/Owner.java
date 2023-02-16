package by.soykin.nook.nookback.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column
    private String name;
    @Column
    @Pattern(regexp = "^(\\+375)\\d{9}$", message = "Phone number is +375.........")
    private String number;
}
