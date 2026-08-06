package application.use_cases.Item.PickUp;

import domain.entities.Item.Item;
import domain.entities.User.User;

public class PickUpInteractor implements PickUpInputBoundary {
    private final PickUpUserDataAccessInterface userSession;
    private final PickUpOutputBoundary presenter;

    public PickUpInteractor(PickUpUserDataAccessInterface userSession, PickUpOutputBoundary presenter) {
        this.userSession = userSession;
        this.presenter = presenter;
    }

    @Override
    public void execute(PickUpInputData inputData) {
        String itemId = inputData.getItemId();

        // Fetches the Item using the userSession data interface
        Item item = userSession.getItemById(itemId);

        if (item == null) {
            return;
        }

        User user = userSession.getCurrentUser();
        if (user != null) {
            user.saveItem(item);
        }

        PickUpOutputData outputData = new PickUpOutputData(item);
        presenter.prepareSuccessView(outputData);
    }
}