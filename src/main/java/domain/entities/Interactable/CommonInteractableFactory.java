package domain.entities.Interactable;

public class CommonInteractableFactory implements InteractableFactory {

    @Override
    public Interactable create(String id, String imagePath, String defaultName, String defaultDescription,
                               String defaultSprite, String interactedName, String interactedDescription,
                               String interactedSprite, boolean isConsumed, boolean consumesItem, boolean needsItem,
                               String requiredItemId, String rewardItemId, String linkedPuzzleId, String unlockedRoomId,
                               String successMessage) {
        return new CommonInteractable(id, imagePath, defaultName, defaultDescription, defaultSprite, interactedName,
                interactedDescription, interactedSprite, isConsumed, consumesItem, needsItem, requiredItemId,
                rewardItemId, linkedPuzzleId, unlockedRoomId, successMessage);
    }
}