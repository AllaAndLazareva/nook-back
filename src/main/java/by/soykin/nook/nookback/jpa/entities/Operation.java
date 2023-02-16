package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Operation {

    @Id
    private String id;

    @JoinColumn(name = "id")
    @OneToOne
    private Nook nook;

    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @JoinColumn(name="id")
    @OneToOne
    private Owner owner;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Cost> costs;


}
