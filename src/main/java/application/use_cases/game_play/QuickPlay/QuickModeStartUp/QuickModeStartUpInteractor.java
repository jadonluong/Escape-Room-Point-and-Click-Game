package application.use_cases.game_play.QuickPlay.QuickModeStartUp;

import java.util.HashMap;
import java.util.Map;

import application.game_registry.RoomRegistry;
import application.use_cases.game_play.ObjectsInfo;
import application.use_cases.game_play.UserDataAccessInterface;
import domain.entities.Room.Room;
import domain.entities.User.User;

public class QuickModeStartUpInteractor implements QuickModeStartUpInputBoundary {
    private final QuickModeStartUpOutputBoundary presenter;
    private final RoomRegistry dataAccess;
    private final UserDataAccessInterface quickModeStartUpDataAccess;

    public QuickModeStartUpInteractor(QuickModeStartUpOutputBoundary presenter,
                                      RoomRegistry dataAccess,
                                      UserDataAccessInterface quickModeStartUpDataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
        this.quickModeStartUpDataAccess = quickModeStartUpDataAccess;
    }

    @Override
    public void execute(QuickModeStartUpInputData inputData) {
        final User currentUser = quickModeStartUpDataAccess.getCurrentUser();

        if (currentUser == null) {
            presenter.prepareFailView("No user selected");
            return;
        }

        currentUser.setActiveGameMode("QuickMode");

        final String roomId = inputData.getTargetRoom();

        if (roomId == null || roomId.trim().isEmpty()) {
            presenter.prepareFailView("Invalid room ID provided.");
            return;
        }

        currentUser.saveCurrentRoomID(roomId);

        final Room targetRoom = dataAccess.getRoomById(roomId);

        if (targetRoom == null) {
            presenter.prepareFailView("Could not load room with ID: " + roomId);
            return;
        }

        currentUser.unlockRoom(targetRoom);
        currentUser.switchRoom(targetRoom);

        final Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();

        // Fetch all data that is needed for rendering

        // Put Interactable
        targetRoom.getInteractables().forEach(interactable -> {
            objectsToDisplay.put(interactable.getId(),
                    new ObjectsInfo(interactable.getSprite(),
                            targetRoom.getPosition(interactable.getId()),
                            "Interactable")); });

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
                    new ObjectsInfo(hint.getImagePath(), targetRoom.getPosition(hint.getObjectID()), "Hint")); });

        final QuickModeStartUpOutputData outputData = new QuickModeStartUpOutputData(objectsToDisplay,
                targetRoom.getImagePath());

        presenter.prepareGameStartView(outputData);

    }

}
