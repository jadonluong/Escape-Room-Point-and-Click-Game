package application.use_cases.Interactable.Interact;

import domain.entities.Interactable.Interactable;
import domain.entities.Puzzle.Puzzle;
import domain.entities.Room.Room;
import domain.entities.User.User;

public class InteractInteractor implements InteractInputBoundary {
    private InteractDataAccessInterface dataAccess;
    private InteractOutputBoundary outputBoundary;
    private InteractUserDataAccessInterface userDataAccess;

    public InteractInteractor(InteractDataAccessInterface dataAccess, InteractOutputBoundary outputBoundary,
                              InteractUserDataAccessInterface userDataAccess) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override // Note: Write specific cases for more complex Interactable's by checking id!
    public void interact(InteractInputData inputData) {
        User player = userDataAccess.getCurrentUser();
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
        String rewardItemName = null;
        if (rewardItemId != null) {
            player.saveItem(dataAccess.getItemById(rewardItemId));
            rewardItemName = dataAccess.getItemById(rewardItemId).getName();
        }

        String selectedItemName = null;
        if (interactable.isConsumesItem() && selectedItemId != null) {
            player.removeItem(dataAccess.getItemById(selectedItemId));
            selectedItemName = dataAccess.getItemById(selectedItemId).getName();
        }

        Room currentRoom = dataAccess.getRoomById(player.getCurrentRoomID());
        if (interactable.isConsumed()) {
            currentRoom.removeInteractable(interactable.getId());
        }

        outputBoundary.prepareSuccessView(new InteractOutputData(interactable.getSuccessMessage(), rewardItemId,
                rewardItemName, selectedItemId, selectedItemName, interactable.getId()));
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

            if (puzzle.isSolved()) {
                if (puzzleUnlockedRoomId != null) {
                    moveToRoom(player, puzzleUnlockedRoomId);
                    return;
                }

                String puzzleSuccessMessage = makeSuccessMessage(puzzle.getRewardItemId(), puzzle.getSuccessMessage());
                outputBoundary.prepareSuccessView(new InteractOutputData(puzzleSuccessMessage, null,
                        null, null, null, interactable.getId()));
            }
            return;
        }

        String successMessage = makeSuccessMessage(interactable.getRewardItemId(), interactable.getSuccessMessage());
        outputBoundary.prepareSuccessView(new InteractOutputData(successMessage, null,
                null, null, null, interactable.getId()));
    }

    private void moveToRoom(User player, String unlockedRoomId) {
        player.switchRoom(dataAccess.getRoomById(unlockedRoomId));
        outputBoundary.prepareRoomView(unlockedRoomId);
    }

    private String makeSuccessMessage(String rewardItemId, String successMessage) {
        String result = successMessage;
        if (rewardItemId != null) {
            if (successMessage.endsWith(".") || successMessage.endsWith("!")) { // If we added punctuation.
                result = successMessage.substring(0, successMessage.length() - 1);
            }
            result = result.concat(" already!"); // "You have obtained a(n) __ already!"
        }
        return result;
    }
}
