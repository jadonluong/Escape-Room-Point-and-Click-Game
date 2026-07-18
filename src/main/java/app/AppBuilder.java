package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuView;
import view.ViewManager;

public class AppBuilder extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewManager viewManager = new ViewManager(primaryStage);
        MainMenuView mainMenu = new MainMenuView(viewManager);

        primaryStage.setTitle("Escapists");
        viewManager.show(mainMenu);
    }
}
