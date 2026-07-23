package domain.entities.Interactable;

public class CommonInteractable implements Interactable {
    private String id;
    private String imagePath;

    private String defaultName;
    private String defaultDescription;
    private String defaultSprite;

    private String interactedName;
    private String interactedDescription;
    private String interactedSprite;

    private boolean interacted;
    private boolean isConsumed; // Will this Interactable disappear upon interaction?
    private boolean consumesItem; // Will this Interactable consume the Item upon use?

    private boolean needsItem;
    private String requiredItemId;
    private String rewardItemId;
    private String linkedPuzzleId;
    private String unlockedRoomId;
    private String successMessage;

    public CommonInteractable(String id, String imagePath, String defaultName, String defaultDescription,
                              String defaultSprite, String interactedName, String interactedDescription,
                              String interactedSprite, boolean isConsumed, boolean consumesItem, boolean needsItem,
                              String requiredItemId, String rewardItemId, String linkedPuzzleId, String unlockedRoomId,
                              String successMessage) {
        this.id = id;
        this.imagePath = imagePath;
        this.defaultName = defaultName;
        this.defaultDescription = defaultDescription;
        this.defaultSprite = defaultSprite;
        this.interactedName = interactedName;
        this.interactedDescription = interactedDescription;
        this.interactedSprite = interactedSprite;
        this.interacted = false;
        this.isConsumed = isConsumed;
        this.consumesItem = consumesItem;
        this.needsItem = needsItem;
        this.requiredItemId = requiredItemId;
        this.rewardItemId = rewardItemId;
        this.linkedPuzzleId = linkedPuzzleId;
        this.unlockedRoomId = unlockedRoomId;
        this.successMessage = successMessage;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
    public boolean isConsumesItem() {
        return consumesItem;
    }

    @Override
    public boolean needsItem() {
        return needsItem;
    }

    @Override
    public String getRequiredItemId() {
        return requiredItemId;
    }

    @Override
    public String getRewardItemId() {
        return rewardItemId;
    }

    @Override
    public String getLinkedPuzzleId() {
        return linkedPuzzleId;
    }

    @Override
    public String getUnlockedRoomId() {
        return unlockedRoomId;
    }

    @Override
    public String getSuccessMessage() {
        return successMessage;
    }
}