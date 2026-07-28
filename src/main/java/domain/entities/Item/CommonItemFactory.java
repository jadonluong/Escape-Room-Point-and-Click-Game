package domain.entities.Item;

import java.util.UUID;

public class CommonItemFactory implements ItemFactory {

    @Override
    public Item createItem(String name, String description, Boolean craftable,
                           String imagePath, Boolean pickable) {
        String randomId = UUID.randomUUID().toString();
        return new CommonItem(randomId, name, description, craftable, imagePath, pickable);
    }

    @Override
    public Item restoreItem(String id, String name, String description, Boolean craftable,
                            String imagePath, Boolean pickable) {
        return new CommonItem(id, name, description, craftable, imagePath, pickable);
    }
}
