package application.use_cases.interactable.interact;

import application.use_cases.game_play.ObjectsInfo;

import java.util.Map;

/**
 * Output boundary for presenting the result of an interaction.
 */
public interface InteractOutputBoundary {

    /**
     * Prepares the success view for a successful interaction.
     *
     * @param outputData the output data containing the interaction result
     */
    void prepareSuccessView(InteractOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param errorMessage the message describing why the interaction failed
     */
    void prepareFailureView(String errorMessage);

    /**
     * Prepares the room view after the player moves to another room.
     *
     * @param imagePath the path to the Room's background image.
     * @param objectsToDisplay the imagePath and position of every displayed object in the Room.
     */
    void prepareRoomView(String imagePath, Map<String, ObjectsInfo> objectsToDisplay);

    /**
     * Prepares the main menu view.
     */
    void prepareMainMenuView();
}
