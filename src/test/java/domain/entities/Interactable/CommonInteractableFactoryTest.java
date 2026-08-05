package domain.entities.Interactable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonInteractableFactoryTest {

    private CommonInteractableFactory factory = new CommonInteractableFactory();

    @Test
    void createTestNormal() {
        String id = "creative_id";
        String defaultName = "name1";
        String defaultDescription = "description1";
        String defaultSprite = "sprite1";
        String interactedName = "name2";
        String interactedDescription = "description2";
        String interactedSprite = "sprite2";
        boolean isConsumed = true;
        boolean consumesItem = true;
        boolean needsItem = true;
        String requiredItemId = "required_item";
        String rewardItemId = "reward_item";
        String linkedPuzzleId = "linked_puzzle";
        String unlockedRoomId = "unlocked_room";
        String successMessage = "YAY!";

        Interactable interactable = factory.create(
                id,
                defaultName,
                defaultDescription,
                defaultSprite,
                interactedName,
                interactedDescription,
                interactedSprite,
                isConsumed,
                consumesItem,
                needsItem,
                requiredItemId,
                rewardItemId,
                linkedPuzzleId,
                unlockedRoomId,
                successMessage
        );

        assertNotNull(interactable);
        assertEquals(id, interactable.getId());

        // Default
        assertEquals(defaultName, interactable.getName());
        assertEquals(defaultDescription, interactable.getDescription());
        assertEquals(defaultSprite, interactable.getSprite());

        assertFalse(interactable.isInteracted());

        // Interacted
        interactable.setInteracted(true);
        assertTrue(interactable.isInteracted());
        assertEquals(interactedName, interactable.getName());
        assertEquals(interactedDescription, interactable.getDescription());
        assertEquals(interactedSprite, interactable.getSprite());

        assertEquals(isConsumed, interactable.isConsumed());
        assertEquals(consumesItem, interactable.isConsumesItem());
        assertEquals(needsItem, interactable.needsItem());
        assertEquals(requiredItemId, interactable.getRequiredItemId());
        assertEquals(rewardItemId, interactable.getRewardItemId());
        assertEquals(linkedPuzzleId, interactable.getLinkedPuzzleId());
        assertEquals(unlockedRoomId, interactable.getUnlockedRoomId());
        assertEquals(successMessage, interactable.getSuccessMessage());
    }

    @Test
    void createTestNullAndFalse() {
        Interactable interactable = factory.create(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                null,
                null,
                null,
                null
        );
        assertNotNull(interactable);
        assertNull(interactable.getId());
        assertNull(interactable.getName());
        assertNull(interactable.getDescription());
        assertNull(interactable.getSprite());
        assertFalse(interactable.isConsumed());
        assertFalse(interactable.isConsumesItem());
        assertFalse(interactable.needsItem());
        assertNull(interactable.getRequiredItemId());
        assertNull(interactable.getRewardItemId());
        assertNull(interactable.getLinkedPuzzleId());
        assertNull(interactable.getUnlockedRoomId());
        assertNull(interactable.getSuccessMessage());
    }
}