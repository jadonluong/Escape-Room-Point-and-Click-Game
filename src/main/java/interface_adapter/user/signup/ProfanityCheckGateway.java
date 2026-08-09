package interface_adapter.user.signup;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import application.use_cases.user.signup.ProfanityCheck;

public class ProfanityCheckGateway implements ProfanityCheck {
    private static final String API_URL = "https://vector.profanity.dev";
    private final HttpClient httpClient;
    private final int timeoutLength = 3;
    private final int statusCode = 200;

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
            final String jsonRequestBody = String.format("{\"message\":\"%s\"}", escapeJson(username));

            // 2. Formulate the HTTP POST request with a strict 3-second timeout guard
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .timeout(Duration.ofSeconds(timeoutLength))
                    .build();

            // 3. Send the request and receive the response as a string
            final HttpResponse<String> response = this.httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            // 4. Parse the response body if the server returns 200 OK
            if (response.statusCode() == statusCode) {
                final String body = response.body();
                // profanity.dev returns {"isProfanity": true/false, ...}
                // A quick string check keeps this implementation clean of JSON library dependencies
                return body.contains("\"isProfanity\":true");
            }
            // Fallback safety: Log or handle unexpected status codes (e.g., 500, 429)
            System.err.println("Profanity API returned unexpected status code: " + response.statusCode());
            return false;

        }
        catch (Exception exception) {
            // Fail open: log the error and allow the user to sign up if the network drops
            // This prevents the entire system's registration from breaking if profanity.dev goes down.
            System.err.println("Profanity check network failure: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Helper to escape quotes and backslashes for a raw JSON string block.
     * @param input the string input
     * @return the string output that helps JSON parsing
     */
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"");
        // Must escape backslashes first then escape double quotes
    }
}
