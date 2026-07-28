package application.use_cases.Puzzle.Solve;

import domain.entities.User.User;

public class SolveInputData {
    private User user;
    private String puzzleId;
    private String playerAnswer;

    public SolveInputData(User user, String puzzleId, String playerAnswer) {
        this.user = user;
        this.puzzleId = puzzleId;
        this.playerAnswer = playerAnswer;
    }

    public User getUser() {
        return user;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }
}
