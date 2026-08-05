package application.use_cases.GamePlay.ActionTrigger;

public class ActionTriggerInputData {
    final String id;
    final String type;

    public ActionTriggerInputData(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
}
