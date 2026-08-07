package application.use_cases.game_play.TutorialAndStoryModeStartUp;

import java.util.Map;

import application.use_cases.game_play.ObjectsInfo;

/**
 * Output data containing the information required to display the starting
 * room for tutorial or story mode.
 */
public class TutorialAndStoryModeStartUpOutPutData {
    private Map<String, ObjectsInfo> objectsToDisplay;
    private String roomImgPath;

    /**
     * Creates output data containing the objects to display and the room image.
     *
     * @param objectsToDisplay a map containing the objects to display in the room
     * @param roomImgPath the path to the room's background image
     */
    public TutorialAndStoryModeStartUpOutPutData(
            Map<String, ObjectsInfo> objectsToDisplay,
            String roomImgPath) {
        this.objectsToDisplay = objectsToDisplay;
        this.roomImgPath = roomImgPath;
    }

    /**
     * Returns the objects to display in the starting room.
     *
     * @return a map containing the objects to display
     */
    public Map<String, ObjectsInfo> getObjectToDisplay() {
        return objectsToDisplay;
    }

    /**
     * Returns the path to the starting room's background image.
     *
     * @return the room image path
     */
    public String getRoomImgPath() {
        return roomImgPath;
    }
}
