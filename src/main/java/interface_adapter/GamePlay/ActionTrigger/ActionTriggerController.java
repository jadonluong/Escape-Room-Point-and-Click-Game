package interface_adapter.GamePlay.ActionTrigger;

import application.use_cases.game_play.action_trigger.ActionTriggerInputBoundary;
import application.use_cases.game_play.action_trigger.ActionTriggerInputData;

public class ActionTriggerController {

    private final ActionTriggerInputBoundary interactor;

    public ActionTriggerController(ActionTriggerInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String id, String type) {

        ActionTriggerInputData inputData = new ActionTriggerInputData(id, type);

        interactor.execute(inputData);
    }
}
