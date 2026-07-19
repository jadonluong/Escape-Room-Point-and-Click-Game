package domain.entities.Puzzle;

public interface Puzzle {
    String getId();
    boolean isSolved();
    void setSolved(boolean solved);
    String getAnswer();
    String getHint();
    String getRewardItemId();
    String getUnlockedRoomId();
    boolean solve(String playerAnswer);
}
