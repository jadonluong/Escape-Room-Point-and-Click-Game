package application.use_cases.game_play.action_trigger;

public class ActionTriggerInputData {
    private final String id;
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
