package interface_adapter.Puzzle.EnterExit;

import java.util.Map;

public class EnterExitState {
    private String puzzleId;
    private String puzzleType;
    private String description;
    private String hint;

    private String scrambled;

    private String encrypted;
    private Map<String, String> cipher;

    public String getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(String puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPuzzleType() {
        return puzzleType;
    }

    public void setPuzzleType(String puzzleType) {
        this.puzzleType = puzzleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getScrambled() {
        return scrambled;
    }

    public void setScrambled(String scrambled) {
        this.scrambled = scrambled;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public Map<String, String> getCipher() {
        return cipher;
    }

    public void setCipher(Map<String, String> cipher) {
        this.cipher = cipher;
    }
}
