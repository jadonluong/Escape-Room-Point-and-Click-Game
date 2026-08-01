package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;


import application.use_cases.GamePlay.ObjectsInfo;
import domain.entities.Room.Room;

import java.util.HashMap;
import java.util.Map;

public class TutorialAndStoryModeStartUpInteractor implements TutorialAndStoryModeStartUpInputBoundary {
    private final StartUpDataAccessInterface dataAccess;
    private final TutorialAndStoryModeStartUpOutputBoundary presenter;

    public TutorialAndStoryModeStartUpInteractor(TutorialAndStoryModeStartUpOutputBoundary
                                                         TutorialAndStoryModeStartUpPresenter
            , StartUpDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
        this.presenter = TutorialAndStoryModeStartUpPresenter;

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


        Map<String, ObjectsInfo> ObjectsToDisplay = new HashMap<>();

        //fetch all data that is needed for rendering.
        //It contains ObjectId as key(for interactable/hint/item), and info (which is a record
        //of ImagePath and Position) as value.
        startingRoom.getInteractables().forEach(interactable -> {
            ObjectsToDisplay.put(interactable.getId(),
                    new ObjectsInfo(interactable.getSprite(),
                            startingRoom.getPosition(interactable.getId()),
                            "Interactable"));
        });
        startingRoom.getItems().forEach(item -> {
            ObjectsToDisplay.put(item.getId(),
                    new ObjectsInfo(item.getImagePath(),
                            startingRoom.getPosition(item.getId()),
                            "Item") );
        });
        startingRoom.getHints().forEach(hint -> {
            ObjectsToDisplay.put(hint.getObjectID(),
                    new ObjectsInfo(hint.getImagePath(),
                            startingRoom.getPosition(hint.getObjectID()),
                            "Hint"));
        });

        //wrap the data
        TutorialAndStoryModeStartUpOutPutData outPutData = new TutorialAndStoryModeStartUpOutPutData(ObjectsToDisplay,
                startingRoom.getImagePath());
        presenter.prepareGameStartView(outPutData);
    }
}
