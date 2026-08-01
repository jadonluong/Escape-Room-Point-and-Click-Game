package application.use_cases.Crafting;

import application.use_cases.Interactable.Interact.InteractDataAccessInterface;
import application.use_cases.Crafting.CraftingInputData;
import application.use_cases.Crafting.CraftingOutputData;
import application.use_cases.Crafting.CraftingInputBoundary;
import application.use_cases.Crafting.CraftingOutputBoundary;
import data_access.JsonUserDataAccessObject;
import domain.entities.Item.Item;
import domain.entities.User.User;

public class CraftingInteractor implements CraftingInputBoundary{
    private InteractDataAccessInterface interactDataAccess;
    private final CraftingOutputBoundary presenter;

    public CraftingInteractor(JsonUserDataAccessObject userDAO, CraftingOutputBoundary presenter){
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(CraftingInputData inputData) {
        if (inputData == null || inputData.getItem() == null) {
            presenter.prepareFailView("Crafting failed: Invalid item.");
            return;
        }

        Item item = inputData.getItem();
        JsonUserDataAccessObject userDAO;
        User user = userDAO.getCurrentUser();

        if (user == null) {
            presenter.prepareFailView("Crafting failed: User not logged in.");
            return;
        }

        // Add item to user entity and persist state
        user.saveItem(item);
        userDAO.saveUser(user);

        // Notify presenter of success
        CraftingOutputData outputData = new CraftingOutputData(item.getName());
        presenter.prepareSuccessView(outputData);
    }
}
