package domain.entities.Item;

public interface ItemFactory {

        Item createItem(String name, String description, Boolean craftable,
                        String imagePath, Boolean pickable);

        Item restoreItem(String id, String name, String description, Boolean craftable,
                         String imagePath, Boolean pickable);
}
