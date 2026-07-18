package domain.entities.Crafting;

public interface CraftingFactory {
    Crafting createRecipe(String ingredientId1, String ingredientId2,
                          String resultName, String resultDescription, Boolean resultCraftable);
}
