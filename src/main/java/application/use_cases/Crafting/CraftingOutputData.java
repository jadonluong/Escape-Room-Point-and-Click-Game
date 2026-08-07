package application.use_cases.Crafting;

/**
 * Output data containing the result of a crafting operation.
 */
public class CraftingOutputData {
    private final String craftedItemName;
    private final boolean isSuccess;

    /**
     * Creates crafting output data with the result of a crafting operation.
     *
     * @param craftedItemName the name of the crafted item
     * @param isSuccess {@code true} if the crafting operation was successful;
     *                  {@code false} otherwise
     */
    public CraftingOutputData(String craftedItemName, boolean isSuccess) {
        this.craftedItemName = craftedItemName;
        this.isSuccess = isSuccess;
    }

    /**
     * Returns the name of the crafted item.
     *
     * @return the crafted item name
     */
    public String getCraftedItemName() {
        return this.craftedItemName;
    }

    /**
     * Returns whether the crafting operation was successful.
     *
     * @return {@code true} if crafting was successful; {@code false} otherwise
     */
    public boolean isSuccess() {
        return this.isSuccess;
    }
}
