package interface_adapter.User.Signup;

import application.use_cases.User.SignUp.ProfanityCheck;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ProfanityCheckGateway implements ProfanityCheck {
    private final HttpClient httpClient;
    private static final String API_URL = "https://vector.profanity.dev";

    public ProfanityCheckGateway(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public boolean hasProfanity(String username) {
        if (username == null || username.isBlank()) {
            return false;
        }

        try {
            // 1. Build the JSON request body matching profanity.dev schema
            // Escaping quotes keeps it robust if usernames contain special characters
            String jsonRequestBody = String.format("{\"message\":\"%s\"}", escapeJson(username));

            // 2. Formulate the HTTP POST request with a strict 3-second timeout guard
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .timeout(Duration.ofSeconds(3))
                    .build();

            // 3. Send the request and receive the response as a string
            HttpResponse<String> response = this.httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            // 4. Parse the response body if the server returns 200 OK
            if (response.statusCode() == 200) {
                String body = response.body();
                // profanity.dev returns {"isProfane": true/false, ...}
                // A quick string check keeps this implementation clean of JSON library dependencies
                return body.contains("\"isProfane\":true");
            }
            // Fallback safety: Log or handle unexpected status codes (e.g., 500, 429)
            System.err.println("Profanity API returned unexpected status code: " + response.statusCode());
            return false;

        } catch (Exception e) {
            // Fail open: log the error and allow the user to sign up if the network drops
            // This prevents the entire system's registration from breaking if profanity.dev goes down.
            System.err.println("Profanity check network failure: " + e.getMessage());
            return false;
        }
    }

    /**
     * Helper to escape quotes and backslashes for a raw JSON string block.
     */
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\") // Must escape backslashes first
                .replace("\"", "\\\""); // Escape double quotes
    }
}
