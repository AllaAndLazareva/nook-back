package by.soykin.nook.nookback.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name="value")
    private String value;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Nook> nooks;

    @Override
    public String toString() {
        return value;
    }
}
