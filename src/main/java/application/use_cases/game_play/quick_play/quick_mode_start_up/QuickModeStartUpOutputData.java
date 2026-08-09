package application.use_cases.game_play.quick_play.quick_mode_start_up;

import java.util.Map;

import application.use_cases.game_play.ObjectsInfo;

/**
 * Output data containing the information required to display the selected
 * room when starting Quick Play.
 */
public class QuickModeStartUpOutputData {
    private final Map<String, ObjectsInfo> roomInfo;
    private String roomImgPath;

    /**
     * Creates output data containing the room's objects and image path.
     *
     * @param roomInfo a map containing the objects to display in the room
     * @param roomImgPath the path to the room's background image
     */
    public QuickModeStartUpOutputData(Map<String, ObjectsInfo> roomInfo, String roomImgPath) {
        this.roomInfo = roomInfo;
        this.roomImgPath = roomImgPath;
    }

    /**
     * Returns the objects to display in the room.
     *
     * @return a map containing the objects to display
     */
    public Map<String, ObjectsInfo> getObjectToDisplay() {
        return roomInfo;
    }

    /**
     * Returns the path to the room's background image.
     *
     * @return the room image path
     */
    public String getRoomImgPath() {
        return roomImgPath;
    }
}
