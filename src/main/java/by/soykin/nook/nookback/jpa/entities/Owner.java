package by.soykin.nook.nookback.jpa.entities;

import jakarta.persistence.Id;

public class Owner {
    @Id
    private String id;
    private String name;
    private String number;
}
