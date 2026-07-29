package app;

import application.use_cases.Audio.ToggleMusic.ToggleMusicInteractor;
import application.use_cases.Audio.ToggleSfx.ToggleSfxInteractor;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsInteractor;
import application.use_cases.GamePlay.QuickPlay.QuickModeStartUp.QuickModeStartUpInteractor;
import application.use_cases.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpInteractor;
import application.use_cases.Interactable.Interact.InteractInteractor;
import application.use_cases.Interactable.Zoom.ZoomInteractor;
import application.use_cases.Puzzle.EnterExit.EnterExitInteractor;
import application.use_cases.Puzzle.Solve.SolveInteractor;
import application.use_cases.User.Login.LoginInteractor;
import application.use_cases.User.Logout.LogoutInteractor;
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
import domain.entities.Puzzle.CommonPuzzleFactory;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Puzzle.PuzzleFactory;
import domain.entities.Room.CommonRoomFactory;
import domain.entities.Room.RoomFactory;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import interface_adapter.Audio.*;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsPresenter;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsViewModel;
import interface_adapter.GamePlay.InGameViewModel;
import interface_adapter.GamePlay.QuickModeStartUp.QuickModeStartUpController;
import interface_adapter.GamePlay.QuickModeStartUp.QuickModeStartUpPresenter;
import interface_adapter.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpController;
import interface_adapter.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpPresenter;
import interface_adapter.Interactable.Interact.InteractController;
import interface_adapter.Interactable.Interact.InteractPresenter;
import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.Interactable.Zoom.ZoomController;
import interface_adapter.Interactable.Zoom.ZoomPresenter;
import interface_adapter.Interactable.Zoom.ZoomViewModel;
import interface_adapter.Puzzle.EnterExit.EnterExitController;
import interface_adapter.Puzzle.EnterExit.EnterExitPresenter;
import interface_adapter.Puzzle.EnterExit.EnterExitViewModel;
import interface_adapter.Puzzle.Solve.SolveController;
import interface_adapter.Puzzle.Solve.SolvePresenter;
import interface_adapter.User.LoggedIn.LoggedInViewModel;
import interface_adapter.User.Login.LoginController;
import interface_adapter.User.Login.LoginPresenter;
import interface_adapter.User.Login.LoginViewModel;
import interface_adapter.User.Logout.LogoutController;
import interface_adapter.User.Logout.LogoutPresenter;
import interface_adapter.User.MainMenu.MainMenuViewModel;
import interface_adapter.User.SaveProgress.SaveProgressViewModel;
import interface_adapter.User.Signup.ProfanityCheckGateway;
import interface_adapter.User.Signup.SignupController;
import interface_adapter.User.Signup.SignupPresenter;
import interface_adapter.User.Signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Game.BrowseRoomsView;
import view.Game.InGameView;
import view.ViewManager;
import view.common.AudioControlView;
import view.common.OverlayFactory;
import view.common.PlaceholderView;
import view.common.SoundPlayer;
import view.interactable.InteractOverlay;
import view.interactable.ZoomView;
import view.mainmenu.MainMenuView;
import view.puzzle.PuzzleView;
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
        PuzzleFactory puzzleFactory = new CommonPuzzleFactory();

        GameAssetManager gameAssetManager = new GameAssetManager(itemFactory, interactableFactory, roomFactory, hintFactory, puzzleFactory);

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
        LoggedInViewModel  loggedInViewModel = new LoggedInViewModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, loggedInViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(userDAO, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);

        // --- Logout chain ---
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        SaveProgressViewModel saveProgressViewModel = new SaveProgressViewModel();
        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, mainMenuViewModel, loggedInViewModel, saveProgressViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(userDAO, logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor, null /* saveAndLogoutInteractor — not wired yet */);

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

        //--- View Model for starting game ---
        InGameViewModel inGameViewModel = new InGameViewModel();

        //--- Tutorial Mode & Story Mode start up chain ---
        TutorialAndStoryModeStartUpPresenter tutorialAndStoryModeStartUpPresenter
                = new TutorialAndStoryModeStartUpPresenter(inGameViewModel, viewManagerModel);
        TutorialAndStoryModeStartUpInteractor tutorialAndStoryModeStartUpInteractor
                = new TutorialAndStoryModeStartUpInteractor(tutorialAndStoryModeStartUpPresenter, gameAssetManager);
        TutorialAndStoryModeStartUpController tutorialAndStoryModeStartUpController
                = new TutorialAndStoryModeStartUpController(tutorialAndStoryModeStartUpInteractor);

        //--- Browse rooms chain ---
        BrowseRoomsViewModel browseRoomsViewModel = new BrowseRoomsViewModel();
        BrowseRoomsPresenter browseRoomsPresenter = new BrowseRoomsPresenter(browseRoomsViewModel, viewManagerModel);
        BrowseRoomsInteractor browseRoomsInteractor = new BrowseRoomsInteractor(gameAssetManager, browseRoomsPresenter);
        BrowseRoomsController browseRoomsController = new BrowseRoomsController(browseRoomsInteractor);

        //--- Quick Mode chain ---
        QuickModeStartUpPresenter quickModeStartUpPresenter
                = new QuickModeStartUpPresenter(inGameViewModel, viewManagerModel);
        QuickModeStartUpInteractor quickModeStartUpInteractor
                = new QuickModeStartUpInteractor(quickModeStartUpPresenter, gameAssetManager);
        QuickModeStartUpController quickModeStartUpController
                = new QuickModeStartUpController(quickModeStartUpInteractor);

        // --- Main menu ---
        MainMenuView mainMenu = new MainMenuView(
                viewManagerModel, mainMenuViewModel, loggedInViewModel,
                loginOverlayFactory, signupOverlayFactory,
                () -> logoutController.executeLogoutWithoutSave(loggedInViewModel.getState().getUsername()),
                audioControlView,
                tutorialAndStoryModeStartUpController,
                browseRoomsController);

        signupPresenter.setSwitchToLoginCallback(mainMenu::switchFromSignupToLogin);

        // --- Browse Rooms ---
        BrowseRoomsView browseRoomsView = new BrowseRoomsView(viewManager,
                mainMenu, browseRoomsViewModel, browseRoomsController, quickModeStartUpController);

        // --- In-game ---
        InGameView inGameView = new InGameView(inGameViewModel);

        // --- Placeholder screens ---
        PlaceholderView storyPlaceholder = new PlaceholderView("Story Line", viewManagerModel);
        PlaceholderView tutorialPlaceholder = new PlaceholderView("Tutorial", viewManagerModel);
        PlaceholderView quickGamePlaceholder = new PlaceholderView("Quick Game", viewManagerModel);

        // --- Interactable Zoom Chain ---
        ZoomViewModel zoomViewModel = new ZoomViewModel();
        ZoomPresenter zoomPresenter = new ZoomPresenter(zoomViewModel, viewManagerModel);
        ZoomInteractor zoomInteractor = new ZoomInteractor(gameAssetManager, zoomPresenter);
        ZoomController zoomController = new ZoomController(zoomInteractor);

        // --- Interactable Interact Chain ---
        InteractViewModel interactViewModel = new InteractViewModel();
        InteractPresenter interactPresenter = new InteractPresenter(interactViewModel, viewManagerModel);
        InteractInteractor interactInteractor = new InteractInteractor(gameAssetManager, interactPresenter);
        InteractController interactController = new InteractController(interactInteractor);

        // --- Puzzle EnterExit Chain ---
        EnterExitViewModel enterExitViewModel = new EnterExitViewModel();
        EnterExitPresenter enterExitPresenter = new EnterExitPresenter(enterExitViewModel, interactViewModel,
                viewManagerModel);
        EnterExitInteractor enterExitInteractor = new EnterExitInteractor(gameAssetManager, enterExitPresenter);
        EnterExitController enterExitController = new EnterExitController(enterExitInteractor);

        // --- Puzzle Solve Chain ---
        SolvePresenter solvePresenter = new SolvePresenter(interactViewModel, viewManagerModel);
        SolveInteractor solveInteractor = new SolveInteractor(gameAssetManager, solvePresenter);
        SolveController solveController = new SolveController(solveInteractor);

        // --- Interactable and Puzzle Views ---
        ZoomView zoomView = new ZoomView(zoomController, zoomViewModel, interactController, enterExitController);
        InteractOverlay interactOverlay = new InteractOverlay(interactViewModel, viewManagerModel);
        PuzzleView puzzleView = new PuzzleView(enterExitViewModel);

        // --- Register every top-level screen by name ---
        viewManager.registerView("main menu", mainMenu);
        viewManager.registerView("in-game", inGameView);
        viewManager.registerView("browse rooms", browseRoomsView);

        // --- Register Interactable and Puzzle Views ---
        viewManager.registerView("Zoom", zoomView);
        // TODO: Register InteractOverlay in viewManager once overlay cases are handled in ViewManager
        viewManager.registerView("Puzzle",  puzzleView);

        // --- Trigger the first screen ---
        viewManagerModel.firePropertyChanged();
    }
}