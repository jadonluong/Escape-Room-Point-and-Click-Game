package application.use_cases.interactable;

import application.use_cases.interactable.zoom.*;
import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import domain.entities.puzzle.AnagramPuzzle;
import domain.entities.puzzle.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ZoomTest {

    private static class TestZoomDataAccess implements ZoomDataAccessInterface {
        private final Map<String, Interactable> interactables = new HashMap<>();
        private final Map<String, Puzzle> puzzles = new HashMap<>();

        @Override
        public Interactable getInteractableById(String interactableId) {
            return interactables.get(interactableId);
        }

        @Override
        public Puzzle getPuzzleById(String puzzleId) {
            return puzzles.get(puzzleId);
        }

        public void addInteractable(Interactable interactable) {
            interactables.put(interactable.getId(), interactable);
        }

        public void addPuzzle(Puzzle puzzle) {
            puzzles.put(puzzle.getId(), puzzle);
        }
    }

    private static class TestZoomOutputBoundary implements ZoomOutputBoundary {
        public ZoomOutputData lastOutputData;
        public boolean zoomOutCalled = false;
        public boolean failureCalled = false;
        public String failureMessage = null;

        @Override
        public void prepareZoomInView(ZoomOutputData outputData) {
            this.lastOutputData = outputData;
        }

        @Override
        public void prepareZoomOutView() {
            this.zoomOutCalled = true;
        }

        @Override
        public void prepareFailureView(String errorMessage) {
            this.failureCalled = true;
            this.failureMessage = errorMessage;
        }
    }

    private TestZoomDataAccess dataAccess;
    private TestZoomOutputBoundary outputBoundary;
    private ZoomInteractor interactor;

    private final String INTERACTABLE_ID = "interactable_1";
    private final String PUZZLE_ID = "puzzle_1";

    @BeforeEach
    void setUp() {
        dataAccess = new TestZoomDataAccess();
        outputBoundary = new TestZoomOutputBoundary();
        interactor = new ZoomInteractor(dataAccess, outputBoundary);
    }

    private Interactable createInteractable(String id, String defaultName, String defaultDescription,
                                            String defaultSprite, String interactedName,
                                            String interactedDescription, String interactedSprite,
                                            boolean isConsumed, boolean consumesItem, boolean needsItem,
                                            String requiredItemId, String rewardItemId,
                                            String linkedPuzzleId, String unlockedRoomId,
                                            String successMessage) {
        return new CommonInteractable(
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
    }

    private Interactable createBasicInteractable(String id) {
        return createInteractable(
                id,
                "Chest",
                "A locked chest",
                "chest_closed.png",
                "Open Chest",
                "An open chest",
                "chest_open.png",
                false,
                false,
                false,
                null,
                null,
                null,
                null,
                "You opened the chest! It's empty."
        );
    }

    private Interactable createInteractableWithItem(String id) {
        return createInteractable(
                id,
                "Locked Chest",
                "A locked chest",
                "chest_closed.png",
                null,
                null,
                null,
                false,
                false,
                true,
                "key_1",
                null,
                null,
                null,
                null
        );
    }

    private Interactable createInteractableWithPuzzle(String id, String puzzleId) {
        return createInteractable(
                id,
                "Puzzle Lock",
                "A lock with a puzzle",
                "lock.png",
                null,
                null,
                null,
                false,
                false,
                false,
                null,
                null,
                puzzleId,
                null,
                null
        );
    }

    private Interactable createInteractableWithRoom(String id, String roomId) {
        return createInteractable(
                id,
                "Door",
                "A door",
                "door.png",
                "Open Door",
                "An open door",
                "door_open.png",
                false,
                false,
                false,
                null,
                null,
                null,
                roomId,
                null
        );
    }

    private Interactable createInteractableWithNoAction(String id) {
        return createInteractable(
                id,
                "Wall Note",
                "A note on the wall",
                "note.png",
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
    }

    private Interactable createInteractableInteractedWithMessage(String id) {
        Interactable interactable = createBasicInteractable(id);
        interactable.setInteracted(true);
        return interactable;
    }

    private AnagramPuzzle createUnsolvedPuzzle(String id) {
        return new AnagramPuzzle(
                id,
                "PDAISRE",
                "DESPAIR",
                "A feeling of hopelessness",
                "You solved it!",
                null,
                null
        );
    }

    private AnagramPuzzle createSolvedPuzzle(String id) {
        AnagramPuzzle puzzle = createUnsolvedPuzzle(id);
        puzzle.setSolved(true);
        return puzzle;
    }

    private AnagramPuzzle createSolvedPuzzleWithRoom(String id, String roomId) {
        AnagramPuzzle puzzle = new AnagramPuzzle(
                id,
                "PDAISRE",
                "DESPAIR",
                "A feeling of hopelessness",
                "You solved it!",
                null,
                roomId
        );
        puzzle.setSolved(true);
        return puzzle;
    }

    @Test
    void testZoomIn_basic() {
        Interactable interactable = createBasicInteractable(INTERACTABLE_ID);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals("Chest", outputBoundary.lastOutputData.getName());
        assertEquals("A locked chest", outputBoundary.lastOutputData.getDescription());
        assertEquals("chest_closed.png", outputBoundary.lastOutputData.getSprite());
        assertEquals(INTERACTABLE_ID, outputBoundary.lastOutputData.getInteractableId());
        assertNull(outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Interact", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withInteracted_returnsInteractedNameAndSprite() {
        Interactable interactable = createInteractableInteractedWithMessage(INTERACTABLE_ID);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals("Open Chest", outputBoundary.lastOutputData.getName());
        assertEquals("An open chest", outputBoundary.lastOutputData.getDescription());
        assertEquals("chest_open.png", outputBoundary.lastOutputData.getSprite());
        assertEquals("Interact", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withItemRequired_returnsUseItemLabel() {
        Interactable interactable = createInteractableWithItem(INTERACTABLE_ID);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals("Use Item", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withNoAction_returnsNullLabel() {
        Interactable interactable = createInteractableWithNoAction(INTERACTABLE_ID);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertNull(outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withUnlockedRoomAndInteracted_returnsGoThroughLabel() {
        Interactable interactable = createInteractableWithRoom(INTERACTABLE_ID, "room_2");
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals("Go Through", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withLinkedPuzzle_unsolved_returnsEnterPuzzleLabel() {
        Puzzle puzzle = createUnsolvedPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createInteractableWithPuzzle(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Enter Puzzle", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withLinkedPuzzle_solvedNoRoom_returnsInteractLabel() {
        Puzzle puzzle = createSolvedPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createInteractableWithPuzzle(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Interact", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withLinkedPuzzle_solvedWithRoom_returnsGoThroughLabel() {
        Puzzle puzzle = createSolvedPuzzleWithRoom(PUZZLE_ID, "room_2");
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createInteractableWithPuzzle(INTERACTABLE_ID, PUZZLE_ID);
        interactable.setInteracted(true);
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Go Through", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withLinkedPuzzle_unsolvedButNotInteracted_returnsDefaultInteractLabel() {
        Puzzle puzzle = createUnsolvedPuzzle(PUZZLE_ID);
        dataAccess.addPuzzle(puzzle);

        Interactable interactable = createInteractableWithPuzzle(INTERACTABLE_ID, PUZZLE_ID);
        // NOT interacted
        dataAccess.addInteractable(interactable);

        interactor.zoomIn(new ZoomInputData(INTERACTABLE_ID));

        assertNotNull(outputBoundary.lastOutputData);
        assertEquals(PUZZLE_ID, outputBoundary.lastOutputData.getPuzzleId());
        assertEquals("Interact", outputBoundary.lastOutputData.getInteractLabel());
    }

    @Test
    void testZoomIn_withNullInteractable_doesNotThrow() {
        assertDoesNotThrow(() -> {
            interactor.zoomIn(new ZoomInputData("non_existent_id"));
        });
    }

    @Test
    void testZoomOut() {
        interactor.zoomOut();

        assertTrue(outputBoundary.zoomOutCalled);
    }
}
