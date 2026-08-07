package app;

import javafx.application.Application;

/**
 * Entry point for the application.
 * Launches the JavaFX application using {@link AppBuilder}
 */
public class Main {

    /**
     * Starts the application by launching the JavaFX runtime with
     * {@link AppBuilder} as the application class.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(AppBuilder.class, args);
    }
}
