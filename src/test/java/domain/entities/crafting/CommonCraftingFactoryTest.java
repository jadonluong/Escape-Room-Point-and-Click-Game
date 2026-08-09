package domain.entities.crafting;

import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonCraftingFactoryTest {

    private CommonCraftingFactory factory;

    @BeforeEach
    void setUp() {
        factory = new CommonCraftingFactory();
    }

    @Test
    void createRecipe() {
        // Creates the recipe via CommonCraftingFactory
        Crafting recipe = factory.createRecipe("stick", "stone", "hammer", "A heavy hammer", true);

        assertNotNull(recipe);

        // Calls getters on Crafting to resolve unused method warnings
        assertEquals("hammer", recipe.getResultName());
        assertEquals("A heavy hammer", recipe.getResultDescription());
        assertTrue(recipe.isResultCraftable());

        // Calls isMatch on Crafting to resolve unused method warning
        Item itemA = new CommonItem("stick", "stick", "wooden stick", true, "stick.png");
        Item itemB = new CommonItem("stone", "stone", "small stone", true, "stone.png");

        assertTrue(recipe.isMatch(itemA, itemB));
    }
}