package application.use_cases.GamePlay.ActionTrigger;

import application.use_cases.Hint.GetHint.GetHintInputBoundary;
import application.use_cases.Hint.GetHint.GetHintInputData;
import application.use_cases.Interactable.Interact.InteractDataAccessInterface;
import application.use_cases.Interactable.Zoom.ZoomInputBoundary;
import application.use_cases.Interactable.Zoom.ZoomInputData;
import application.use_cases.Item.PickUp.PickUpInputBoundary;
import application.use_cases.Item.PickUp.PickUpInputData;
import domain.entities.Item.Item;

import java.util.Objects;

public class ActionTriggerInteractor implements ActionTriggerInputBoundary {

    private ZoomInputBoundary zoomInteractor;
    private ActionTriggerDataAccessInterface dataAccess;
    private PickUpInputBoundary pickUpInteractor;
    private GetHintInputBoundary getHintInteractor;


    public ActionTriggerInteractor(ZoomInputBoundary zoomInteractor,
                                   PickUpInputBoundary pickUpInteractor,
                                   GetHintInputBoundary getHintInteractor,
                                   ActionTriggerDataAccessInterface dataAccess) {
        this.zoomInteractor = zoomInteractor;
        this.dataAccess = dataAccess;
        this.pickUpInteractor = pickUpInteractor;
        this.getHintInteractor = getHintInteractor;

    }
    @Override
    public void execute(ActionTriggerInputData inputData) {

        String userId = dataAccess.getCurrentUser().getUsername();
        if(Objects.equals(inputData.mode, "Interactable")) {
            ZoomInputData interactableInputDate = new ZoomInputData(inputData.id);
            zoomInteractor.zoomIn(interactableInputDate);
        }
        else if (Objects.equals(inputData.mode, "item") || "item".equalsIgnoreCase(inputData.mode)) {
            System.out.println("-> ACTION TRIGGERED FOR ITEM!");
            System.out.println("   ID Clicked: " + inputData.id);
            System.out.println("   Mode String: " + inputData.mode);

            Item itemToPickUp = dataAccess.getItemById(inputData.id);
            System.out.println("   Item Object Found: " + itemToPickUp);
            if (itemToPickUp != null) {
                System.out.println("   Calling PickUpInteractor...");
                PickUpInputData itemInputData = new PickUpInputData(itemToPickUp);
                pickUpInteractor.execute(itemInputData);
            } else {
                System.out.println("   ERROR: itemToPickUp is NULL!");
            }
        }
        else if ("Hint".equalsIgnoreCase(inputData.mode) || "hint".equalsIgnoreCase(inputData.mode)) {
            System.out.println("-> HINT TRIGGERED!");
            System.out.println("   Hint Object ID: " + inputData.id);
            System.out.println("   User ID: " + userId);

            GetHintInputData hintInputData = new GetHintInputData(inputData.id, userId);
            getHintInteractor.execute(hintInputData);
        }
    }
}