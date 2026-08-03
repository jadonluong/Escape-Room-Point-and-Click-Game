package application.use_cases.User;

import application.use_cases.GamePlay.ActionTrigger.ActionTriggerDataAccessInterface;
import application.use_cases.GamePlay.UserDataAccessInterface;
import application.use_cases.Hint.GetHint.GetHintUserDataAccessInterface;
import application.use_cases.Interactable.Interact.InteractUserDataAccessInterface;
import application.use_cases.Puzzle.EnterExit.EnterExitUserDataAccessInterface;
import application.use_cases.Puzzle.Solve.SolveUserDataAccessInterface;
import application.use_cases.User.Login.LoginUserSessionDataAccessInterface;
import application.use_cases.User.Logout.LogoutUserDataAccessInterface;
import application.use_cases.User.SaveProgress.SaveProgressUserSessionDataAccessInterface;
import domain.entities.User.User;

public class LiveUserSessionTracking implements GetHintUserDataAccessInterface,
        LoginUserSessionDataAccessInterface,
        LogoutUserDataAccessInterface,
        SaveProgressUserSessionDataAccessInterface,
        UserDataAccessInterface,
        ActionTriggerDataAccessInterface,
        InteractUserDataAccessInterface,
        EnterExitUserDataAccessInterface,
        SolveUserDataAccessInterface {

    private User currentUser;

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
