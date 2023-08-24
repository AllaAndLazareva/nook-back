package by.soykin.nook.nookback.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageModel {

    private String location;
    private byte[] images;
    private String type;
}
