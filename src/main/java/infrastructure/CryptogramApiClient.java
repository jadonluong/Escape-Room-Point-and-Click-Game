package infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
}
