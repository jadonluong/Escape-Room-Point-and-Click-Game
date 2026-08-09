package application.use_cases.user.save_progress;

import domain.entities.user.CommonUser;

public interface SaveProgressUserDataAccessInterface {

    /**
     * Saves the common user's progress.
     * @param user the Common user to be saved to database
     */
    void saveProgress(CommonUser user);
}
