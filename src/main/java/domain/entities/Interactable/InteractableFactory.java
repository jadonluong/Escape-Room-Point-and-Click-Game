package domain.entities.Interactable;

public interface InteractableFactory {
    Interactable create(String id, String imagePath, String defaultName, String defaultDescription,
                        String defaultSprite, String interactedName, String interactedDescription,
                        String interactedSprite, boolean isConsumed, boolean consumesItem, boolean needsItem,
                        String requiredItemId, String rewardItemId, String linkedPuzzleId, String unlockedRoomId,
                        String successMessage);
}