package application.use_cases.interactable.interact;

import application.use_cases.game_play.ObjectsInfo;
import domain.entities.interactable.Interactable;
import domain.entities.puzzle.Puzzle;
import domain.entities.room.Room;
import domain.entities.user.User;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    // Note: Write specific cases for more complex Interactable's by checking id!
    public void interact(InteractInputData inputData) {
        User player = userDataAccess.getCurrentUser();
        Interactable interactable = dataAccess.getInteractableById(inputData.getInteractableId());

        if (interactable.isInteracted()) {
            repeatedInteraction(player, interactable);
        }
        else {

            if (interactable.needsItem()) {
                itemRequiredFirstInteraction(player, interactable);
            }
            else {
                successfulFirstInteraction(player, interactable, null);
            }
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
        }
        else if (!selectedItemId.equals(interactable.getRequiredItemId())) {
            outputBoundary.prepareFailureView("A different item is required for this interaction.");
        }
        else {
            successfulFirstInteraction(player, interactable, selectedItemId);
        }
    }

    private void repeatedInteraction(User player, Interactable interactable) {
        String unlockedRoomId = interactable.getUnlockedRoomId();

        if (unlockedRoomId != null) {
            if ("main menu".equals(unlockedRoomId)) {
                outputBoundary.prepareMainMenuView();
            }
            else {
                moveToRoom(player, unlockedRoomId);
            }
        }
        else {
            String linkedPuzzleId = interactable.getLinkedPuzzleId();

            if (linkedPuzzleId != null) {
                handleLinkedPuzzle(player, interactable, linkedPuzzleId);
            }
            else {
                String successMessage = makeSuccessMessage(
                        interactable.getRewardItemId(),
                        interactable.getSuccessMessage());

                outputBoundary.prepareSuccessView(
                        new InteractOutputData(
                                successMessage,
                                null,
                                null,
                                null,
                                null,
                                interactable.getId()));
            }
        }
    }

    private void handleLinkedPuzzle(User player, Interactable interactable, String linkedPuzzleId) {

        Puzzle puzzle = dataAccess.getPuzzleById(linkedPuzzleId);
        String puzzleUnlockedRoomId = puzzle.getUnlockedRoomId();

        if (puzzle.isSolved()) {
            if (puzzleUnlockedRoomId != null) {
                if ("main menu".equals(puzzleUnlockedRoomId)) {
                    outputBoundary.prepareMainMenuView();
                }
                else {
                    moveToRoom(player, puzzleUnlockedRoomId);
                }
            }
            else {
                String puzzleSuccessMessage = makeSuccessMessage(
                        puzzle.getRewardItemId(),
                        puzzle.getSuccessMessage());

                outputBoundary.prepareSuccessView(
                        new InteractOutputData(
                                puzzleSuccessMessage,
                                null,
                                null,
                                null,
                                null,
                                interactable.getId()));
            }
        }
    }

    private void moveToRoom(User player, String unlockedRoomId) {
        final Room unlockedRoom = dataAccess.getRoomById(unlockedRoomId);
        player.unlockRoom(unlockedRoom);
        player.switchRoom(unlockedRoom);

        final Map<String, ObjectsInfo> objectsToDisplay = new HashMap<>();
        unlockedRoom.getInteractables().forEach(interactable -> {
            objectsToDisplay.put(interactable.getId(),
                    new ObjectsInfo(interactable.getSprite(),
                            unlockedRoom.getPosition(interactable.getId()),
                            "Interactable"));
        });

        unlockedRoom.getItems().forEach(item -> {
            if (!player.hasItemID(item.getId())) {
                objectsToDisplay.put(item.getId(),
                        new ObjectsInfo(item.getImagePath(),
                                unlockedRoom.getPosition(item.getId()),
                                "Item"));
            }
        });

        unlockedRoom.getHints().forEach(hint -> {
            objectsToDisplay.put(hint.getObjectID(),
                    new ObjectsInfo(hint.getImagePath(),
                            unlockedRoom.getPosition(hint.getObjectID()),
                            "Hint"));
        });
        outputBoundary.prepareRoomView(unlockedRoom.getImagePath(), objectsToDisplay);
    }

    private String makeSuccessMessage(String rewardItemId, String successMessage) {
        String result = successMessage;
        if (rewardItemId != null && successMessage != null && successMessage.contains("You have obtained")) {
            // If we added punctuation.
            if (successMessage.endsWith(".") || successMessage.endsWith("!")) {
                result = successMessage.substring(0, successMessage.length() - 1);
            }
            // "You have obtained a(n) __ already!"
            result = result.concat(" already!");
        }
        return result;
    }
}
