package domain.entities.Item;

public interface Item {

    String getId();
    String getName();
    String getDescription();
    Boolean getCraftable();
    Boolean getPickable();
    String getImagePath();
}
