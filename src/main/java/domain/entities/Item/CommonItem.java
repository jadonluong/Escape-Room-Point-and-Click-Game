package domain.entities.Item;

import java.util.Objects;
import java.util.UUID;

public class CommonItem implements Item {
    private UUID id;
    private String name;
    private String description;
    private Boolean craftable;

    public CommonItem(UUID id, String name, String description, Boolean craftable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.craftable = craftable;
    }

    @Override
    public String getId() {
        return this.id.toString();
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) o;
        return Objects.equals(this.getId(), otherItem.getId());
    }

    /**
     * Overriding hashCode ensures that collections like HashMaps or HashSets
     * locate this item correctly based on its ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
