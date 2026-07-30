package interface_adapter.GamePlay.ActionTrigger;

import application.use_cases.GamePlay.ActionTrigger.ActionTriggerInputBoundary;
import application.use_cases.GamePlay.ActionTrigger.ActionTriggerInputData;
import application.use_cases.GamePlay.ActionTrigger.ActionTriggerInteractor;

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
