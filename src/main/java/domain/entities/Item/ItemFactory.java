package domain.entities.Item;

import java.util.UUID;

public interface ItemFactory {

        Item createItem(String name, String description, Boolean craftable);

        Item restoreItem(UUID id, String name, String description, Boolean craftable);
}
