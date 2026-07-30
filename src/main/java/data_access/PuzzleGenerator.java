package data_access;

import domain.entities.Puzzle.AnagramPuzzle;
import domain.entities.Puzzle.CryptogramPuzzle;
import domain.entities.Puzzle.PuzzleFactory;
import infrastructure.AnagramApiClient;
import infrastructure.CryptogramApiClient;

import java.util.Map;

public class PuzzleGenerator {
    private final AnagramApiClient anagramApi;
    private final CryptogramApiClient cryptogramApi;
    private final PuzzleFactory puzzleFactory;

    public PuzzleGenerator(AnagramApiClient anagramApi, CryptogramApiClient cryptogramApi,
                           PuzzleFactory puzzleFactory) {
        this.anagramApi = anagramApi;
        this.cryptogramApi = cryptogramApi;
        this.puzzleFactory = puzzleFactory;
    }

    /*
    public AnagramPuzzle generateAnagramPuzzle(String id, String successMessage, String rewardItemId,
                                               String unlockedRoomId) {
        // TODO: Call AnagramApiClient

        String scrambled = anagramApi.getScrambled();
        String answer = anagramApi.getAnswer();
        String hint = anagramApi.getHint();

        return puzzleFactory.createAnagram(id, scrambled, answer, hint, successMessage, rewardItemId,
                unlockedRoomId);
    }

    public CryptogramPuzzle generateCryptogramPuzzle(String id, String cipherKeyId, String successMessage,
                                                     String rewardItemId, String unlockedRoomId) {
        // TODO: Call CryptogramApiClient

        String encrypted = cryptogramApi.getEncrypted();
        String answer = cryptogramApi.getAnswer();
        Map<String, String> cipher = cryptogramApi.getCipher();

        return puzzleFactory.createCryptogram(id, encrypted, answer, cipher, cipherKeyId, successMessage,
                rewardItemId, unlockedRoomId);
    }
    */
}
