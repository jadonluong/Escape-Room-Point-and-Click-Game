package domain.entities.Crafting;

import domain.entities.Item.Item;
import java.util.Objects;

public class CommonCrafting implements Crafting {
    private final String ingredientId1;
    private final String ingredientId2;
    private final String resultName;
    private final String resultDescription;
    private final Boolean resultCraftable;

    public CommonCrafting(String ingredientId1, String ingredientId2,
                          String resultName, String resultDescription, Boolean resultCraftable) {
        this.ingredientId1 = ingredientId1;
        this.ingredientId2 = ingredientId2;
        this.resultName = resultName;
        this.resultDescription = resultDescription;
        this.resultCraftable = resultCraftable;
    }

    @Override
    public boolean isMatch(Item item1, Item item2) {
        if (item1 == null || item2 == null) {
            return false;
        }

        String id1 = item1.getId();
        String id2 = item2.getId();

        // Order-independent checking: (A + B) or (B + A)
        boolean forwardMatch = Objects.equals(id1, ingredientId1) && Objects.equals(id2, ingredientId2);
        boolean reverseMatch = Objects.equals(id1, ingredientId2) && Objects.equals(id2, ingredientId1);

        return forwardMatch || reverseMatch;
    }

    @Override
    public String getResultName() {
        return this.resultName;
    }

    @Override
    public String getResultDescription() {
        return this.resultDescription;
    }

    @Override
    public Boolean isResultCraftable() {
        return this.resultCraftable;
    }
}
