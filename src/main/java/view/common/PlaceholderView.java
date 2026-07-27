package view.common;

import interface_adapter.ViewManagerModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlaceholderView extends VBox {
    public PlaceholderView(String title, ViewManagerModel viewManagerModel) {
        setAlignment(Pos.CENTER);
        setSpacing(20);

        Label label = new Label(title + " — screen not built yet");
        Button back = new Button("Back to Main Menu");
        back.setOnAction(e -> {
            viewManagerModel.setState("main menu");
            viewManagerModel.firePropertyChanged();
        });

        getChildren().addAll(label, back);
    }
}