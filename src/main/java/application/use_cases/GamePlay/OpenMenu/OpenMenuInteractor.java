package application.use_cases.GamePlay.OpenMenu;

public class OpenMenuInteractor implements OpenMenuInputBoundary {
    private final OpenMenuOutputBoundary openMenuPresenter;

    // Dependency Injection via constructor
    public OpenMenuInteractor(OpenMenuOutputBoundary openMenuPresenter){
        this.openMenuPresenter = openMenuPresenter;
    }

    @Override
    public void execute() {
        // 1. Pause the timer
        openMenuPresenter.prepareMenuView();
    }
}
