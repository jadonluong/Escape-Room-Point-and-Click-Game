package view.game;

import java.io.InputStream;

import javafx.scene.image.Image;

public class ImgLoader {

    /**
     * Loads an image resource from the specified path.
     *
     * @param resourcePath the path to the image resource
     * @return the loaded image
     * @throws IllegalArgumentException if the resource cannot be found
     */
    public Image loadImage(String resourcePath) {
        final InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return new Image(stream);
    }
}
