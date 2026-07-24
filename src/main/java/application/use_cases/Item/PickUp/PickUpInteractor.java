package application.use_cases.Item.PickUp;

import domain.entities.Item.Item;
import domain.entities.User.User;

public class PickUpInteractor implements PickUpInputBoundary{
    private final PickUpOutputBoundary presenter;
    private final User user;

    public PickUpInteractor(PickUpOutputBoundary presenter, User user) {
        this.presenter = presenter;
        this.user = user;
    }

    @Override
    public void execute(PickUpInputData inputData) {
        Item item = inputData.getItem();

        // 1. Check if item is pickable — silently return if false or null
        if (item == null || Boolean.FALSE.equals(item.getPickable())) {
            return; // Do nothing
        }

        // 2. Save item directly to the user's inventory (ArrayList)
        user.saveItem(item);

        // 3. Notify presenter across the Output Boundary to refresh UI
        PickUpOutputData outputData = new PickUpOutputData(item.getName());
        presenter.prepareSuccessView(outputData);
    }
}
