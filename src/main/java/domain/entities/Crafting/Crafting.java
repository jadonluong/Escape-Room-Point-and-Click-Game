package domain.entities.Crafting;

import domain.entities.Item.Item;

public interface Crafting {
    boolean isMatch(Item item1, Item item2);

    String getResultName();
    String getResultDescription();
    Boolean isResultCraftable();
}
