package application.use_cases.Puzzle.EnterExit;

import domain.entities.User.User;

public class EnterExitInputData {
    private User user;
    private String puzzleId;

    public EnterExitInputData(User user, String puzzleId) {
        this.user = user;
        this.puzzleId = puzzleId;
    }

    public User getUser() {
        return user;
    }

    public String getPuzzleId() {
        return puzzleId;
    }
}
