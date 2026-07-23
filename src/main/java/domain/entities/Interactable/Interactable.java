package domain.entities.Interactable;

public interface Interactable {
    String getId();
    String getImagePath();
    void setImagePath(String imagePath);

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