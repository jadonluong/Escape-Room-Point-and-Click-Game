package application.use_cases.crafting;

/**
 * Output data containing the result of a crafting operation.
 */
public record CraftingOutputData(String craftedItemName, boolean isSuccess) {
    /**
     * Creates crafting output data with the result of a crafting operation.
     *
     * @param craftedItemName the name of the crafted item
     * @param isSuccess       {@code true} if the crafting operation was successful;
     *                        {@code false} otherwise
     */
    public CraftingOutputData {
    }

    /**
     * Returns the name of the crafted item.
     *
     * @return the crafted item name
     */
    @Override
    public String craftedItemName() {
        return this.craftedItemName;
    }

    /**
     * Returns whether the crafting operation was successful.
     *
     * @return {@code true} if crafting was successful; {@code false} otherwise
     */
    @Override
    public boolean isSuccess() {
        return this.isSuccess;
    }
}
