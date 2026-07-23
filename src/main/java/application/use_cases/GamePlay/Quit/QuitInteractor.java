package application.use_cases.GamePlay.Quit;

public class QuitInteractor implements QuitInputBoundary {
    private final QuitOutputBoundary quitPresenter;
    //Can also inject a GameStateManager, SaveDataAccess.

    public QuitInteractor(QuitOutputBoundary quitPresenter) {
        this.quitPresenter = quitPresenter;
    }

    @Override
    public void execute() {
        // 1. Run any clean-up here
        // 2. Alert the presenter that it is safe to tear down the UI
        quitPresenter.prepareMenuView();;
    }
}