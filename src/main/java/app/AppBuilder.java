package app;

import application.use_cases.User.Login.LoginInteractor;
import application.use_cases.User.SignUp.ProfanityCheck;
import application.use_cases.User.SignUp.SignupInteractor;
import data_access.GameAssetManager;
import data_access.JsonUserDataAccessObject;
import domain.entities.Interactable.CommonInteractableFactory;
import domain.entities.Interactable.InteractableFactory;
import domain.entities.Item.CommonItemFactory;
import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.Room.CommonRoomFactory;
import domain.entities.Room.RoomFactory;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import interface_adapter.User.Login.LoginController;
import interface_adapter.User.Login.LoginPresenter;
import interface_adapter.User.Login.LoginViewModel;
import interface_adapter.User.Signup.ProfanityCheckGateway;
import interface_adapter.User.Signup.SignupController;
import interface_adapter.User.Signup.SignupPresenter;
import interface_adapter.User.Signup.SignupViewModel;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;
import view.common.OverlayFactory;
import view.mainmenu.MainMenuView;
import view.user.LoginOverlay;
import view.user.SignupOverlay;

import java.net.http.HttpClient;

public class AppBuilder extends Application {

    @Override
    public void start(Stage primaryStage) {

        ViewManager viewManager = new ViewManager(primaryStage);

        // --- JSON information chain ---
        ItemFactory itemFactory = new CommonItemFactory();
        InteractableFactory interactableFactory = new CommonInteractableFactory();
        RoomFactory roomFactory = new CommonRoomFactory();

        GameAssetManager gameAssetManager = new GameAssetManager(itemFactory, interactableFactory, roomFactory);

        JsonUserDataAccessObject userDAO = new JsonUserDataAccessObject(gameAssetManager,gameAssetManager);
        CommonUserFactory userFactory = new CommonUserFactoryClass();

        // --- Login chain ---
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(userDAO, loginPresenter, userFactory);
        LoginController loginController = new LoginController(loginInteractor);

        // --- Signup chain ---
        ProfanityCheck profanityCheck = new ProfanityCheckGateway(HttpClient.newHttpClient());
        SignupViewModel signupViewModel = new SignupViewModel();
        SignupPresenter signupPresenter = new SignupPresenter(signupViewModel);
        SignupInteractor signupInteractor =
                new SignupInteractor(userDAO, signupPresenter, userFactory, profanityCheck);
        SignupController signupController = new SignupController(signupInteractor);

        OverlayFactory loginOverlayFactory =
                onClose -> new LoginOverlay(onClose, loginController, loginViewModel);

        OverlayFactory signupOverlayFactory =
                onClose -> new SignupOverlay(onClose, signupController, signupViewModel);

        MainMenuView mainMenu = new MainMenuView(viewManager, loginOverlayFactory, signupOverlayFactory);

        signupPresenter.setSwitchToLoginCallback(mainMenu::switchFromSignupToLogin);

        primaryStage.setTitle("Escapists");
        viewManager.show(mainMenu);
    }
}