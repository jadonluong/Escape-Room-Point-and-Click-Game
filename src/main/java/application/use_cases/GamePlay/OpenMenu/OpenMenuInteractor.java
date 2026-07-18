package application.use_cases.GamePlay.OpenMenu;

public class OpenMenuInteractor implements OpenMenuInputBoundary {
    private final OpenMenuOutputBoundary openMenuPresenter;

    // Dependency Injection via constructor
    public OpenMenuInteractor(OpenMenuOutputBoundary openMenuPresenter, Timer timer) {
        this.openMenuPresenter = openMenuPresenter;
        this.timer = timer;
    }

    @Override
    public void execute() {
        // 1. Pause the timer
        timer.setPaused(true);
        openMenuPresenter.prepareMenuView();
        //TODO: add anything necessary after the timer is implemented.
    }
}
