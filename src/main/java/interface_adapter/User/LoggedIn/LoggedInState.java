package interface_adapter.User.LoggedIn;

public class LoggedInState {
    private String username = "";
    private boolean registered = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isLoggedIn() {
        return username != null && !username.isEmpty();
    }
}
