package application.use_cases.registries;

import application.game_registry.CommonItemRegistry;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonItemRegistryTest {
    private CommonItemRegistry registry;
    private Item sampleItem1;
    private Item sampleItem2;

    @BeforeEach
    void setup() {
        registry = new CommonItemRegistry(); //[cite: 4]

        sampleItem1 = (Item) new CommonItem("item_1", "item1", "test item 1", false, "fake_path");
        sampleItem2 = (Item) new CommonItem("item_2", "item2", "test item 2", false, "fake_path");

        registry.registerItem(sampleItem1); //[cite: 4]
        registry.registerItem(sampleItem2); //[cite: 4]
    }

    @Test
    void testGetItemByIdSuccess() {
        Item retrievedItem = registry.getItemById("iron_sword"); //[cite: 4]

        assertNotNull(retrievedItem, "Registered item should be retrieved successfully.");
        assertEquals(sampleItem1, retrievedItem, "Retrieved item should match the registered instance.");
    }

    @Test
    void testGetItemByIdNotFound() {
        Item retrievedItem = registry.getItemById("magic_wand"); //[cite: 4]

        assertNull(retrievedItem, "Retrieving an unregistered item ID should return null.");
    }

    @Test
    void testRegisterItemNull() {
        assertDoesNotThrow(() -> registry.registerItem(null), "Registering null should not throw an exception."); //[cite: 4]
        assertNull(registry.getItemById(null), "Null ID lookup should return null."); //[cite: 4]
    }

    @Test
    void testGetRecipeResultWithNullInputs() {
        assertNull(registry.getRecipeResult(null, "wooden_shield"), "Should return null when first item name is null."); //[cite: 4]
        assertNull(registry.getRecipeResult("iron_sword", null), "Should return null when second item name is null."); //[cite: 4]
        assertNull(registry.getRecipeResult(null, null), "Should return null when both item names are null."); //[cite: 4]
    }

    @Test
    void testGetRecipeResultUnregisteredRecipe() {
        String result = registry.getRecipeResult("iron_sword", "wooden_shield"); //[cite: 4]

        assertNull(result, "Should return null when no matching recipe exists in the catalog."); //[cite: 4]
    }
}
