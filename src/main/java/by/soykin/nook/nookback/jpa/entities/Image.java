package by.soykin.nook.nookback.jpa.entities;

import by.soykin.nook.nookback.jpa.entities.enums.ImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
public class Image {


    @Id
    @Column(name = "location")
    private String location;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ImageType type;



}
