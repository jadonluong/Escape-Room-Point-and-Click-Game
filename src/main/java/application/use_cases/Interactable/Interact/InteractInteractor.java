package application.use_cases.Interactable.Interact;

import domain.entities.Interactable.Interactable;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Room.Room;
import domain.entities.User.User;

public class InteractInteractor implements InteractInputBoundary {
    private InteractDataAccessInterface dataAccess;
    private InteractOutputBoundary outputBoundary;

    public InteractInteractor(InteractDataAccessInterface dataAccess, InteractOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override // Note: Write specific cases for more complex Interactable's by checking id!
    public void interact(InteractInputData inputData) {
        User player = inputData.getUser();
        Interactable interactable = dataAccess.getInteractableById(inputData.getInteractableId());

        if (interactable.isInteracted()) {
            repeatedInteraction(player, interactable);
            return;
        }

        if (interactable.needsItem()) {
            itemRequiredFirstInteraction(player, interactable);
        } else {
            successfulFirstInteraction(player, interactable, null);
        }
    }

    private void successfulFirstInteraction(User player, Interactable interactable, String selectedItemId) {
        interactable.setInteracted(true);

        String rewardItemId = interactable.getRewardItemId();
        if (rewardItemId != null) {
            player.saveItem(dataAccess.getItemById(rewardItemId));
        }

        if (interactable.isConsumesItem() && selectedItemId != null) {
            player.removeItem(dataAccess.getItemById(selectedItemId));
        }

        Room currentRoom = dataAccess.getRoomById(player.getCurrentRoomID());
        if (interactable.isConsumed()) {
            currentRoom.removeInteractable(interactable.getId());
        }

        outputBoundary.prepareSuccessView(new InteractOutputData(interactable.getSuccessMessage()));
    }

    private void itemRequiredFirstInteraction(User player, Interactable interactable) {
        String selectedItemId = player.getSelectedItemID();

        if (selectedItemId == null) {
            outputBoundary.prepareFailureView("A specific item is required for this interaction.");
        } else if (!selectedItemId.equals(interactable.getRequiredItemId())) {
            outputBoundary.prepareFailureView("A different item is required for this interaction.");
        } else {
            successfulFirstInteraction(player, interactable, selectedItemId);
        }
    }

    private void repeatedInteraction(User player, Interactable interactable) {
        String unlockedRoomId = interactable.getUnlockedRoomId();
        if (unlockedRoomId != null) {
            moveToRoom(player, unlockedRoomId);
            return;
        }

        String linkedPuzzleId = interactable.getLinkedPuzzleId();
        if (linkedPuzzleId != null) {
            Puzzle puzzle = dataAccess.getPuzzleById(linkedPuzzleId);
            String puzzleUnlockedRoomId = puzzle.getUnlockedRoomId();

            if (puzzle.isSolved() && puzzleUnlockedRoomId != null) {
                moveToRoom(player, puzzleUnlockedRoomId);
            } else if (puzzle.isSolved()) {
                outputBoundary.prepareSuccessView(new InteractOutputData(puzzle.getSuccessMessage()));
            }
            return;
        }

        String successMessage = interactable.getSuccessMessage();
        if (interactable.getRewardItemId() != null) {
            if (successMessage.endsWith(".")) { // In case we forgot to add a period at the end
                successMessage = successMessage.substring(0, successMessage.length() - 1);
            }
            successMessage = successMessage.concat(" already!"); // "You have obtained a(n) __ already!"
        }
        outputBoundary.prepareSuccessView(new InteractOutputData(successMessage));
    }

    private void moveToRoom(User player, String unlockedRoomId) {
        player.switchRoom(dataAccess.getRoomById(unlockedRoomId));
        outputBoundary.prepareRoomView(unlockedRoomId);
    }
}
