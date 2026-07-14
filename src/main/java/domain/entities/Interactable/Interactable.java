package domain.entities.Interactable;

import java.util.UUID;

public interface Interactable {
    UUID getId();

    String getName();
    String getDescription();
    String getSprite();

    boolean isInteracted();
    void setInteracted(boolean interacted);

    boolean isConsumed();

    boolean needsItem();
    UUID getRequiredItemId();
    UUID getRewardItemId();
    UUID getLinkedPuzzleId();
    UUID getUnlockedRoomId();
    String getSuccessMessage();
}