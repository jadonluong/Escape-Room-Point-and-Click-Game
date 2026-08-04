package application.use_cases.Puzzle.EnterExit;

import java.util.Map;

public class EnterExitOutputData {
    private String puzzleId;
    private String puzzleType; // "Anagram", "Cryptogram", "CodeLock", etc.
    private String description;
    private String hint;

    private String scrambled;

    private String encrypted;
    private Map<String, String> cipher;

    private String interactableId;

    // AnagramPuzzle
    public EnterExitOutputData(String puzzleId, String puzzleType, String description, String hint, String scrambled,
                               String interactableId) {
        this.puzzleId = puzzleId;
        this.puzzleType = puzzleType;
        this.description = description;
        this.hint = hint;
        this.scrambled = scrambled;
        this.encrypted = null;
        this.cipher = null;
        this.interactableId = interactableId;
    }

    // CryptogramPuzzle
    public EnterExitOutputData(String puzzleId, String puzzleType, String description, String encrypted,
                               Map<String, String> cipher, String interactableId) {
        this.puzzleId = puzzleId;
        this.puzzleType = puzzleType;
        this.description = description;
        this.hint = null;
        this.scrambled = null;
        this.encrypted = encrypted;
        this.cipher = cipher;
        this.interactableId = interactableId;
    }

    // CodeLockPuzzle
    public EnterExitOutputData(String puzzleId, String puzzleType, String description, String hint,
                               String interactableId) {
        this.puzzleId = puzzleId;
        this.puzzleType = puzzleType;
        this.description = description;
        this.hint = hint;
        this.scrambled = null;
        this.encrypted = null;
        this.cipher = null;
        this.interactableId = interactableId;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPuzzleType() {
        return puzzleType;
    }

    public String getDescription() {
        return description;
    }

    public String getHint() {
        return hint;
    }

    public String getScrambled() {
        return scrambled;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public Map<String, String> getCipher() {
        return cipher;
    }

    public String getInteractableId() {
        return interactableId;
    }
}
