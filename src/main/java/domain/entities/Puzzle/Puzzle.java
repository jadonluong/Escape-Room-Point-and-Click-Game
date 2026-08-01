package domain.entities.Puzzle;

public interface Puzzle {
    String getId();
    boolean isSolved();
    void setSolved(boolean solved);
    String getDescription();
    String getSuccessMessage();
    String getRewardItemId();
    String getUnlockedRoomId();
    boolean solve(String playerAnswer);
}
