package by.soykin.nook.nookback.mapper.impl;

import by.soykin.nook.nookback.jpa.entities.Image;
import by.soykin.nook.nookback.jpa.entities.Nook;
import by.soykin.nook.nookback.jpa.repository.ImageRepository;
import by.soykin.nook.nookback.mapper.Mapper;
import by.soykin.nook.nookback.model.ImageModel;
import by.soykin.nook.nookback.model.NookModel;
import by.soykin.nook.nookback.provider.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NookMapper implements Mapper<Nook, NookModel> {

    @Autowired
    private static ImageMapper imageMapper;
    @Autowired
    private static ImageRepository imageRepository;
    @Override
    public Nook toEntity(NookModel nookModel) {
        return null;
    }

    @Override
    public NookModel toModel(Nook nook) throws IOException {
        NookModel nookModel=new NookModel();
        nookModel.setAddress(nook.getAddress().getValue());
        nookModel.setQuantityRooms(nook.getQuantityRooms().toString());
        String imageLocation=nook.getImages().get(0).getLocation();
              byte[] images=null;
        try {            images= Files.readAllBytes(new File(imageLocation).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nookModel.setImage(images);

        nookModel.setTimeOfEditing(nook.getTimeOfEditing());
        nookModel.setType(nook.getType().toString());
        return nookModel;
    }


}
