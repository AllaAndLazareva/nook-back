package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "operation")
@Getter
@Setter
public class Operation {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @OneToOne
    @JoinColumn(name = "nook", referencedColumnName = "id")
    private Nook nook;

    @OneToOne
    @JoinColumn(name="owner", referencedColumnName = "id")
    private Owner owner;

    @OneToOne
    @JoinColumn(name =     "cost", referencedColumnName = "id")
    private Cost cost;


}
