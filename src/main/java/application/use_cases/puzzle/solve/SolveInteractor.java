package application.use_cases.puzzle.solve;

import domain.entities.puzzle.Puzzle;
import domain.entities.user.User;

public class SolveInteractor implements SolveInputBoundary {
    private final SolveDataAccessInterface dataAccess;
    private final SolveOutputBoundary outputBoundary;
    private final SolveUserDataAccessInterface userDataAccess;

    public SolveInteractor(SolveDataAccessInterface dataAccess, SolveOutputBoundary outputBoundary,
                           SolveUserDataAccessInterface userDataAccess) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void solve(SolveInputData inputData) {
        final Puzzle puzzle = dataAccess.getPuzzleById(inputData.getPuzzleId());
        if (puzzle.solve(inputData.getPlayerAnswer())) {
            puzzle.setSolved(true);

            final User player = userDataAccess.getCurrentUser();
            if (player.getStoryModeInteractables() != null) {
                player.saveInteractable(inputData.getInteractableId());
            }

            final String rewardItemId = puzzle.getRewardItemId();
            String rewardItemName = null;
            if (rewardItemId != null) {
                player.saveItem(dataAccess.getItemById(rewardItemId));
                rewardItemName = dataAccess.getItemById(rewardItemId).getName();
            }

            outputBoundary.prepareSuccessView(new SolveOutputData(puzzle.getSuccessMessage(), rewardItemId,
                    rewardItemName, inputData.getInteractableId()));
        }
        else {
            outputBoundary.prepareFailureView("Your input was incorrect.");
        }
    }
}
