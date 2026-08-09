package app;

import java.net.http.HttpClient;

import application.use_cases.audio.toggle_music.ToggleMusicInteractor;
import application.use_cases.audio.toggle_sfx.ToggleSfxInteractor;
import application.use_cases.crafting.CraftingInteractor;
import application.use_cases.hint.get_hint.GetHintInteractor;
import application.use_cases.interactable.interact.InteractInteractor;
import application.use_cases.interactable.zoom.ZoomInteractor;
import application.use_cases.item.pick_up.PickUpInteractor;
import application.use_cases.item.select_item.SelectItemInteractor;
import application.use_cases.puzzle.enter_exit.EnterExitInteractor;
import application.use_cases.puzzle.solve.SolveInteractor;
import application.use_cases.user.LiveUserSessionTracking;
import application.use_cases.user.login.LoginInteractor;
import application.use_cases.user.logout.LogoutInteractor;
import application.use_cases.user.save_and_logout.SaveAndLogoutInteractor;
import application.use_cases.user.save_progress.SaveProgressInteractor;
import application.use_cases.user.signup.ProfanityCheck;
import application.use_cases.user.signup.SignupInteractor;
import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsInteractor;
import application.use_cases.game_play.quick_play.quick_mode_start_up.QuickModeStartUpInteractor;
import application.use_cases.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpInteractor;
import data_access.GameAssetManager;
import data_access.JsonUserDataAccessObject;
import data_access.PuzzleGenerator;
import domain.entities.audio.AudioSettings;
import domain.entities.hint.CommonHintFactory;
import domain.entities.hint.HintFactory;
import domain.entities.interactable.CommonInteractableFactory;
import domain.entities.interactable.InteractableFactory;
import domain.entities.item.CommonItemFactory;
import domain.entities.item.ItemFactory;
import domain.entities.puzzle.CommonPuzzleFactory;
import domain.entities.puzzle.PuzzleFactory;
import domain.entities.room.CommonRoomFactory;
import domain.entities.room.RoomFactory;
import domain.entities.user.CommonUserFactory;
import domain.entities.user.CommonUserFactoryClass;
import domain.entities.user.GuestUserFactory;
import domain.entities.user.GuestUserFactoryClass;
import domain.entities.user.User;
import infrastructure.AnagramApiClient;
import infrastructure.CryptogramApiClient;
import interface_adapter.audio.AudioViewModel;
import interface_adapter.audio.ToggleMusicController;
import interface_adapter.audio.ToggleMusicPresenter;
import interface_adapter.audio.ToggleSfxController;
import interface_adapter.audio.ToggleSfxPresenter;
import interface_adapter.game_play.browse_rooms.BrowseRoomsController;
import interface_adapter.game_play.browse_rooms.BrowseRoomsPresenter;
import interface_adapter.game_play.browse_rooms.BrowseRoomsViewModel;
import interface_adapter.game_play.InGameViewModel;
import interface_adapter.game_play.quick_mode_start_up.QuickModeStartUpController;
import interface_adapter.game_play.quick_mode_start_up.QuickModeStartUpPresenter;
import interface_adapter.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpController;
import interface_adapter.game_play.tutorial_and_story_mode_start_up.TutorialAndStoryModeStartUpPresenter;
import interface_adapter.hint.GetHintController;
import interface_adapter.hint.GetHintPresenter;
import interface_adapter.hint.GetHintViewModel;
import interface_adapter.interactable.interact.InteractController;
import interface_adapter.interactable.interact.InteractPresenter;
import interface_adapter.interactable.interact.InteractViewModel;
import interface_adapter.interactable.zoom.ZoomController;
import interface_adapter.interactable.zoom.ZoomPresenter;
import interface_adapter.interactable.zoom.ZoomViewModel;
import interface_adapter.inventory.*;
import interface_adapter.puzzle.enter_exit.EnterExitController;
import interface_adapter.puzzle.enter_exit.EnterExitPresenter;
import interface_adapter.puzzle.enter_exit.EnterExitViewModel;
import interface_adapter.puzzle.solve.SolveController;
import interface_adapter.puzzle.solve.SolvePresenter;
import interface_adapter.user.logged_in.LoggedInViewModel;
import interface_adapter.user.login.LoginController;
import interface_adapter.user.login.LoginPresenter;
import interface_adapter.user.login.LoginViewModel;
import interface_adapter.user.logout.LogoutController;
import interface_adapter.user.logout.LogoutPresenter;
import interface_adapter.user.main_menu.MainMenuViewModel;
import interface_adapter.user.save_progress.SaveProgressController;
import interface_adapter.user.save_progress.SaveProgressPresenter;
import interface_adapter.user.save_progress.SaveProgressViewModel;
import interface_adapter.user.signup.ProfanityCheckGateway;
import interface_adapter.user.signup.SignupController;
import interface_adapter.user.signup.SignupPresenter;
import interface_adapter.user.signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.item.PickUpController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.common.MusicPlayer;
import view.game.BrowseRoomsView;
import view.game.GameMenuView;
import view.game.GameObjectActionDispatcher;
import view.game.InGameView;
import view.hint.HintOverlay;
import view.ViewManager;
import view.common.AudioControlView;
import view.common.OverlayFactory;
import view.common.SoundPlayer;
import view.interactable.InteractOverlay;
import view.interactable.ZoomView;
import view.inventory.InventoryOverlay;
import view.main_menu.AuthOverlayFactories;
import view.main_menu.MainMenuView;
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
        final AnagramApiClient anagramApiClient = new AnagramApiClient(
                "apv_7700ad64-d8be-4591-8f67-607ed21edcce");
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
        final AudioSettings audioSettings = new AudioSettings();
        final AudioViewModel audioViewModel = new AudioViewModel();
        final ToggleSfxPresenter sfxPresenter = new ToggleSfxPresenter(audioViewModel);
        final ToggleSfxInteractor sfxInteractor = new ToggleSfxInteractor(sfxPresenter, audioSettings);
        final ToggleSfxController sfxController = new ToggleSfxController(sfxInteractor);
        final ToggleMusicPresenter musicPresenter = new ToggleMusicPresenter(audioViewModel);
        final ToggleMusicInteractor musicInteractor = new ToggleMusicInteractor(musicPresenter, audioSettings);
        final ToggleMusicController musicController = new ToggleMusicController(musicInteractor);

        // Repeated on purpose otherwise it only shows in one place
        final AudioControlView audioControlViewForMainMenu = new AudioControlView(sfxController, musicController,
                audioViewModel);
        final AudioControlView audioControlViewForPause = new AudioControlView(sfxController, musicController,
                audioViewModel);

        final SoundPlayer soundPlayer = new SoundPlayer("/audio/sfx/click.wav");
        final ViewManager viewManager = new ViewManager(
                primaryStage, viewManagerModel, soundPlayer,
                () -> audioViewModel.getState().isSfxOn());

        final MusicPlayer musicPlayer = new MusicPlayer("/audio/music/background.wav");
        musicPlayer.setMuted(!audioViewModel.getState().isMusicOn());
        musicPlayer.play();

        // Keep playback muted state in sync whenever the player toggles music on/off
        audioViewModel.addPropertyChangeListener(
                evt -> musicPlayer.setMuted(!audioViewModel.getState().isMusicOn()));

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

        // --- Crafting Chain ---
        final CraftingInteractor craftingInteractor = new CraftingInteractor(
                inventoryPresenter,                      // CraftingOutputBoundary
                userSessionTracking.getCurrentUser(),    // Active User domain entity
                // itemFactory,                             // ItemFactory
                gameAssetManager,                        // ItemRegistry
                gameAssetManager                         // CraftingDataAccessInterface
        );

        final CraftController craftController = new CraftController(craftingInteractor);

        // --- Select Item Chain ---
        final SelectItemPresenter selectItemPresenter = new SelectItemPresenter();
        final SelectItemInteractor selectItemInteractor = new SelectItemInteractor(userSessionTracking,
                selectItemPresenter);
        final SelectItemController selectItemController = new SelectItemController(selectItemInteractor);

        // --- Main menu ---
        final AuthOverlayFactories authOverlayFactories =
                new AuthOverlayFactories(loginOverlayFactory, signupOverlayFactory);

        final MainMenuView mainMenu = new MainMenuView(
                mainMenuViewModel,
                loggedInViewModel,
                authOverlayFactories,
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
                audioControlViewForMainMenu,
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
                audioControlViewForPause,
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
        inventoryOverlay.setCraftController(craftController);
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
