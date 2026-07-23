package application.use_cases.GamePlay.Resume;

public class ResumeInteracter implements ResumeInputBoundary {
    private final ResumeOutputBoundary resumePresenter;

    public ResumeInteracter(ResumeOutputBoundary resumePresenter) {
        this.resumePresenter = resumePresenter;
    }

    @Override
    public void execute() {
        // 1. Trigger your unpause mechanism here (e.g., Engine.resume() or updating your game state)

        // 2. Pass control to the presenter to swap back to the active game UI
        resumePresenter.prepareResumeView();
    }
}
