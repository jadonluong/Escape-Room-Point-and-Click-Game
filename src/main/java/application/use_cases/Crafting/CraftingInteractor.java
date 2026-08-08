package application.use_cases.Crafting;

import application.game_registry.ItemRegistry;
import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.User.User;

public class CraftingInteractor implements CraftingInputBoundary {
    private final CraftingOutputBoundary presenter;
    private final User user;
    private final ItemFactory itemFactory;
    private final ItemRegistry itemRegistry; // Injected to handle recipe domain lookups

    public CraftingInteractor(CraftingOutputBoundary presenter, User user,
                              ItemFactory itemFactory, ItemRegistry itemRegistry) {
        this.presenter = presenter;
        this.user = user;
        this.itemFactory = itemFactory;
        this.itemRegistry = itemRegistry;
    }

    @Override
    public void execute(CraftingInputData inputData) {
        Item itemA = inputData.getItemA();
        Item itemB = inputData.getItemB();

        if (itemA == null || itemB == null) {
            presenter.prepareFailView("Select two valid items to craft!");
            return;
        }

        // 1. Delegate recipe evaluation to ItemRegistry
        String craftedName = itemRegistry.getRecipeResult(itemA.getName(), itemB.getName());
        if (craftedName == null) {
            presenter.prepareFailView("These items cannot be combined.");
            return;
        }

        // 2. Remove raw materials from inventory
        user.removeItem(itemA);
        user.removeItem(itemB);

        // 3. Instantiate crafted Item entity via ItemFactory
        Item newItem = itemFactory.createItem(
                craftedName,
                "A crafted item made by combining ingredients.",
                true,
                "crafted_" + System.currentTimeMillis()
        );

        // 4. Save new item to user inventory
        user.saveItem(newItem);

        // 5. Notify presenter with formatted payload
        String formattedNewItem = newItem.getId() + ":" + newItem.getName();
        CraftingOutputData outputData = new CraftingOutputData(formattedNewItem, true);
        presenter.prepareSuccessView(outputData);
    }
}