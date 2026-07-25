package app;

import application.use_cases.Audio.ToggleMusic.ToggleMusicInteractor;
import application.use_cases.Audio.ToggleSfx.ToggleSfxInteractor;
import application.use_cases.User.Login.LoginInteractor;
import application.use_cases.User.SignUp.ProfanityCheck;
import application.use_cases.User.SignUp.SignupInteractor;
import data_access.GameAssetManager;
import data_access.JsonUserDataAccessObject;
import domain.entities.Hint.CommonHintFactory;
import domain.entities.Hint.HintFactory;
import domain.entities.Interactable.CommonInteractableFactory;
import domain.entities.Interactable.InteractableFactory;
import domain.entities.Item.CommonItemFactory;
import domain.entities.Item.ItemFactory;
import domain.entities.Room.CommonRoomFactory;
import domain.entities.Room.RoomFactory;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import interface_adapter.Audio.*;
import interface_adapter.User.Login.LoginController;
import interface_adapter.User.Login.LoginPresenter;
import interface_adapter.User.Login.LoginViewModel;
import interface_adapter.User.MainMenu.MainMenuViewModel;
import interface_adapter.User.Signup.ProfanityCheckGateway;
import interface_adapter.User.Signup.SignupController;
import interface_adapter.User.Signup.SignupPresenter;
import interface_adapter.User.Signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;
import view.common.AudioControlView;
import view.common.OverlayFactory;
import view.common.PlaceholderView;
import view.common.SoundPlayer;
import view.mainmenu.MainMenuView;
import view.user.LoginOverlay;
import view.user.SignupOverlay;

import java.net.http.HttpClient;

public class AppBuilder extends Application {

    @Override
    public void start(Stage primaryStage) {

        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // --- JSON information chain ---
        ItemFactory itemFactory = new CommonItemFactory();
        InteractableFactory interactableFactory = new CommonInteractableFactory();
        RoomFactory roomFactory = new CommonRoomFactory();
        HintFactory hintFactory = new CommonHintFactory();

        GameAssetManager gameAssetManager = new GameAssetManager(itemFactory, interactableFactory, roomFactory, hintFactory);

        JsonUserDataAccessObject userDAO = new JsonUserDataAccessObject(gameAssetManager,gameAssetManager);
        CommonUserFactory userFactory = new CommonUserFactoryClass();

        // --- Audio chain (built before ViewManager, which needs the sfx state) ---
        AudioViewModel audioViewModel = new AudioViewModel();
        ToggleSfxPresenter sfxPresenter = new ToggleSfxPresenter(audioViewModel);
        ToggleSfxInteractor sfxInteractor = new ToggleSfxInteractor(sfxPresenter);
        ToggleSfxController sfxController = new ToggleSfxController(sfxInteractor);
        ToggleMusicPresenter musicPresenter = new ToggleMusicPresenter(audioViewModel);
        ToggleMusicInteractor musicInteractor = new ToggleMusicInteractor(musicPresenter);
        ToggleMusicController musicController = new ToggleMusicController(musicInteractor);
        AudioControlView audioControlView = new AudioControlView(sfxController, musicController, audioViewModel);

        SoundPlayer soundPlayer = new SoundPlayer("/audio/sfx/click.wav");
        ViewManager viewManager = new ViewManager(
                primaryStage, viewManagerModel, soundPlayer,
                () -> audioViewModel.getState().isSfxOn());

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

        // --- Main menu ---
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        MainMenuView mainMenu = new MainMenuView(
                viewManagerModel, mainMenuViewModel,
                loginOverlayFactory, signupOverlayFactory, audioControlView);

        signupPresenter.setSwitchToLoginCallback(mainMenu::switchFromSignupToLogin);

        // --- Placeholder screens ---
        PlaceholderView storyPlaceholder = new PlaceholderView("Story Line", viewManagerModel);
        PlaceholderView tutorialPlaceholder = new PlaceholderView("Tutorial", viewManagerModel);
        PlaceholderView quickGamePlaceholder = new PlaceholderView("Quick Game", viewManagerModel);

        // --- Register every top-level screen by name ---
        viewManager.registerView("main menu", mainMenu);
        viewManager.registerView("story", storyPlaceholder);
        viewManager.registerView("tutorial", tutorialPlaceholder);
        viewManager.registerView("quick game", quickGamePlaceholder);

        // --- Trigger the first screen ---
        viewManagerModel.firePropertyChanged();
    }
}