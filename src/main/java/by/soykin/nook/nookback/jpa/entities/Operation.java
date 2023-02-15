package by.soykin.nook.nookback.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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


}
