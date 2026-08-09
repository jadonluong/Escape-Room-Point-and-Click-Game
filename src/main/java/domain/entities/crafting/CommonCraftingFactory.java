package domain.entities.crafting;

public class CommonCraftingFactory implements CraftingFactory {
    @Override
    public Crafting createRecipe(String ingredientId1, String ingredientId2,
                                 String resultName, String resultDescription, Boolean resultCraftable) {
        return new CommonCrafting(ingredientId1, ingredientId2, resultName, resultDescription, resultCraftable);
    }
}
