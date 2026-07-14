package domain.entities.Interactable;

import java.util.UUID;

public class CommonInteractableFactory implements InteractableFactory {

    @Override
    public Interactable create(UUID id, String defaultName, String defaultDescription, String defaultSprite,
                               String interactedName, String interactedDescription, String interactedSprite,
                               boolean isConsumed, boolean needsItem, UUID requiredItemId, UUID rewardItemId,
                               UUID linkedPuzzleId, UUID unlockedRoomId, String successMessage) {
        return new CommonInteractable(id, defaultName, defaultDescription, defaultSprite, interactedName,
                interactedDescription, interactedSprite, isConsumed, needsItem, requiredItemId, rewardItemId,
                linkedPuzzleId, unlockedRoomId, successMessage);
    }
}