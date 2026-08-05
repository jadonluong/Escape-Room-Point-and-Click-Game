package application.use_cases.Puzzle.Solve;

import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;

public class SolveInteractor implements SolveInputBoundary {
    private SolveDataAccessInterface dataAccess;
    private SolveOutputBoundary outputBoundary;
    private SolveUserDataAccessInterface userDataAccess;

    public SolveInteractor(SolveDataAccessInterface dataAccess, SolveOutputBoundary outputBoundary,
                           SolveUserDataAccessInterface userDataAccess) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void solve(SolveInputData inputData) {
        Puzzle puzzle = dataAccess.getPuzzleById(inputData.getPuzzleId());
        if (puzzle.solve(inputData.getPlayerAnswer())) {
            puzzle.setSolved(true); // Ik solve already does this but just in case...

            User player = userDataAccess.getCurrentUser();
            String rewardItemId = puzzle.getRewardItemId();
            String rewardItemName = null;
            if (rewardItemId != null) {
                player.saveItem(dataAccess.getItemById(rewardItemId));
                rewardItemName = dataAccess.getItemById(rewardItemId).getName();
            }

            outputBoundary.prepareSuccessView(new SolveOutputData(puzzle.getSuccessMessage(), rewardItemId,
                    rewardItemName, inputData.getInteractableId()));
        } else {
            outputBoundary.prepareFailureView("Your input was incorrect.");
        }
    }
}
