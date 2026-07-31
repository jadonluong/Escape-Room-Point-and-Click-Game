package data_access;

import domain.entities.Puzzle.AnagramPuzzle;
import domain.entities.Puzzle.CryptogramPuzzle;
import domain.entities.Puzzle.PuzzleFactory;
import infrastructure.AnagramApiClient;
import infrastructure.CryptogramApiClient;

import java.io.IOException;
import java.util.List;
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

    public AnagramPuzzle generateAnagramPuzzle(String id, String successMessage, String rewardItemId,
                                               String unlockedRoomId) throws IOException {
        AnagramApiClient.AnagramApiResponse anagramApiResponse = anagramApi.generateAnagram();

        String scrambled = anagramApiResponse.getScrambled();
        List<String> answers = anagramApiResponse.getAnswers();

        return puzzleFactory.createAnagram(id, scrambled, answers, successMessage, rewardItemId, unlockedRoomId);
    }

    public CryptogramPuzzle generateCryptogramPuzzle(String id, String cipherKeyId, String successMessage,
                                                     String rewardItemId, String unlockedRoomId) throws IOException {
        CryptogramApiClient.CryptogramApiResponse cryptogramApiResponse = cryptogramApi.generateCryptogram();

        String encrypted = cryptogramApiResponse.getEncrypted();
        String answer = cryptogramApiResponse.getAnswer();
        Map<String, String> cipher = cryptogramApiResponse.getCipher();

        return puzzleFactory.createCryptogram(id, encrypted, answer, cipher, cipherKeyId, successMessage,
                rewardItemId, unlockedRoomId);
    }
}
