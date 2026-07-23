package interface_adapter.GamePlay.InGameMenu.Quit;

import application.use_cases.GamePlay.Quit.QuitInputBoundary;

public class QuitController {
    private final QuitInputBoundary quitUseCaseInteractor;

    public QuitController(QuitInputBoundary quitUseCaseInteractor) {
        this.quitUseCaseInteractor = quitUseCaseInteractor;
    }

    public void execute() {
        quitUseCaseInteractor.execute();
    }
}
