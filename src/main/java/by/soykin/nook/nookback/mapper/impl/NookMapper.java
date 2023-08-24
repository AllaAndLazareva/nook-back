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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NookMapper implements Mapper<Nook, NookModel> {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public Nook toEntity(NookModel nookModel) {
        return null;
    }

    @Override
    public NookModel toModel(Nook nook) throws IOException {
        NookModel nookModel=new NookModel();
        nookModel.setAddress(nook.getAddress().getValue());
        nookModel.setQuantityRooms(nook.getQuantityRooms().toString());
        nookModel.setImage(imageMapper.toModel(nook.getImages().get(0)));

//                .stream()
//                .map(image->imageMapper.toModel(image))
//                .collect(Collectors.toList()));
        nookModel.setTimeOfEditing(nook.getTimeOfEditing());
        nookModel.setType(nook.getType().toString());
        return nookModel;
    }


}
