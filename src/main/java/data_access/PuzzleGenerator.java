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

    public AnagramPuzzle generateAnagramPuzzle(String id, String answer, String hint, String successMessage,
                                               String rewardItemId, String unlockedRoomId) throws IOException {
        final AnagramPuzzle result;

        if (answer == null) {
            result = null;
        }
        else {
            final AnagramApiClient.AnagramApiResponse anagramApiResponse = anagramApi.generateAnagram(answer);

            final String scrambled = anagramApiResponse.getScrambled();

            result = puzzleFactory.createAnagram(id, scrambled, answer, hint, successMessage, rewardItemId,
                    unlockedRoomId);
        }

        return result;
    }

    public CryptogramPuzzle generateCryptogramPuzzle(String id, String answer, String cipherKeyId,
                                                     String successMessage, String rewardItemId,
                                                     String unlockedRoomId) throws IOException {
        final CryptogramApiClient.CryptogramApiResponse cryptogramApiResponse = cryptogramApi
                .generateCryptogram(answer);

        final String encrypted = cryptogramApiResponse.getEncrypted();
        final Map<String, String> cipher = cryptogramApiResponse.getCipher();

        return puzzleFactory.createCryptogram(id, encrypted, answer, cipher, cipherKeyId, successMessage,
                rewardItemId, unlockedRoomId);
    }
}
