package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import application.use_cases.GamePlay.ObjectsInfo;
import domain.entities.Room.Room;

import java.util.HashMap;
import java.util.Map;

import application.game_registry.RoomRegistry;

public class QuickModeStartUpInteractor implements QuickModeStartUpInputBoundary {
    private final QuickModeStartUpOutputBoundary presenter;
    private final RoomRegistry dataAccess;

    public QuickModeStartUpInteractor(QuickModeStartUpOutputBoundary presenter, RoomRegistry dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(QuickModeStartUpInputData inputData) {

            String roomId = inputData.getTargetRoom();

            if (roomId == null || roomId.trim().isEmpty()) {
                presenter.prepareFailView("Invalid room ID provided.");
                return;
            }

            Room targetRoom = dataAccess.getRoomByID(roomId);

            if (targetRoom == null) {
                presenter.prepareFailView("Could not load room with ID: " + roomId);
                return;
            }

            Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();

            // Fetch all data that is needed for rendering

            // Put Interactable
            targetRoom.getInteractables().forEach(interactable -> {
                objectsToDisplay.put(interactable.getId(),
                        new ObjectsInfo(interactable.getImagePath(),
                                targetRoom.getPosition(interactable.getId())));
            });

            // Put Items
            targetRoom.getItems().forEach(item -> {
                objectsToDisplay.put(item.getId(),
                        new ObjectsInfo(item.getImagePath(),
                                targetRoom.getPosition(item.getId())));
            });

            // Put Hints
            targetRoom.getHints().forEach(hint -> {
                objectsToDisplay.put(hint.getObjectID(),
                        new ObjectsInfo(hint.getImagePath()
                                ,targetRoom.getPosition(hint.getObjectID())));
            });

            QuickModeStartUpOutputData outputData = new QuickModeStartUpOutputData(objectsToDisplay);

            presenter.prepareGameStartView(outputData);

    }

}
