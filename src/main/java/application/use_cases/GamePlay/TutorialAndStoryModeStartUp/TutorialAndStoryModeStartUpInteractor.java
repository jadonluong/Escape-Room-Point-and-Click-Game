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

        Room startingRoom;

        switch (inputData.getMode()) {
            case "TUTORIAL" -> startingRoom = dataAccess.findStartingRoomForTut();
            case "STORY"    -> startingRoom = dataAccess.findStartingRoomForStory();
            default -> {
                presenter.prepareFailView("Invalid mode selected: " + inputData.getMode());
                return;
            }
        };


        Map<String,String> ObjectsToDisplay = new HashMap<>();

        //fetch all data that is needed for rendering
        startingRoom.getInteractables().forEach(interactable -> {
            ObjectsToDisplay.put(interactable.getId(), interactable.getImagePath());
        });
        startingRoom.getItems().forEach(item -> {
            ObjectsToDisplay.put(item.getId(), item.getImagePath());
        });
        startingRoom.getHints().forEach(hint -> {
            ObjectsToDisplay.put(hint.getObjectID(),  hint.getImagePath());
        });

        //wrap the data
        TutorialAndStoryModeStartUpOutPutData outPutData = new TutorialAndStoryModeStartUpOutPutData(ObjectsToDisplay);
        presenter.prepareGameStartView(outPutData);
    }
}
