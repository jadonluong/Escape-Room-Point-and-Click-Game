package domain.entities.crafting;

import domain.entities.item.Item;

public interface Crafting {
    boolean isMatch(Item item1, Item item2);

    String getResultName();
    String getResultDescription();
    Boolean isResultCraftable();
}
