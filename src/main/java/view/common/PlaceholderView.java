package view.common;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import view.mainmenu.MainMenuView;
import view.ViewManager;

public class PlaceholderView extends VBox {
    public PlaceholderView(String title, ViewManager viewManager, MainMenuView mainMenu) {
        setAlignment(Pos.CENTER);
        setSpacing(20);

        Label label = new Label(title + " — screen not built yet");
        Button back = new Button("Back to Main Menu");
        back.setOnAction(e -> viewManager.show(mainMenu));

        getChildren().addAll(label, back);
    }
}
