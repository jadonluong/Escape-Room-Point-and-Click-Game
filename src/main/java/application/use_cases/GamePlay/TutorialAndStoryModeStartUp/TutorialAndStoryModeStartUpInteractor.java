package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;


import domain.entities.Room.Room;

import java.util.HashMap;
import java.util.Map;

public class TutorialAndStoryModeStartUpInteractor implements TutorialAndStoryModeStartUpInputBoundary {
    private final StartUpDataAccessInterface dataAccess;
    private final TutorialAndStoryModeStartUpOutputBoundary presenter;

    public TutorialAndStoryModeStartUpInteractor(TutorialAndStoryModeStartUpOutputBoundary selectModePresenter
            , StartUpDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
        this.presenter = selectModePresenter;

    }


    @Override
    public void execute(TutorialAndStoryModeStartUpInputData inputData) {

        String startingRoomId;

        switch (inputData.getMode()) {
            case "TUTORIAL" -> startingRoomId = dataAccess.findStartingRoomForTut();
            case "STORY"    -> startingRoomId = dataAccess.findStartingRoomForStory();
            default -> {
                presenter.prepareFailView("Invalid mode selected: " + inputData.getMode());
                return;
            }
        };


        Room room = dataAccess.findRoom(startingRoomId);
        Map<String,String> ObjectsToDisplay = new HashMap<>();

        //fetch all data that is needed for rendering
        room.getInteractables().forEach(interactable -> {
            ObjectsToDisplay.put(interactable.getId(), interactable.getImagePath());
        });
        room.getItems().forEach(item -> {
            ObjectsToDisplay.put(item.getId(), item.getImagePath());
        });
        room.getHints().forEach(hint -> {
            ObjectsToDisplay.put(hint.getObjectID(),  hint.getImagePath());
        });

        //wrap the data
        TutorialAndStoryModeStartUpOutPutData outPutData = new TutorialAndStoryModeStartUpOutPutData(ObjectsToDisplay);
        presenter.prepareGameStartView(outPutData);
    }
}
