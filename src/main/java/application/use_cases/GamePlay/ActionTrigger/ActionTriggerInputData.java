package application.use_cases.GamePlay.ActionTrigger;

public class ActionTriggerInputData {
    final String id;
    final String mode;

    public ActionTriggerInputData(String id, String mode) {
        this.id = id;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }
    public String getMode() {
        return mode;
    }
}
