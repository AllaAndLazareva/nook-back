package by.soykin.nook.nookback.provider;

import by.soykin.nook.nookback.model.NookModel;

import java.io.IOException;
import java.util.List;

public interface ImageProvider {



    byte[] downloadImage(String imageLocation) throws IOException;
}
