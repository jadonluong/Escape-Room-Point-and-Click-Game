/*
package application.use_cases.game_play.action_trigger;

import application.use_cases.Hint.GetHint.GetHintInputBoundary;
import application.use_cases.Hint.GetHint.GetHintInputData;
import application.use_cases.Interactable.Zoom.ZoomInputBoundary;
import application.use_cases.Interactable.Zoom.ZoomInputData;
import application.use_cases.Item.PickUp.PickUpInputBoundary;
import application.use_cases.Item.PickUp.PickUpInputData;
import domain.entities.Item.Item;


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

        final String userId = dataAccess.getCurrentUser().getUsername();

        // System.out.println("=== ACTION TRIGGER ===");
        // System.out.println("1. Clicked ID: " + inputData.id);
        // System.out.println("2. Mode String: " + inputData.mode);

        if (inputData.type.equals("Interactable")) {
            final ZoomInputData interactableInputDate = new ZoomInputData(inputData.getId());
            zoomInteractor.zoomIn(interactableInputDate);
        }
        else if ("item".equalsIgnoreCase(inputData.type)) {

            final Item itemToPickUp = gameDataAccess.getItemById(inputData.getId());
            // System.out.println("3. Item Found in DAO: " + itemToPickUp);

            if (itemToPickUp != null) {
                final PickUpInputData itemInputData = new PickUpInputData(itemToPickUp);
                pickUpInteractor.execute(itemInputData);
            }
        }
        else if (inputData.type.equals("Hint")) {

            final GetHintInputData hintInputData = new GetHintInputData(inputData.getId(), userId);
            getHintInteractor.execute(hintInputData);
        }
    }
}
 */
