package interface_adapter.GamePlay.SelectRoom;

import application.use_cases.GamePlay.SelectMode.SelectModeInputBoundary;
import application.use_cases.GamePlay.SelectMode.SelectModeInputData;

public class SelectRoomController {
    private final SelectModeInputBoundary selectRoomUseCaseInteractor;

    public SelectRoomController(SelectModeInputBoundary selectRoomUseCaseInteractor) {
        this.selectRoomUseCaseInteractor = selectRoomUseCaseInteractor;
    }

    /**
     * Triggered by the UI View layer when a player attempts to select a room.
     * @param roomId The unique identifier of the room being selected (e.g., "Room_A", "Kitchen_01")
     */
    public void execute(String roomId) {
        SelectModeInputData inputData = new SelectModeInputData(roomId);
        selectRoomUseCaseInteractor.execute(inputData);
    }
}
