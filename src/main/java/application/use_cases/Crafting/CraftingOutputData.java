package application.use_cases.Crafting;

public class CraftingOutputData {
    private final String craftedItemName;
    private final boolean isSuccess;

    public CraftingOutputData(String craftedItemName, boolean isSuccess) {
        this.craftedItemName = craftedItemName;
        this.isSuccess = isSuccess;
    }

    public String getCraftedItemName() {
        return this.craftedItemName;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }
}
