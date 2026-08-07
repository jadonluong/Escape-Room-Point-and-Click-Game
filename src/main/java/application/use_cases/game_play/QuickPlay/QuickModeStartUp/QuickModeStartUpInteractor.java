package application.use_cases.game_play.QuickPlay.QuickModeStartUp;

import java.util.HashMap;
import java.util.Map;

import application.game_registry.RoomRegistry;
import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.game_play.UserDataAccessInterface;
import domain.entities.Room.Room;
import domain.entities.User.User;

/**
 * Interactor responsible for starting a Quick Play game in a selected room.
 *
 * <p>Retrieves the current user and selected room, updates the user's game
 * state, and prepares the room objects and image information required to
 * start the game.</p>
 */
public class QuickModeStartUpInteractor implements QuickModeStartUpInputBoundary {
    private final QuickModeStartUpOutputBoundary presenter;
    private final RoomRegistry dataAccess;
    private final UserDataAccessInterface quickModeStartUpDataAccess;

    /**
     * Creates a {@code QuickModeStartUpInteractor} with the specified
     * presenter and data access interfaces.
     *
     * @param presenter the output boundary used to present the game start result
     * @param dataAccess the room registry used to retrieve the selected room
     * @param quickModeStartUpDataAccess the data access interface used to
     *                                   retrieve the current user
     */
    public QuickModeStartUpInteractor(QuickModeStartUpOutputBoundary presenter,
                                      RoomRegistry dataAccess,
                                      UserDataAccessInterface quickModeStartUpDataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
        this.quickModeStartUpDataAccess = quickModeStartUpDataAccess;
    }

    /**
     * Starts Quick Play in the room selected by the user.
     *
     * <p>If no user is selected, the room ID is invalid, or the selected room
     * cannot be found, a failure view is prepared. Otherwise, the user's game
     * state and current room are updated, and the objects required to render
     * the room are passed to the presenter.</p>
     *
     * @param inputData the input data containing the ID of the room to enter
     */
    @Override
    public void execute(QuickModeStartUpInputData inputData) {
        final User currentUser = quickModeStartUpDataAccess.getCurrentUser();

        if (currentUser == null) {
            presenter.prepareFailView("No user selected");
        }
        else {
            currentUser.setActiveGameMode("QuickMode");

            final String roomId = inputData.getTargetRoom();

            if (roomId == null || roomId.trim().isEmpty()) {
                presenter.prepareFailView("Invalid room ID provided.");
            }
            else {
                currentUser.saveCurrentRoomID(roomId);

                final Room targetRoom = dataAccess.getRoomById(roomId);

                if (targetRoom == null) {
                    presenter.prepareFailView("Could not load room with ID: " + roomId);
                }
                else {
                    currentUser.unlockRoom(targetRoom);
                    currentUser.switchRoom(targetRoom);

                    final Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();

                    // Fetch all data that is needed for rendering

                    // Put Interactable
                    targetRoom.getInteractables().forEach(interactable -> {
                        objectsToDisplay.put(interactable.getId(),
                                new ObjectsInfo(interactable.getSprite(),
                                        targetRoom.getPosition(interactable.getId()),
                                        "Interactable"));
                    });

                    // Put Items
                    targetRoom.getItems().forEach(item -> {
                        if (!currentUser.hasItemID(item.getId())) {
                            objectsToDisplay.put(item.getId(),
                                    new ObjectsInfo(item.getImagePath(),
                                            targetRoom.getPosition(item.getId()), "Item"));
                        }
                    });

                    // Put Hints
                    targetRoom.getHints().forEach(hint -> {
                        objectsToDisplay.put(hint.getObjectID(),
                                new ObjectsInfo(hint.getImagePath(),
                                        targetRoom.getPosition(hint.getObjectID()), "Hint"));
                    });

                    final QuickModeStartUpOutputData outputData =
                            new QuickModeStartUpOutputData(
                                    objectsToDisplay,
                                    targetRoom.getImagePath());

                    presenter.prepareGameStartView(outputData);
                }
            }
        }
    }
}
