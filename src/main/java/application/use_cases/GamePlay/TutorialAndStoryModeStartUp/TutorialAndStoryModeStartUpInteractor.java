package application.use_cases.GamePlay.TutorialAndStoryModeStartUp;


import application.use_cases.GamePlay.ObjectsInfo;
import application.use_cases.GamePlay.UserDataAccessInterface;
import domain.entities.Room.Room;
import domain.entities.User.User;

import java.util.HashMap;
import java.util.Map;

public class TutorialAndStoryModeStartUpInteractor implements TutorialAndStoryModeStartUpInputBoundary {
    private final StartUpDataAccessInterface dataAccess;
    private final TutorialAndStoryModeStartUpOutputBoundary presenter;
    private final UserDataAccessInterface userDataAccess;

    public TutorialAndStoryModeStartUpInteractor(TutorialAndStoryModeStartUpOutputBoundary
                                                         TutorialAndStoryModeStartUpPresenter
            , StartUpDataAccessInterface dataAccess,
                                                 UserDataAccessInterface userDataAccess) {
        this.dataAccess = dataAccess;
        this.presenter = TutorialAndStoryModeStartUpPresenter;
        this.userDataAccess = userDataAccess;

    }


    @Override
    public void execute(TutorialAndStoryModeStartUpInputData inputData) {

        Room startingRoom;
        User currentUser = userDataAccess.getCurrentUser();

        if (currentUser == null) {
            presenter.prepareFailView("User is null");
            return;
        }

        switch (inputData.getMode()) {
            case "TUTORIAL" -> {
                currentUser.setActiveGameMode("TutorialMode");
                startingRoom = dataAccess.findStartingRoomForTut();
                currentUser.unlockRoom(startingRoom);
            }
            case "STORY"    -> {
                currentUser.setActiveGameMode("StoryMode");
                startingRoom = dataAccess.findStartingRoomForStory();
                currentUser.unlockRoom(startingRoom);
            }
            default -> {
                presenter.prepareFailView("Invalid mode selected: " + inputData.getMode());
                return;
            }
        }

        currentUser.unlockRoom(startingRoom);
        currentUser.switchRoom(startingRoom);


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
