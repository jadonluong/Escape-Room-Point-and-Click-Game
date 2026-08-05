package application.use_cases.User.SaveProgress;

import domain.entities.User.CommonUser;
import domain.entities.User.User;

public interface SaveProgressUserDataAccessInterface {

    /**
     * Saves the common user's progress.
     * @param user the Common user to be saved to database
     */
    void saveProgress(CommonUser user);
}
