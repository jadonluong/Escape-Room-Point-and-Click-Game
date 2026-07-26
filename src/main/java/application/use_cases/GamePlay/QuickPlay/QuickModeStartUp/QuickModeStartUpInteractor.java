package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import domain.entities.Room.Room;

import java.util.HashMap;
import java.util.Map;


public class QuickModeStartUpInteractor implements QuickModeStartUpInputBoundary {
    private final QuickModeStartUpOutputBoundary presenter;
    private final StartUpDataAccessInterface dataAccess;

    public QuickModeStartUpInteractor(QuickModeStartUpOutputBoundary presenter, StartUpDataAccessInterface dataAccess) {
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

            Room targetRoom = dataAccess.getRoomForQuickMode(roomId);

            if (targetRoom == null) {
                presenter.prepareFailView("Could not load room with ID: " + roomId);
                return;
            }

            Map<String, String> objectsToDisplay = new HashMap<>();

            // Fetch all data that is needed for rendering
            targetRoom.getInteractables().forEach(interactable -> {
                objectsToDisplay.put(interactable.getId(), interactable.getImagePath());
            });
            targetRoom.getItems().forEach(item -> {
                objectsToDisplay.put(item.getId(), item.getImagePath());
            });
            targetRoom.getHints().forEach(hint -> {
                objectsToDisplay.put(hint.getObjectID(), hint.getImagePath());
            });

            QuickModeStartUpOutputData outputData = new QuickModeStartUpOutputData(objectsToDisplay);

            presenter.prepareGameStartView(outputData);

    }

}
