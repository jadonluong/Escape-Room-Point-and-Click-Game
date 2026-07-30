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
        Item item = inputData.getItem();

        if (item == null) {
            return;
        }

        User user = userDAO.getCurrentUser();
        if (user != null) {
            user.saveItem(item); // Adds item to dynamic ArrayList
        }

        CraftingOutputData outputData = new CraftingOutputData(item.getName());
        presenter.prepareSuccessView(outputData);
    }
}
