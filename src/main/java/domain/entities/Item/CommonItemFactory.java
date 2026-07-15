package domain.entities.Item;

import java.util.UUID;

public class CommonItemFactory implements ItemFactory {

    @Override
    public Item createItem(String name, String description, Boolean craftable) {
        UUID newId = UUID.randomUUID();
        return new CommonItem(newId, name, description, craftable);
    }

    @Override
    public Item restoreItem(UUID id, String name, String description, Boolean craftable) {
        return new CommonItem(id, name, description, craftable);
    }
}
