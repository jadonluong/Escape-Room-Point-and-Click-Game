package application.use_cases.Item.PickUp;

import data_access.JsonUserDataAccessObject;
import domain.entities.Item.Item;
import domain.entities.User.User;

public class PickUpInteractor implements PickUpInputBoundary{
    private final JsonUserDataAccessObject userDAO;
    private final PickUpOutputBoundary presenter;

    // 1. Accept userDAO and presenter in the constructor
    public PickUpInteractor(JsonUserDataAccessObject userDAO, PickUpOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(PickUpInputData inputData) {
        Item item = inputData.getItem();

        if (item == null) {
            return;
        }

        String currentUsername = userDAO.getCurrentUsername();
        if (currentUsername != null) {
            User user = userDAO.getUser(currentUsername);
            if (user != null) {
                user.saveItem(item); // Adds item to dynamic ArrayList
            }
        }

        PickUpOutputData outputData = new PickUpOutputData(item.getName());
        presenter.prepareSuccessView(outputData);
    }
}
