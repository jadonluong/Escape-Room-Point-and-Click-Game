package domain.entities.Item;

public interface Item {

    String getId();
    String getName();
    String getDescription();
    Boolean getCraftable();
    String getImagePath();
    Boolean getPickable();
}
