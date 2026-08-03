package domain.entities.Interactable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonInteractableTest {

    private CommonInteractable interactable;
    private final String ID = "creative_id";
    private final String DEFAULT_NAME = "name1";
    private final String DEFAULT_DESCRIPTION = "description1";
    private final String DEFAULT_SPRITE = "sprite1";
    private final String INTERACTED_NAME = "name2";
    private final String INTERACTED_DESCRIPTION = "description2";
    private final String INTERACTED_SPRITE = "sprite2";
    private final String REQUIRED_ITEM = "required_item";
    private final String REWARD_ITEM = "reward_item";
    private final String LINKED_PUZZLE = "linked_puzzle";
    private final String UNLOCKED_ROOM = "unlocked_room";
    private final String SUCCESS_MESSAGE = "YAY!";

    @BeforeEach
    void setup() {
        this.interactable = new CommonInteractable(
                ID,
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                DEFAULT_SPRITE,
                INTERACTED_NAME,
                INTERACTED_DESCRIPTION,
                INTERACTED_SPRITE,
                true,
                true,
                true,
                REQUIRED_ITEM,
                REWARD_ITEM,
                LINKED_PUZZLE,
                UNLOCKED_ROOM,
                SUCCESS_MESSAGE
        );
    }

    @Test
    void commonInteractableTestNormal() {
        assertEquals(ID, interactable.getId());
        assertEquals(DEFAULT_NAME, interactable.getName());
        assertEquals(DEFAULT_DESCRIPTION, interactable.getDescription());
        assertEquals(DEFAULT_SPRITE, interactable.getSprite());
        assertFalse(interactable.isInteracted());
        assertTrue(interactable.isConsumed());
        assertTrue(interactable.isConsumesItem());
        assertTrue(interactable.needsItem());
        assertEquals(REQUIRED_ITEM, interactable.getRequiredItemId());
        assertEquals(REWARD_ITEM, interactable.getRewardItemId());
        assertEquals(LINKED_PUZZLE, interactable.getLinkedPuzzleId());
        assertEquals(UNLOCKED_ROOM, interactable.getUnlockedRoomId());
        assertEquals(SUCCESS_MESSAGE, interactable.getSuccessMessage());
    }

    @Test
    void commonInteractableTestNullAndFalse() {
        CommonInteractable nullInteractable = new CommonInteractable(
                null, null, null, null,
                null, null, null,
                false, false, false,
                null, null, null, null, null
        );
        assertNotNull(nullInteractable);
        assertNull(nullInteractable.getId());
        assertNull(nullInteractable.getName());
        assertNull(nullInteractable.getDescription());
        assertNull(nullInteractable.getSprite());
        assertFalse(nullInteractable.isConsumed());
        assertFalse(nullInteractable.isConsumesItem());
        assertFalse(nullInteractable.needsItem());
        assertNull(nullInteractable.getRequiredItemId());
        assertNull(nullInteractable.getRewardItemId());
        assertNull(nullInteractable.getLinkedPuzzleId());
        assertNull(nullInteractable.getUnlockedRoomId());
        assertNull(nullInteractable.getSuccessMessage());
    }

    @Test
    void getNameTestDefault() {
        assertEquals(DEFAULT_NAME, interactable.getName());
    }

    @Test
    void getNameTestInteracted() {
        interactable.setInteracted(true);
        assertEquals(INTERACTED_NAME, interactable.getName());
    }

    @Test
    void getDescriptionTestDefault() {
        assertEquals(DEFAULT_DESCRIPTION, interactable.getDescription());
    }

    @Test
    void getDescriptionTestInteracted() {
        interactable.setInteracted(true);
        assertEquals(INTERACTED_DESCRIPTION, interactable.getDescription());
    }

    @Test
    void getSpriteTestDefault() {
        assertEquals(DEFAULT_SPRITE, interactable.getSprite());
    }

    @Test
    void getSpriteTestInteracted() {
        interactable.setInteracted(true);
        assertEquals(INTERACTED_SPRITE, interactable.getSprite());
    }

    @Test
    void setInteractedTest() {
        interactable.setInteracted(true);
        assertTrue(interactable.isInteracted());

        interactable.setInteracted(false);
        assertFalse(interactable.isInteracted());
    }
}