package by.soykin.nook.nookback.model;

import by.soykin.nook.nookback.jpa.entities.Address;
import by.soykin.nook.nookback.jpa.entities.Image;
import by.soykin.nook.nookback.jpa.entities.enums.NookType;
import by.soykin.nook.nookback.jpa.entities.enums.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class NookModel {

    private String quantityRooms;

    private String type;

    private LocalDateTime timeOfEditing;

    private String address;

    private ImageModel image;

}
