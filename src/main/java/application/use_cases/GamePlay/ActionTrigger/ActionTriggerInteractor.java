package application.use_cases.GamePlay.ActionTrigger;

import application.game_registry.ItemRegistry;
import application.use_cases.Hint.GetHint.GetHintInputBoundary;
import application.use_cases.Hint.GetHint.GetHintInputData;
import application.use_cases.Interactable.Zoom.ZoomInputBoundary;
import application.use_cases.Interactable.Zoom.ZoomInputData;
import application.use_cases.Item.PickUp.PickUpInputBoundary;
import application.use_cases.Item.PickUp.PickUpInputData;
import domain.entities.Item.Item;

import java.util.Objects;

public class ActionTriggerInteractor implements ActionTriggerInputBoundary {

    private final ZoomInputBoundary zoomInteractor;
    private final ActionTriggerDataAccessInterface dataAccess;
    private final ActionTriggerGameDataAccessInterface gameDataAccess;
    private final PickUpInputBoundary pickUpInteractor;
    private final GetHintInputBoundary getHintInteractor;


    public ActionTriggerInteractor(ZoomInputBoundary zoomInteractor,
                                   PickUpInputBoundary pickUpInteractor,
                                   GetHintInputBoundary getHintInteractor,
                                   ActionTriggerDataAccessInterface dataAccess,
                                   ActionTriggerGameDataAccessInterface gameDataAccess) {
        this.zoomInteractor = zoomInteractor;
        this.dataAccess = dataAccess;
        this.pickUpInteractor = pickUpInteractor;
        this.getHintInteractor = getHintInteractor;
        this.gameDataAccess = gameDataAccess;
    }
    @Override
    public void execute(ActionTriggerInputData inputData) {

        String userId = dataAccess.getCurrentUser().getUsername();

        // System.out.println("=== ACTION TRIGGER ===");
        // System.out.println("1. Clicked ID: " + inputData.id);
        // System.out.println("2. Mode String: " + inputData.mode);

        if(Objects.equals(inputData.type, "Interactable")) {
            ZoomInputData interactableInputDate = new ZoomInputData(inputData.id);
            zoomInteractor.zoomIn(interactableInputDate);
        }
        else if ("item".equalsIgnoreCase(inputData.type)) {

            Item itemToPickUp = gameDataAccess.getItemById(inputData.id);
            // System.out.println("3. Item Found in DAO: " + itemToPickUp);

            if (itemToPickUp != null) {
                PickUpInputData itemInputData = new PickUpInputData(itemToPickUp);
                pickUpInteractor.execute(itemInputData);
            }
        }
        else if (Objects.equals(inputData.type, "Hint")) {

            GetHintInputData hintInputData = new GetHintInputData(inputData.id, userId);
            getHintInteractor.execute(hintInputData);
        }
    }
}