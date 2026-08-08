package application.game_registry;

import domain.entities.Item.Item;

public interface ItemRegistry {

    Item getItemById(String id);

    /**
     * Evaluates whether two items can be crafted together.
     * Provides a default fallback so all implementing registries compile.
     */
    default String getRecipeResult(String nameA, String nameB) {
        return null;
    }
}
