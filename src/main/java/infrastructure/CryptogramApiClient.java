package infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CryptogramApiClient {
    private static final String API_URL = "https://api.apiverve.com/v1/cryptogram?random=true";
    private final String apiKey;
    private final OkHttpClient client;
    private final Gson gson;

    public CryptogramApiClient(String apiKey) {
        if (apiKey == null) {
            throw new IllegalArgumentException("API key cannot be null");
        }
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.gson = new GsonBuilder().create();
    }

    public CryptogramApiResponse generateCryptogram() throws IOException {
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

            Map<String, String> cipher;
            if (apiResponse.data.cipher != null) {
                cipher = apiResponse.data.cipher;
            } else { // So we don't get a nullPointerException because of the free plan
                cipher = new HashMap<>();
            }

            return new CryptogramApiResponse(
                    apiResponse.data.encoded,
                    apiResponse.data.original,
                    cipher
            );
        }
    }

    private static class ApiResponse {
        String status;
        String error;
        Data data;
    }

    private static class Data {
        String encoded;
        String original;
        Map<String, String> cipher;
    }

    public static class CryptogramApiResponse {
        private final String encrypted;
        private final String answer;
        private final Map<String, String> cipher;

        public CryptogramApiResponse(String encrypted, String answer, Map<String, String> cipher) {
            this.encrypted = encrypted;
            this.answer = answer;
            this.cipher = cipher;
        }

        public String getEncrypted() { return encrypted; }
        public String getAnswer() { return answer; }
        public Map<String, String> getCipher() { return cipher; }
    }
}