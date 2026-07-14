package domain.entities.Interactable;

import java.util.UUID;

public interface InteractableFactory {
    Interactable create(UUID id, String defaultName, String defaultDescription, String defaultSprite,
                        String interactedName, String interactedDescription, String interactedSprite,
                        boolean isConsumed, boolean needsItem, UUID requiredItemId, UUID rewardItemId,
                        UUID linkedPuzzleId, UUID unlockedRoomId, String successMessage);
}