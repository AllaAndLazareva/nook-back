package by.soykin.nook.nookback.controller;

import by.soykin.nook.nookback.model.ImageModel;
import by.soykin.nook.nookback.provider.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

//    private final ImageProvider imageProvider;
//
//    @RequestMapping(value = "/{nookId}", method = RequestMethod.GET, produces = "image/jpeg")
//    public ResponseEntity<?> downloadImage(@PathVariable String nookId) throws IOException {
//        List<ImageModel> all = imageProvider.getAll(nookId);
//        ResponseEntity<byte[]> body = null;
//        for (ImageModel imageModel : all) {
//            byte[] images = imageModel.getImages();
//            if (imageModel.getLocation().contains(".png")) {
//                body = ResponseEntity.status(HttpStatus.OK)
//                        .contentType(MediaType.valueOf("image/png"))
//                        .body(images);
//            } else if (imageModel.getLocation().contains(".jpg")) {
//                body = ResponseEntity.status(HttpStatus.OK)
//                        .contentType(MediaType.valueOf("image/jpg"))
//                        .body(images);
//            } else if (imageModel.getLocation().contains(".jpeg")) {
//                body = ResponseEntity.status(HttpStatus.OK)
//                        .contentType(MediaType.valueOf("image/jpeg"))
//                        .body(images);
//            } else if (imageModel.getLocation().contains(".gif")) {
//                body = ResponseEntity.status(HttpStatus.OK)
//                        .contentType(MediaType.valueOf("image/gif"))
//                        .body(images);
//            }
//
//
//        }
//        return body;
//    }
}






