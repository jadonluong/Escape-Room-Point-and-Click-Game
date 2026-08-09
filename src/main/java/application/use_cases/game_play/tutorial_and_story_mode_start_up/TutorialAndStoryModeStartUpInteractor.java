package application.use_cases.game_play.tutorial_and_story_mode_start_up;

import java.util.HashMap;
import java.util.Map;

import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.game_play.UserDataAccessInterface;
import domain.entities.room.Room;
import domain.entities.user.User;

/**
 * Interactor responsible for starting the tutorial or story game mode.
 *
 * <p>Retrieves the appropriate starting room, updates the current user's
 * game mode and room, and prepares the room objects required for display.</p>
 */
public class TutorialAndStoryModeStartUpInteractor
        implements TutorialAndStoryModeStartUpInputBoundary {

    private final StartUpDataAccessInterface dataAccess;
    private final TutorialAndStoryModeStartUpOutputBoundary presenter;
    private final UserDataAccessInterface userDataAccess;

    /**
     * Creates a {@code TutorialAndStoryModeStartUpInteractor} with the
     * specified presenter and data access interfaces.
     *
     * @param TutorialAndStoryModeStartUpPresenter the output boundary used
     *                                              to present the game start result
     * @param dataAccess the data access interface used to retrieve the
     *                   starting rooms
     * @param userDataAccess the data access interface used to retrieve
     *                       the current user
     */
    public TutorialAndStoryModeStartUpInteractor(
            TutorialAndStoryModeStartUpOutputBoundary TutorialAndStoryModeStartUpPresenter,
            StartUpDataAccessInterface dataAccess,
            UserDataAccessInterface userDataAccess) {
        this.dataAccess = dataAccess;
        this.presenter = TutorialAndStoryModeStartUpPresenter;
        this.userDataAccess = userDataAccess;
    }

    /**
     * Starts the selected tutorial or story game mode.
     *
     * <p>If no current user is available or an invalid game mode is selected,
     * a failure view is prepared. Otherwise, the appropriate starting room
     * is retrieved, unlocked, and set as the user's current room. The objects
     * required to display the room are then passed to the presenter.</p>
     *
     * @param inputData the input data containing the selected game mode
     */
    @Override
    public void execute(TutorialAndStoryModeStartUpInputData inputData) {

        Room startingRoom;
        User currentUser = userDataAccess.getCurrentUser();

        if (currentUser == null) {
            presenter.prepareFailView("user is null");
            return;
        }

        switch (inputData.getMode()) {
            case "TUTORIAL" -> {
                currentUser.setActiveGameMode("TutorialMode");
                startingRoom = dataAccess.findStartingRoomForTut();
            }
            case "STORY" -> {
                currentUser.setActiveGameMode("StoryMode");
                startingRoom = dataAccess.findStartingRoomForStory();
            }
            default -> {
                presenter.prepareFailView("Invalid mode selected: " + inputData.getMode());
                return;
            }
        }

        currentUser.unlockRoom(startingRoom);
        currentUser.switchRoom(startingRoom);

        Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();

        // fetch all data that is needed for rendering.
        // It contains ObjectId as key(for interactable/hint/item), and info (which is a record
        // of ImagePath and Position) as value.

        startingRoom.getInteractables().forEach(interactable -> {
            objectsToDisplay.put(interactable.getId(),
                    new ObjectsInfo(interactable.getSprite(),
                            startingRoom.getPosition(interactable.getId()),
                            "Interactable"));
        });

        startingRoom.getItems().forEach(item -> {
            if (!currentUser.hasItemID(item.getId())) {
                objectsToDisplay.put(item.getId(),
                        new ObjectsInfo(item.getImagePath(),
                                startingRoom.getPosition(item.getId()),
                                "Item"));
            }
        });

        startingRoom.getHints().forEach(hint -> {
            objectsToDisplay.put(hint.getObjectID(),
                    new ObjectsInfo(hint.getImagePath(),
                            startingRoom.getPosition(hint.getObjectID()),
                            "Hint"));
        });

        // wrap the data
        TutorialAndStoryModeStartUpOutPutData outPutData =
                new TutorialAndStoryModeStartUpOutPutData(
                        objectsToDisplay,
                        startingRoom.getImagePath());

        presenter.prepareGameStartView(outPutData);
    }
}
