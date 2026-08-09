package domain.entities.item;

public interface ItemFactory {

        Item createItem(String name, String description, Boolean craftable,
                        String imagePath);

        Item restoreItem(String id, String name, String description, Boolean craftable,
                         String imagePath);
}
