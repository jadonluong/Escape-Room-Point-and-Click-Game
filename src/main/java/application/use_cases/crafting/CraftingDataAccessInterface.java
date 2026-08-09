package application.use_cases.crafting;

import java.util.Map;
import java.util.Set;

public interface CraftingDataAccessInterface {

    Map<Set<String>, String> getCraftingRecipes();
}
