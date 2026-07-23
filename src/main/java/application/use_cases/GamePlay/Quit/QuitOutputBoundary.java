package application.use_cases.GamePlay.Quit;

public interface QuitOutputBoundary {
    void prepareMenuView();
    void prepareFailView(String message);
}
