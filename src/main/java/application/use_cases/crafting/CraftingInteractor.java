package application.use_cases.crafting;

import application.game_registry.ItemRegistry;
import domain.entities.item.Item;
// import domain.entities.item.ItemFactory;
import domain.entities.user.User;

import java.util.Map;
import java.util.Set;

public class CraftingInteractor implements CraftingInputBoundary {
    private final CraftingOutputBoundary presenter;
    private final User user;
    // private final ItemFactory itemFactory;
    private final ItemRegistry itemRegistry; // Injected to handle recipe domain lookups
    private final CraftingDataAccessInterface craftingDataAccess;

    public CraftingInteractor(CraftingOutputBoundary presenter,
                              User user,
                              // ItemFactory itemFactory,
                              ItemRegistry itemRegistry,
                              CraftingDataAccessInterface craftingDataAccess)
    {
        this.presenter = presenter;
        this.user = user;
        // this.itemFactory = itemFactory;
        this.itemRegistry = itemRegistry;
        this.craftingDataAccess = craftingDataAccess;
    }

    @Override
    public void execute(CraftingInputData inputData) {
        // 1. Resolve Item entities from registry (supports both raw IDs and Item objects)
        Item itemA = inputData.getItemA() != null
                ? inputData.getItemA()
                : (inputData.getItemAId() != null ? itemRegistry.getItemById(inputData.getItemAId()) : null);

        Item itemB = inputData.getItemB() != null
                ? inputData.getItemB()
                : (inputData.getItemBId() != null ? itemRegistry.getItemById(inputData.getItemBId()) : null);

        if (itemA == null || itemB == null) {
            presenter.prepareFailView("Select two valid items to craft!");
            return;
        }

        // 2. Fetch active recipe map from data access
        String craftedItemId = getItemId(itemA, itemB);

        if (craftedItemId == null) {
            presenter.prepareFailView("These items cannot be combined.");
            return;
        }

        // 4. Fetch authentic Item entity from ItemRegistry using the DB ID
        Item newItem = itemRegistry.getItemById(craftedItemId);
        if (newItem == null) {
            presenter.prepareFailView("Crafted item not found in registry.");
            return;
        }

        // 5. Update user inventory
        user.removeItem(itemA);
        user.removeItem(itemB);
        user.saveItem(newItem);

        // 6. Notify presenter
        String formattedNewItem = newItem.getId() + ":" + newItem.getName();
        CraftingOutputData outputData = new CraftingOutputData(formattedNewItem, true);
        presenter.prepareSuccessView(outputData);
    }

    private String getItemId(Item itemA, Item itemB) {
        Map<Set<String>, String> recipes = craftingDataAccess.getCraftingRecipes();

        // 3. Query recipe match using ingredient IDs
        Set<String> ingredientIds = Set.of(itemA.getId(), itemB.getId());
        String craftedItemId = recipes.get(ingredientIds);

        // Fallback lookup using ingredient names
        if (craftedItemId == null) {
            Set<String> ingredientNames = Set.of(itemA.getName().toLowerCase(), itemB.getName().toLowerCase());
            craftedItemId = recipes.get(ingredientNames);
        }
        return craftedItemId;
    }
}