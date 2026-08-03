package application.use_cases.Item.PickUp;

import domain.entities.Item.Item;
import domain.entities.User.User;

public class PickUpInteractor implements PickUpInputBoundary {
    private final PickUpUserDataAccessInterface userSession; // 👈 Updated type
    private final PickUpOutputBoundary presenter;

    public PickUpInteractor(PickUpUserDataAccessInterface userSession, PickUpOutputBoundary presenter) {
        this.userSession = userSession;
        this.presenter = presenter;
    }

    @Override
    public void execute(PickUpInputData inputData) {
        Item item = inputData.getItem();

        if (item == null) {
            return;
        }

        User user = userSession.getCurrentUser(); // 👈 Calls userSession now
        if (user != null) {
            user.saveItem(item);
        }

        PickUpOutputData outputData = new PickUpOutputData(item);
        presenter.prepareSuccessView(outputData);
    }
}
