package by.soykin.nook.nookback.controller;

import by.soykin.nook.nookback.jpa.entities.Nook;
import by.soykin.nook.nookback.model.ImageModel;
import by.soykin.nook.nookback.model.NookModel;
import by.soykin.nook.nookback.provider.ImageProvider;
import by.soykin.nook.nookback.provider.NookProvider;
import by.soykin.nook.nookback.provider.impl.NookProviderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/nook")
@RequiredArgsConstructor
public class NookController {

    private final NookProvider nookProvider;

    private final ImageProvider imageProvider;



    @RequestMapping(value = "/image//{fileName}", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.GET)
    public ResponseEntity<List<NookModel>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {

        return ResponseEntity.ok(nookProvider.getAll(offset,limit));
    }

    @RequestMapping(value = "/image/{fileName}", method = RequestMethod.GET, produces = "image/jpeg")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) throws IOException {
        byte [] image = imageProvider.downloadImage(fileName);
        ResponseEntity<byte[]> body=null;
        if(fileName.contains(".png")){
             body= ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(image);
        }
        else if(fileName.contains(".jpg")){
            body=ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpg"))
                    .body(image);}
        else if(fileName.contains(".jpeg")){
            body= ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpeg"))
                    .body(image);}
        else if(fileName.contains(".gif")){
           body= ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/gif"))
                    .body(image);}
        return body;

            }











}
