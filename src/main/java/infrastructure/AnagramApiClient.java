package infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnagramApiClient {
    private static final String API_URL = "https://api.apiverve.com/v1/anagrampuzzle?difficulty=medium";
    private final String apiKey;
    private final OkHttpClient client;
    private final Gson gson;

    public AnagramApiClient(String apiKey) {
        if (apiKey == null) {
            throw new IllegalArgumentException("API key cannot be null");
        }
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.gson = new GsonBuilder().create();
    }

    public AnagramApiResponse generateAnagram() throws IOException {
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("x-api-key", apiKey)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code());
            }

            if (response.body() == null) {
                throw new IOException("Response body is empty");
            }

            String json = response.body().string();
            ApiResponse apiResponse = gson.fromJson(json, ApiResponse.class);

            if (apiResponse == null || apiResponse.data == null) {
                throw new IOException("Invalid API response");
            }

            if (!"ok".equals(apiResponse.status)) {
                throw new IOException("API error: " + apiResponse.error);
            }

            PuzzleData puzzle = apiResponse.data.puzzles.get(0); // Only need one puzzle!

            if (puzzle.anagrams == null) {
                puzzle.anagrams = new ArrayList<>();
            }
            puzzle.anagrams.add(puzzle.original);

            return new AnagramApiResponse(
                    puzzle.scrambled,
                    puzzle.anagrams
            );
        }
    }

    private static class ApiResponse {
        String status;
        String error;
        Data data;
    }

    private static class Data {
        List<PuzzleData> puzzles;
    }

    private static class PuzzleData {
        String original;
        String scrambled;
        List<String> anagrams;
    }

    public static class AnagramApiResponse {
        private final String scrambled;
        private final List<String> answers;

        public AnagramApiResponse(String scrambled, List<String> answers) {
            this.scrambled = scrambled;
            this.answers = answers;
        }

        public String getScrambled() { return scrambled; }
        public List<String> getAnswers() { return answers; }
    }
}