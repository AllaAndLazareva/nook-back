package by.soykin.nook.nookback.mapper.impl;

import by.soykin.nook.nookback.jpa.entities.Image;
import by.soykin.nook.nookback.jpa.entities.Nook;
import by.soykin.nook.nookback.mapper.Mapper;
import by.soykin.nook.nookback.model.ImageModel;
import by.soykin.nook.nookback.model.NookModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageMapper implements Mapper<Image, ImageModel> {

    @Override
    public Image toEntity(ImageModel imageModel) {
        return null;
    }

    @Override
    public ImageModel toModel(Image image)  {
        ImageModel imageModel=new ImageModel();
        String locationOfImage= image.getLocation();
        imageModel.setLocation(locationOfImage);
        byte[] images=null;
        try {
            images= Files.readAllBytes(new File(locationOfImage).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageModel.setImages(images);
return imageModel;
    }
}
