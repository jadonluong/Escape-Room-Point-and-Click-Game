package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

public interface SelectRoomOutputBoundary {
    void prepareSuccessView(SelectRoomOutputData outputData);
    void prepareFailView(String error);
}
