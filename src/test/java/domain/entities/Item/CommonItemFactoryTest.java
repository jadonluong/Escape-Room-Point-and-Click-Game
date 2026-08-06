package domain.entities.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonItemFactoryTest {

    private CommonItemFactory factory;

    @BeforeEach
    void setUp() {
        factory = new CommonItemFactory();
    }

    @Test
    void createItem() {
        String name = "Stick";
        String description = "A wooden stick";
        Boolean craftable = true;
        String imagePath = "assets/stick.png";

        Item item = factory.createItem(name, description, craftable, imagePath);

        assertNotNull(item);
        assertNotNull(item.getId());
        assertFalse(item.getId().isBlank());
        assertEquals(name, item.getName());
        assertEquals(description, item.getDescription());
        assertEquals(craftable, item.getCraftable());
        assertEquals(imagePath, item.getImagePath());
    }

    @Test
    void createItemGeneratesUniqueIds() {
        Item item1 = factory.createItem("Key", "A key", false, "key.png");
        Item item2 = factory.createItem("Key", "A key", false, "key.png");

        assertNotEquals(item1.getId(), item2.getId());
    }

    @Test
    void restoreItem() {
        String customId = "prisonitem_1";
        String name = "Rusty Key";
        String description = "Opens cell 1";
        Boolean craftable = false;
        String imagePath = "assets/key.png";

        Item item = factory.restoreItem(customId, name, description, craftable, imagePath);

        assertNotNull(item);
        assertEquals(customId, item.getId());
        assertEquals(name, item.getName());
        assertEquals(description, item.getDescription());
        assertEquals(craftable, item.getCraftable());
        assertEquals(imagePath, item.getImagePath());
    }
}