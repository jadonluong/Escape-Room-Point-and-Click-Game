package application.use_cases.registries;

import application.game_registry.ItemRegistry;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRegistryTest {
    private TestItemRegistry testRegistry;
    private String validItemId1;
    private String validItemId2;
    private Item sampleItem1;
    private Item sampleItem2;

    // Test double implementation for ItemRegistry[cite: 2]
    private static class TestItemRegistry implements ItemRegistry {
        private final Map<String, Item> items = new HashMap<>();
        private final Map<String, String> recipes = new HashMap<>();

        public void addItem(String id, Item item) {
            items.put(id, item);
        }

        public void addRecipe(String itemA, String itemB, String result) {
            recipes.put(itemA + "+" + itemB, result);
            recipes.put(itemB + "+" + itemA, result);
        }

        @Override
        public Item getItemById(String id) {
            return items.get(id);
        }

        @Override
        public String getRecipeResult(String nameA, String nameB) {
            String result = recipes.get(nameA + "+" + nameB);
            if (result != null) {
                return result;
            }
            // Delegate to default interface method fallback if not in test map[cite: 2]
            return ItemRegistry.super.getRecipeResult(nameA, nameB);
        }
    }

    // Default fallback verification test double
    private static class DefaultItemRegistry implements ItemRegistry {
        @Override
        public Item getItemById(String id) {
            return null;
        }
    }

    @BeforeEach
    void setup() {
        testRegistry = new TestItemRegistry();

        validItemId1 = "stick";
        validItemId2 = "stone";

        sampleItem1 = (Item) new CommonItem(validItemId1, "item1", "test item 1", false, "fake_path");
        sampleItem2 = (Item) new CommonItem(validItemId2, "item2", "test item 2", false, "fake_path");

        testRegistry.addItem(validItemId1, sampleItem1);
        testRegistry.addItem(validItemId2, sampleItem2);
    }

    @Test
    void testGetItemByIdSuccess() {
        Item retrievedItem = testRegistry.getItemById(validItemId1);

        assertNotNull(retrievedItem, "Item should be retrieved successfully.");
        assertEquals(sampleItem1, retrievedItem, "Retrieved item should match the registered instance.");
    }

    @Test
    void testGetItemByIdNotFound() {
        Item retrievedItem = testRegistry.getItemById("non_existent_item");

        assertNull(retrievedItem, "Retrieving an unregistered item ID should return null.");
    }

    @Test
    void testGetRecipeResultSuccess() {
        testRegistry.addRecipe("stick", "stone", "hammer");

        String result = testRegistry.getRecipeResult("stick", "stone");

        assertEquals("hammer", result, "Crafting stick and stone should produce a hammer.");
    }

    @Test
    void testGetRecipeResultDefaultFallback() {
        ItemRegistry defaultRegistry = new DefaultItemRegistry();

        String result = defaultRegistry.getRecipeResult("stick", "stone");

        assertNull(result, "Default interface method should return null as fallback.");
    }
}
