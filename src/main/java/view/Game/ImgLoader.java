package view.Game;

import javafx.scene.image.Image;

import java.io.InputStream;

public class ImgLoader {

    public Image loadImage(String resourcePath) {
        InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return new Image(stream);
    }
}
