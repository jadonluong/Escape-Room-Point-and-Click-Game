package domain.entities.interactable;

public interface Interactable {
    String getId();

    String getName();
    String getDescription();
    String getSprite();

    boolean isInteracted();
    void setInteracted(boolean interacted);

    boolean isConsumed();
    boolean isConsumesItem();

    boolean needsItem();
    String getRequiredItemId();
    String getRewardItemId();
    String getLinkedPuzzleId();
    String getUnlockedRoomId();
    String getSuccessMessage();
}