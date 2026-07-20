package application.use_cases.GamePlay.QuickPlay.SelectRoom;

public interface SelectRoomOutputBoundary {
    void prepareSuccessView(SelectRoomOutputData outputData);
    void prepareFailView(String error);
}
