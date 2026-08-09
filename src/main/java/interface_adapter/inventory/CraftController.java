package interface_adapter.inventory;

import application.use_cases.crafting.CraftingInputBoundary;
import application.use_cases.crafting.CraftingInputData;

public class CraftController {

    private final CraftingInputBoundary craftingInteractor;

    public CraftController(CraftingInputBoundary craftingInteractor) {
        this.craftingInteractor = craftingInteractor;
    }

    public void execute(String itemA, String itemB) {
        CraftingInputData inputData = new CraftingInputData(itemA, itemB);
        craftingInteractor.execute(inputData);
    }
}