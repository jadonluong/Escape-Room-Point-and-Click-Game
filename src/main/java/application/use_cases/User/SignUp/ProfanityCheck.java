package application.use_cases.User.SignUp;

/**
 * The interface for profanity checks in the usernames of a User.
 */
public interface ProfanityCheck {

    /**
     * Checks if the username contains any swear words.
     * @param username the username to be checked
     * @return true if the username has taboo words, false otherwise
     */
    boolean hasProfanity(String username);
}
