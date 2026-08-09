package application.use_cases.user.signup;

/**
 * The interface for profanity checks in the usernames of a user.
 */
public interface ProfanityCheck {

    /**
     * Checks if the username contains any swear words.
     * @param username the username to be checked
     * @return true if the username has taboo words, false otherwise
     */
    boolean hasProfanity(String username);
}
