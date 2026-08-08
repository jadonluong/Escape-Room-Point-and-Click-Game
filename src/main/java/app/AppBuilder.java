package app;

import java.net.http.HttpClient;

import application.use_cases.Audio.ToggleMusic.ToggleMusicInteractor;
import application.use_cases.Audio.ToggleSfx.ToggleSfxInteractor;
import application.use_cases.Hint.GetHint.GetHintInteractor;
import application.use_cases.Interactable.Interact.InteractInteractor;
import application.use_cases.Interactable.Zoom.ZoomInteractor;
import application.use_cases.Item.PickUp.PickUpInteractor;
import application.use_cases.Item.SelectItem.SelectItemInteractor;
import application.use_cases.Puzzle.EnterExit.EnterExitInteractor;
import application.use_cases.Puzzle.Solve.SolveInteractor;
import application.use_cases.User.LiveUserSessionTracking;
import application.use_cases.User.Login.LoginInteractor;
import application.use_cases.User.Logout.LogoutInteractor;
import application.use_cases.User.SaveAndLogout.SaveAndLogoutInteractor;
import application.use_cases.User.SaveProgress.SaveProgressInteractor;
import application.use_cases.User.SignUp.ProfanityCheck;
import application.use_cases.User.SignUp.SignupInteractor;
import application.use_cases.game_play.QuickPlay.BrowseRooms.BrowseRoomsInteractor;
import application.use_cases.game_play.QuickPlay.QuickModeStartUp.QuickModeStartUpInteractor;
import application.use_cases.game_play.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpInteractor;
import data_access.GameAssetManager;
import data_access.JsonUserDataAccessObject;
import data_access.PuzzleGenerator;
import domain.entities.Hint.CommonHintFactory;
import domain.entities.Hint.HintFactory;
import domain.entities.Interactable.CommonInteractableFactory;
import domain.entities.Interactable.InteractableFactory;
import domain.entities.Item.CommonItemFactory;
import domain.entities.Item.ItemFactory;
import domain.entities.Puzzle.CommonPuzzleFactory;
import domain.entities.Puzzle.PuzzleFactory;
import domain.entities.Room.CommonRoomFactory;
import domain.entities.Room.RoomFactory;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import domain.entities.User.GuestUserFactory;
import domain.entities.User.GuestUserFactoryClass;
import domain.entities.User.User;
import infrastructure.AnagramApiClient;
import infrastructure.CryptogramApiClient;
import interface_adapter.Audio.AudioViewModel;
import interface_adapter.Audio.ToggleMusicController;
import interface_adapter.Audio.ToggleMusicPresenter;
import interface_adapter.Audio.ToggleSfxController;
import interface_adapter.Audio.ToggleSfxPresenter;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsController;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsPresenter;
import interface_adapter.GamePlay.BrowseRooms.BrowseRoomsViewModel;
import interface_adapter.GamePlay.InGameViewModel;
import interface_adapter.GamePlay.QuickModeStartUp.QuickModeStartUpController;
import interface_adapter.GamePlay.QuickModeStartUp.QuickModeStartUpPresenter;
import interface_adapter.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpController;
import interface_adapter.GamePlay.TutorialAndStoryModeStartUp.TutorialAndStoryModeStartUpPresenter;
import interface_adapter.Hint.GetHintController;
import interface_adapter.Hint.GetHintPresenter;
import interface_adapter.Hint.GetHintViewModel;
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
import interface_adapter.User.SaveProgress.SaveProgressController;
import interface_adapter.User.SaveProgress.SaveProgressPresenter;
import interface_adapter.User.SaveProgress.SaveProgressViewModel;
import interface_adapter.User.Signup.ProfanityCheckGateway;
import interface_adapter.User.Signup.SignupController;
import interface_adapter.User.Signup.SignupPresenter;
import interface_adapter.User.Signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.inventory.InventoryPresenter;
import interface_adapter.inventory.InventoryViewModel;
import interface_adapter.inventory.SelectItemController;
import interface_adapter.inventory.SelectItemPresenter;
import interface_adapter.item.PickUpController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Game.BrowseRoomsView;
import view.Game.GameMenuView;
import view.Game.GameObjectActionDispatcher;
import view.Game.InGameView;
import view.Hint.HintOverlay;
import view.ViewManager;
import view.common.AudioControlView;
import view.common.OverlayFactory;
import view.common.SoundPlayer;
import view.interactable.InteractOverlay;
import view.interactable.ZoomView;
import view.inventory.InventoryOverlay;
import view.mainmenu.MainMenuView;
import view.puzzle.PuzzleView;
import view.user.LoginOverlay;
import view.user.SignupOverlay;

public class AppBuilder extends Application {

    @Override
    public void start(Stage primaryStage) {
        final String mainMenuString = "main menu";
        final ViewManagerModel viewManagerModel = new ViewManagerModel();

        // --- JSON information chain ---
        final ItemFactory itemFactory = new CommonItemFactory();
        final InteractableFactory interactableFactory = new CommonInteractableFactory();
        final RoomFactory roomFactory = new CommonRoomFactory();
        final HintFactory hintFactory = new CommonHintFactory();
        final PuzzleFactory puzzleFactory = new CommonPuzzleFactory();

        // --- Puzzle Generator chain ---
        final AnagramApiClient anagramApiClient = new AnagramApiClient("apv_7700ad64-d8be-4591-8f67-607ed21edcce");
        final CryptogramApiClient cryptogramApiClient = new CryptogramApiClient(
                "apv_7700ad64-d8be-4591-8f67-607ed21edcce");
        final PuzzleGenerator puzzleGenerator = new PuzzleGenerator(anagramApiClient, cryptogramApiClient,
                puzzleFactory);

        final GameAssetManager gameAssetManager = new GameAssetManager(itemFactory, interactableFactory, roomFactory,
                hintFactory, puzzleGenerator, puzzleFactory);

        final JsonUserDataAccessObject userDataAccessObject = new JsonUserDataAccessObject(gameAssetManager,
                gameAssetManager);
        final LiveUserSessionTracking userSessionTracking = new LiveUserSessionTracking();
        final CommonUserFactory userFactory = new CommonUserFactoryClass();
        final GuestUserFactory guestUserFactory = new GuestUserFactoryClass();
        final User defaultGuestUser = guestUserFactory.createGuestUser();
        userSessionTracking.setCurrentUser(defaultGuestUser);

        // --- Audio chain (built before ViewManager, which needs the sfx state) ---
        final AudioViewModel audioViewModel = new AudioViewModel();
        final ToggleSfxPresenter sfxPresenter = new ToggleSfxPresenter(audioViewModel);
        final ToggleSfxInteractor sfxInteractor = new ToggleSfxInteractor(sfxPresenter);
        final ToggleSfxController sfxController = new ToggleSfxController(sfxInteractor);
        final ToggleMusicPresenter musicPresenter = new ToggleMusicPresenter(audioViewModel);
        final ToggleMusicInteractor musicInteractor = new ToggleMusicInteractor(musicPresenter);
        final ToggleMusicController musicController = new ToggleMusicController(musicInteractor);
        final AudioControlView audioControlView = new AudioControlView(sfxController, musicController, audioViewModel);

        final SoundPlayer soundPlayer = new SoundPlayer("/audio/sfx/click.wav");
        final ViewManager viewManager = new ViewManager(
                primaryStage, viewManagerModel, soundPlayer,
                () -> audioViewModel.getState().isSfxOn());

        // --- Login chain ---
        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, loggedInViewModel);
        final LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject,
                userSessionTracking, loginPresenter);
        final LoginController loginController = new LoginController(loginInteractor);

        // --- Logout chain ---
        final MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        final SaveProgressViewModel saveProgressViewModel = new SaveProgressViewModel();

        // --- Save progress chain ---
        final SaveProgressPresenter saveProgressPresenter = new SaveProgressPresenter(saveProgressViewModel);
        final SaveProgressInteractor saveProgressInteractor = new SaveProgressInteractor(saveProgressPresenter,
                userDataAccessObject, userSessionTracking);
        final SaveProgressController saveProgressController = new SaveProgressController(saveProgressInteractor);

        final LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, mainMenuViewModel,
                loggedInViewModel, saveProgressViewModel);
        final LogoutInteractor logoutInteractor = new LogoutInteractor(userSessionTracking, logoutPresenter);

        final SaveAndLogoutInteractor saveAndLogoutInteractor = new SaveAndLogoutInteractor(userDataAccessObject,
                userSessionTracking, logoutPresenter);
        // Note: save & logout chain uses logout controller

        final LogoutController logoutController = new LogoutController(logoutInteractor, saveAndLogoutInteractor);

        // --- Signup chain ---
        final ProfanityCheck profanityCheck = new ProfanityCheckGateway(HttpClient.newHttpClient());
        final SignupViewModel signupViewModel = new SignupViewModel();
        final SignupPresenter signupPresenter = new SignupPresenter(signupViewModel);
        final SignupInteractor signupInteractor =
                new SignupInteractor(userDataAccessObject, signupPresenter, userFactory, profanityCheck);
        final SignupController signupController = new SignupController(signupInteractor);

        final OverlayFactory loginOverlayFactory =
                onClose -> new LoginOverlay(onClose, loginController, loginViewModel);
        final OverlayFactory signupOverlayFactory =
                onClose -> new SignupOverlay(onClose, signupController, signupViewModel);

        // --- Get hint chain ---
        final GetHintViewModel getHintViewModel = new GetHintViewModel();
        final GetHintPresenter getHintPresenter = new GetHintPresenter(getHintViewModel, viewManager);
        final GetHintInteractor getHintInteractor = new GetHintInteractor(getHintPresenter, gameAssetManager,
                userSessionTracking);
        final GetHintController getHintController = new GetHintController(getHintInteractor);

        // --- Get hint overlay ---
        // OverlayFactory getHintOverlayFactory = onClose -> new HintOverlay(getHintViewModel, onClose);
        final HintOverlay hintOverlay = new HintOverlay(getHintViewModel, viewManager);

        // --- View Model for starting game ---
        final InGameViewModel inGameViewModel = new InGameViewModel();

        // --- Tutorial Mode & Story Mode start up chain ---
        final TutorialAndStoryModeStartUpPresenter tutorialAndStoryModeStartUpPresenter =
                new TutorialAndStoryModeStartUpPresenter(inGameViewModel, viewManagerModel);
        final TutorialAndStoryModeStartUpInteractor tutorialAndStoryModeStartUpInteractor =
                new TutorialAndStoryModeStartUpInteractor(tutorialAndStoryModeStartUpPresenter,
                        gameAssetManager, userSessionTracking);
        final TutorialAndStoryModeStartUpController tutorialAndStoryModeStartUpController =
                new TutorialAndStoryModeStartUpController(tutorialAndStoryModeStartUpInteractor);

        // --- Browse rooms chain ---
        final BrowseRoomsViewModel browseRoomsViewModel = new BrowseRoomsViewModel();
        final BrowseRoomsPresenter browseRoomsPresenter = new BrowseRoomsPresenter(browseRoomsViewModel,
                viewManagerModel);
        final BrowseRoomsInteractor browseRoomsInteractor = new BrowseRoomsInteractor(gameAssetManager,
                browseRoomsPresenter);
        final BrowseRoomsController browseRoomsController = new BrowseRoomsController(browseRoomsInteractor);

        // --- Quick Mode chain ---
        final QuickModeStartUpPresenter quickModeStartUpPresenter = new QuickModeStartUpPresenter(inGameViewModel,
                viewManagerModel);
        final QuickModeStartUpInteractor quickModeStartUpInteractor = new QuickModeStartUpInteractor(
                quickModeStartUpPresenter, gameAssetManager, userSessionTracking);
        final QuickModeStartUpController quickModeStartUpController = new QuickModeStartUpController(
                quickModeStartUpInteractor);

        // --- Items & Inventory Chain ---
        final InventoryViewModel inventoryViewModel = new InventoryViewModel();
        final InventoryPresenter inventoryPresenter = new InventoryPresenter(inventoryViewModel, inGameViewModel);

        // Updated to include itemRegistry as the 2nd argument
        final PickUpInteractor pickUpInteractor = new PickUpInteractor(
                userSessionTracking,
                gameAssetManager,
                inventoryPresenter
        );

        final PickUpController pickUpController = new PickUpController(pickUpInteractor);

        // --- Select Item Chain ---
        final SelectItemPresenter selectItemPresenter = new SelectItemPresenter();
        final SelectItemInteractor selectItemInteractor = new SelectItemInteractor(userSessionTracking,
                selectItemPresenter);
        final SelectItemController selectItemController = new SelectItemController(selectItemInteractor);

        // --- Main menu ---
        final MainMenuView mainMenu = new MainMenuView(
                viewManagerModel, mainMenuViewModel, loggedInViewModel,
                loginOverlayFactory, signupOverlayFactory,
                () -> {
                    if (loggedInViewModel.getState().isLoggedIn()) {
                        logoutController.executeLogoutWithoutSave(loggedInViewModel.getState().getUsername());
                    }
                    else {
                        // Guest was never logged in — just navigate home, no logout flow, no message.
                        viewManagerModel.setState(mainMenuString);
                        viewManagerModel.firePropertyChanged();
                    }
                },
                audioControlView,
                tutorialAndStoryModeStartUpController,
                browseRoomsController);

        signupPresenter.setSwitchToLoginCallback(mainMenu::switchFromSignupToLogin);

        // --- Browse Rooms ---
        final BrowseRoomsView browseRoomsView = new BrowseRoomsView(viewManager,
                mainMenu, browseRoomsViewModel, quickModeStartUpController);

        // --- Interactable Zoom Chain ---
        final ZoomViewModel zoomViewModel = new ZoomViewModel();
        final ZoomPresenter zoomPresenter = new ZoomPresenter(zoomViewModel, viewManagerModel);
        final ZoomInteractor zoomInteractor = new ZoomInteractor(gameAssetManager, zoomPresenter);
        final ZoomController zoomController = new ZoomController(zoomInteractor);

        // --- Interactable Interact Chain ---
        final InteractViewModel interactViewModel = new InteractViewModel();
        final InteractPresenter interactPresenter = new InteractPresenter(interactViewModel, inventoryViewModel,
                zoomInteractor, viewManagerModel, viewManager);
        final InteractInteractor interactInteractor = new InteractInteractor(gameAssetManager, interactPresenter,
                userSessionTracking);
        final InteractController interactController = new InteractController(interactInteractor);

        // --- Puzzle EnterExit Chain ---
        final EnterExitViewModel enterExitViewModel = new EnterExitViewModel();
        final EnterExitPresenter enterExitPresenter = new EnterExitPresenter(enterExitViewModel, interactViewModel,
                viewManagerModel, viewManager);
        final EnterExitInteractor enterExitInteractor = new EnterExitInteractor(gameAssetManager, enterExitPresenter,
                userSessionTracking);
        final EnterExitController enterExitController = new EnterExitController(enterExitInteractor);

        // --- Puzzle Solve Chain ---
        final SolvePresenter solvePresenter = new SolvePresenter(interactViewModel, inventoryViewModel, zoomInteractor,
                viewManagerModel, viewManager);
        final SolveInteractor solveInteractor = new SolveInteractor(gameAssetManager, solvePresenter,
                userSessionTracking);
        final SolveController solveController = new SolveController(solveInteractor);

        // --- Interactable and Puzzle Views ---
        final ZoomView zoomView = new ZoomView(zoomController, zoomViewModel, interactController, enterExitController);
        final InteractOverlay interactOverlay = new InteractOverlay(interactViewModel, viewManager);
        final PuzzleView puzzleView = new PuzzleView(enterExitViewModel, enterExitController, solveController);

        // --- In-game ---
        final GameObjectActionDispatcher gameObjectActionDispatcher = new GameObjectActionDispatcher(zoomController,
                getHintController, pickUpController);
        // Create game menu view
        final GameMenuView gameMenuView =
                new GameMenuView(viewManager,
                loggedInViewModel,
                audioControlView,
                        () -> {
                            if (userSessionTracking.getCurrentUser() != null) {
                                final String username = loggedInViewModel.getState().getUsername();
                                saveProgressController.execute(username);
                            }
                        },
                        // --- On save & quit ---
                        () -> {
                            if (userSessionTracking.getCurrentUser() != null) {
                                final String username = loggedInViewModel.getState().getUsername();
                                logoutController.executeLogoutWithSave(username);
                            }
                        },
                        // --- On quit ---
                        () -> {
                            if (loggedInViewModel.getState().isLoggedIn()) {
                                logoutController.executeLogoutWithoutSave(loggedInViewModel.getState().getUsername());
                            }
                            else {
                                viewManagerModel.setState(mainMenuString);
                                viewManagerModel.firePropertyChanged();
                            }
                        }
                );
        final InventoryOverlay inventoryOverlay = new InventoryOverlay(viewManager, inventoryViewModel);
        inventoryOverlay.setSelectItemController(selectItemController);
        final InGameView inGameView = new InGameView(inGameViewModel, gameMenuView, inventoryOverlay,
                gameObjectActionDispatcher);

        // --- Register every top-level screen by name ---
        viewManager.registerView(mainMenuString, mainMenu);
        viewManager.registerView("browse rooms", browseRoomsView);
        viewManager.registerView("in-game", inGameView);

        // --- Register Interactable and Puzzle Views ---
        viewManager.registerView("Zoom", zoomView);
        viewManager.registerOverlay("Interact", interactOverlay);
        viewManager.registerView("Puzzle", puzzleView);
        viewManager.registerOverlay("get hint", hintOverlay, true);

        // --- Register In-game menu ---
        viewManager.registerOverlay("in-game menu", gameMenuView);

        viewManager.registerOverlay("inventory", inventoryOverlay);

        // --- Trigger the first screen ---
        viewManagerModel.firePropertyChanged();
    }
}
