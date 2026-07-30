package application.use_cases.User.SaveProgress;

import domain.entities.User.User;

/**
 * The Input Data for the Save Progress Use Case.
 */
public class SaveProgressInputData {
    private String username;

    public SaveProgressInputData(String username){
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
