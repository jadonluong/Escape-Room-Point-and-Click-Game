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

        // Ensure the item passed in isn't null
        if (item == null) {
            return;
        }

        // 2. All Items are pickable! Add directly to user's inventory
        user.saveItem(item);

        // 3. Notify presenter across the Output Boundary to refresh UI
        PickUpOutputData outputData = new PickUpOutputData(item.getName());
        presenter.prepareSuccessView(outputData);
    }
}
