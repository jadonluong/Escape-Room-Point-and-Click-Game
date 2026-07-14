package domain.entities.Interactable;

import java.util.UUID;

public class CommonInteractable implements Interactable {
    private UUID id;

    private String defaultName;
    private String defaultDescription;
    private String defaultSprite;

    private String interactedName;
    private String interactedDescription;
    private String interactedSprite;

    private boolean interacted;
    private boolean isConsumed; // Will this item be consumed upon interaction?

    private boolean needsItem;
    private UUID requiredItemId;
    private UUID rewardItemId;
    private UUID linkedPuzzleId;
    private UUID unlockedRoomId; // Self-note: Doorway Interactable behind Door!
    private String successMessage;

    public CommonInteractable(UUID id, String defaultName, String defaultDescription, String defaultSprite,
                              String interactedName, String interactedDescription, String interactedSprite,
                              boolean isConsumed, boolean needsItem, UUID requiredItemId, UUID rewardItemId,
                              UUID linkedPuzzleId, UUID unlockedRoomId, String successMessage) {
        this.id = id;
        this.defaultName = defaultName;
        this.defaultDescription = defaultDescription;
        this.defaultSprite = defaultSprite;
        this.interactedName = interactedName;
        this.interactedDescription = interactedDescription;
        this.interactedSprite = interactedSprite;
        this.interacted = false;
        this.isConsumed = isConsumed;
        this.needsItem = needsItem;
        this.requiredItemId = requiredItemId;
        this.rewardItemId = rewardItemId;
        this.linkedPuzzleId = linkedPuzzleId;
        this.unlockedRoomId = unlockedRoomId;
        this.successMessage = successMessage;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        if (interacted) {
            return interactedName;
        }
        return defaultName;
    }

    @Override
    public String getDescription() {
        if (interacted) {
            return interactedDescription;
        }
        return defaultDescription;
    }

    @Override
    public String getSprite() {
        if (interacted) {
            return interactedSprite;
        }
        return defaultSprite;
    }

    @Override
    public boolean isInteracted() {
        return interacted;
    }

    @Override
    public void setInteracted(boolean interacted) {
        this.interacted = interacted;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public boolean needsItem() {
        return needsItem;
    }

    @Override
    public UUID getRequiredItemId() {
        return requiredItemId;
    }

    @Override
    public UUID getRewardItemId() {
        return rewardItemId;
    }

    @Override
    public UUID getLinkedPuzzleId() {
        return linkedPuzzleId;
    }

    @Override
    public UUID getUnlockedRoomId() {
        return unlockedRoomId;
    }

    @Override
    public String getSuccessMessage() {
        return successMessage;
    }
}