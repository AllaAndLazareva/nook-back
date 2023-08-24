package by.soykin.nook.nookback.provider.impl;

import by.soykin.nook.nookback.jpa.entities.Image;
import by.soykin.nook.nookback.jpa.repository.ImageRepository;
import by.soykin.nook.nookback.mapper.Mapper;
import by.soykin.nook.nookback.model.ImageModel;
import by.soykin.nook.nookback.model.NookModel;
import by.soykin.nook.nookback.provider.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageProviderImpl  {
//    @Override
//    public List<ImageModel> getAll(String nookId) {
//        return n
}

//
//    private ImageRepository imageRepository;
//
//    private final Mapper<Image, ImageModel> mapper;
//
//
//
//    public List<ImageModel> getAll(String nookId){
//        List<ImageModel> imageModels=imageRepository.findByNook(nookId).stream()
//                .map(entity-> {
//                    try {
//                        return mapper.toModel(entity);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }).collect(Collectors.toList());
//
//        return imageModels;
//
//    }
//    public void save(ImageModel imageModel){
//
//    }


