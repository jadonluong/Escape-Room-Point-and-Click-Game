package domain.entities.Item;

import java.util.Objects;

public class CommonItem implements Item {
    private final String id;
    private final String name;
    private final String description;
    private final Boolean craftable;
    private final String imagePath;
    private final Boolean pickable;

    public CommonItem(String id, String name, String description, Boolean craftable,
                      String imagePath, Boolean pickable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.craftable = craftable;
        this.imagePath = imagePath;
        this.pickable = pickable;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Boolean getCraftable() {
        return this.craftable;
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public Boolean getPickable() {
        return this.pickable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Item otherItem) {
            return Objects.equals(this.getId(), otherItem.getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
