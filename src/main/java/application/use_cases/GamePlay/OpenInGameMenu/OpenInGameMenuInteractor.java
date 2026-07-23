package application.use_cases.GamePlay.OpenInGameMenu;

public class OpenInGameMenuInteractor implements OpenInGameMenuInputBoundary {
    private final OpenInGameMenuOutputBoundary openMenuPresenter;

    // Dependency Injection via constructor
    public OpenInGameMenuInteractor(OpenInGameMenuOutputBoundary openMenuPresenter){
        this.openMenuPresenter = openMenuPresenter;
    }

    @Override
    public void execute() {

    }
}
