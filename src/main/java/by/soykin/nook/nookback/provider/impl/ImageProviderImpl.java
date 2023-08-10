package by.soykin.nook.nookback.provider.impl;

import by.soykin.nook.nookback.jpa.entities.Image;
import by.soykin.nook.nookback.jpa.repository.ImageRepository;
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

@Service
@RequiredArgsConstructor
public class ImageProviderImpl implements ImageProvider {

    @Autowired
    private ImageRepository imageRepository;

    //private NookModel nookModel;

//    @Override
//    public List<NookModel> showImage(List<NookModel> nookModels)  {
//        for (int i = 0; i < nookModels.size(); i++) {
//
//            String location = nookModels.get(i).getImage().getLocation();
//            BufferedImage img = new BufferedImage(
//                    235, 157, BufferedImage.TYPE_INT_RGB);
//            File f = new File(location);
//            try {
//
//                if (location.contains(".png")) {
//                    ImageIO.write(img, "PNG", f);
//                } else if (location.contains(".jpg")) {
//                     ImageIO.write(img, "JPG", f);
//                } else if (location.contains(".jpeg")) {
//                    ImageIO.write(img, "JPEG", f);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return nookModels;
//    }


    public byte[] downloadImage(String imageLocation) throws IOException {
        Optional<Image> image=imageRepository.findById(imageLocation);
        byte [] images= Files.readAllBytes(new File(imageLocation).toPath());

        return images;
    }
}
