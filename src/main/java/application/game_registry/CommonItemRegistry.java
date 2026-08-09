package application.game_registry;

import domain.entities.item.Item;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommonItemRegistry implements ItemRegistry {

    private final Map<String, Item> itemCatalog = new HashMap<>();
    private final Map<Set<String>, String> recipes = new HashMap<>();

    public CommonItemRegistry() {
        // Register default items into the catalog if needed
    }

    public void registerItem(Item item) {
        if (item != null) {
            itemCatalog.put(item.getId(), item);
        }
    }

    @Override
    public Item getItemById(String id) {
        return itemCatalog.get(id);
    }

    @Override
    public String getRecipeResult(String nameA, String nameB) {
        if (nameA == null || nameB == null) {
            return null;
        }
        Set<String> queryKey = Set.of(nameA.toLowerCase(), nameB.toLowerCase());
        return recipes.get(queryKey);
    }
}