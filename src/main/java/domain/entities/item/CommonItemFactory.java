package domain.entities.item;

import java.util.UUID;

public class CommonItemFactory implements ItemFactory {

    @Override
    public Item createItem(String name, String description, Boolean craftable,
                           String imagePath) {
        String randomId = UUID.randomUUID().toString();
        return new CommonItem(randomId, name, description, craftable, imagePath);
    }

    @Override
    public Item restoreItem(String id, String name, String description, Boolean craftable,
                            String imagePath) {
        return new CommonItem(id, name, description, craftable, imagePath);
    }
}
