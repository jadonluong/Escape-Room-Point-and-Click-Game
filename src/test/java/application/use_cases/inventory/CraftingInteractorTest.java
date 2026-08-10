package application.use_cases.inventory;

import application.game_registry.ItemRegistry;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import domain.entities.user.CommonUser;
import application.use_cases.crafting.CraftingInteractor;
import application.use_cases.crafting.CraftingDataAccessInterface;
import application.use_cases.crafting.CraftingOutputData;
import application.use_cases.crafting.CraftingInputData;
import application.use_cases.crafting.CraftingOutputBoundary;
import application.use_cases.crafting.CraftingInputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CraftingInteractorTest {

    private TestUser testUser;
    private TestItemRegistry itemRegistry;
    private TestCraftingDataAccess craftingDataAccess;
    private TestPresenter testPresenter;
    private CraftingInteractor interactor;

    private Item stick;
    private Item stone;
    private Item hammer;

    // --- Inner Test Doubles ---
    private static class TestUser extends CommonUser {
        private final List<Item> inventory = new ArrayList<>();

        public TestUser(String username, String password) {
            super(username, password);
        }

        @Override
        public void saveItem(Item item) {
            inventory.add(item);
        }

        @Override
        public boolean removeItem(Item item) {
            return inventory.remove(item);
        }

        public List<Item> getInventory() {
            return inventory;
        }
    }

    private static class TestItemRegistry implements ItemRegistry {
        private final Map<String, Item> registryMap = new HashMap<>();

        public void addItem(Item item) {
            registryMap.put(item.getId(), item);
        }

        @Override
        public Item getItemById(String id) {
            return registryMap.get(id);
        }
    }

    private static class TestCraftingDataAccess implements CraftingDataAccessInterface {
        private final Map<Set<String>, String> recipes = new HashMap<>();

        public void addRecipe(Set<String> ingredientIdsOrNames, String resultId) {
            recipes.put(ingredientIdsOrNames, resultId);
        }

        @Override
        public Map<Set<String>, String> getCraftingRecipes() {
            return recipes;
        }
    }

    private static class TestPresenter implements CraftingOutputBoundary {
        private CraftingOutputData successData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(CraftingOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public CraftingOutputData getSuccessData() {
            return successData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    @BeforeEach
    void setup() {
        testUser = new TestUser("TestUser", "password123");
        itemRegistry = new TestItemRegistry();
        craftingDataAccess = new TestCraftingDataAccess();
        testPresenter = new TestPresenter();

        interactor = new CraftingInteractor(testPresenter, testUser, itemRegistry, craftingDataAccess);

        // Populate test entities in registry and user inventory
        stick = new CommonItem("item_stick", "Stick", "A wooden stick", true, "stick.png");
        stone = new CommonItem("item_stone", "Stone", "A heavy stone", true, "stone.png");
        hammer = new CommonItem("item_hammer", "Hammer", "A solid hammer", true, "hammer.png");

        testUser.saveItem(stick);
        testUser.saveItem(stone);

        itemRegistry.addItem(stick);
        itemRegistry.addItem(stone);
        itemRegistry.addItem(hammer);
    }

    @Test
    void testExecuteSuccessByItemIds() {
        craftingDataAccess.addRecipe(Set.of("item_stick", "item_stone"), "item_hammer");
        CraftingInputData inputData = new CraftingInputData(stick.getId(), stone.getId());

        interactor.execute(inputData);

        assertNotNull(testPresenter.getSuccessData());
        assertEquals("item_hammer:Hammer", testPresenter.getSuccessData().craftedItemName());
        assertTrue(testPresenter.getSuccessData().isSuccess());
        assertNull(testPresenter.getErrorMessage());

        // Verify inventory transformation
        assertTrue(testUser.getInventory().contains(hammer));
        assertFalse(testUser.getInventory().contains(stick));
        assertFalse(testUser.getInventory().contains(stone));
    }

    @Test
    void testExecuteSuccessByNameFallback() {
        // Recipe indexed by lower-case names instead of IDs
        craftingDataAccess.addRecipe(Set.of("stick", "stone"), "item_hammer");
        CraftingInputData inputData = new CraftingInputData(stick.getId(), stone.getId());

        interactor.execute(inputData);

        assertNotNull(testPresenter.getSuccessData());
        assertEquals("item_hammer:Hammer", testPresenter.getSuccessData().craftedItemName());
    }

    @Test
    void testExecuteFailWhenItemsAreNull() {
        CraftingInputData inputData = new CraftingInputData(null, null);

        interactor.execute(inputData);

        assertEquals("Select two valid items to craft!", testPresenter.getErrorMessage());
        assertNull(testPresenter.getSuccessData());
    }

    @Test
    void testExecuteFailWhenNoRecipeMatches() {
        CraftingInputData inputData = new CraftingInputData(stick.getId(), stone.getId());

        interactor.execute(inputData);

        assertEquals("These items cannot be combined.", testPresenter.getErrorMessage());
        assertNull(testPresenter.getSuccessData());
    }

    @Test
    void testExecuteFailWhenCraftedItemNotInRegistry() {
        craftingDataAccess.addRecipe(Set.of("item_stick", "item_stone"), "non_existent_result_id");
        CraftingInputData inputData = new CraftingInputData(stick.getId(), stone.getId());

        interactor.execute(inputData);

        assertEquals("Crafted item not found in registry.", testPresenter.getErrorMessage());
        assertNull(testPresenter.getSuccessData());
    }
}