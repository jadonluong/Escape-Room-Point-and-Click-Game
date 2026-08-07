package interface_adapter.item;

import application.use_cases.Item.PickUp.PickUpInputBoundary;
import application.use_cases.Item.PickUp.PickUpInputData;

public class PickUpController {
    private final PickUpInputBoundary pickUpInteractor;
    public PickUpController(PickUpInputBoundary pickUpInteractor) {
        this.pickUpInteractor = pickUpInteractor;
    }

    public void execute(String item){
        PickUpInputData inputData = new PickUpInputData(item);
        pickUpInteractor.execute(inputData);
    }
}
