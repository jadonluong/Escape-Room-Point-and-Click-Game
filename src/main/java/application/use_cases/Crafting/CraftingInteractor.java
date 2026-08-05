package application.use_cases.Crafting;

import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.User.User;

public class CraftingInteractor implements CraftingInputBoundary {
    private final CraftingOutputBoundary presenter;
    private final User user; // Or LiveUserSessionTracking.getActiveUser()
    private final ItemFactory itemFactory;

    public CraftingInteractor(CraftingOutputBoundary presenter, User user, ItemFactory itemFactory) {
        this.presenter = presenter;
        this.user = user;
        this.itemFactory = itemFactory;
    }

    @Override
    public void execute(CraftingInputData inputData) {
        // 1. Extract itemA and itemB from inputData
        Item itemA = inputData.getItemA();
        Item itemB = inputData.getItemB();

        if (itemA == null || itemB == null) {
            presenter.prepareFailView("Select two valid items to craft!");
            return;
        }

        // 2. Check recipe match using item names
        String craftedName = checkRecipe(itemA.getName(), itemB.getName());
        if (craftedName == null) {
            presenter.prepareFailView("These items cannot be combined.");
            return;
        }

        // 3. Remove raw ingredient Item objects from user inventory
        user.removeItem(itemA);
        user.removeItem(itemB);

        // 4. Create new Item entity matching ItemFactory's signature
        Item newItem = itemFactory.createItem(
                craftedName,
                "A crafted item made by combining ingredients.",
                true,
                "crafted_" + System.currentTimeMillis()
        );

        // 5. Save the new Item directly to user inventory
        user.saveItem(newItem);

        // 6. Format as "itemId:itemName" for the presenter layer
        String formattedNewItem = newItem.getId() + ":" + newItem.getName();
        CraftingOutputData outputData = new CraftingOutputData(formattedNewItem, true);
        presenter.prepareSuccessView(outputData);
    }

    private String extractName(String rawItem) {
        if (rawItem.contains(":")) {
            return rawItem.split(":")[1]; // Takes the "itemName" portion
        }
        return rawItem;
    }

    private String checkRecipe(String nameA, String nameB) {
        if ((nameA.equalsIgnoreCase("stick") && nameB.equalsIgnoreCase("rock")) ||
                (nameA.equalsIgnoreCase("rock") && nameB.equalsIgnoreCase("stick"))) {
            return "hammer";
        }
        return null;
    }
}