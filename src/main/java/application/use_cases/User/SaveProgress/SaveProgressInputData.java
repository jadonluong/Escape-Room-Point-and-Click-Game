package application.use_cases.User.SaveProgress;

import domain.entities.User.User;

/**
 * The Input Data for the Save Progress Use Case.
 */
public class SaveProgressInputData {
    private User user;

    public SaveProgressInputData(User user){
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
