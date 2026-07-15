package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppBuilder extends Application {

    private final StackPane rootPane = new StackPane();

    @Override
    public void start(Stage primaryStage) {
        // TODO: swap in a real RoomView once view/ and interface_adapters/ exist
        Scene scene = new Scene(rootPane, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Escapists");
        primaryStage.show();
    }

    // Future pattern, once pieces exist — chainable builder methods:
    // public AppBuilder addRoomView(RoomViewModel viewModel, RoomController controller) {
    //     rootPane.getChildren().setAll(new RoomView(viewModel, controller));
    //     return this;
    // }
}
