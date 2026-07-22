package application.use_cases.Puzzle.Solve;

import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;

public class SolveInteractor implements SolveInputBoundary {
    private SolveDataAccessInterface dataAccess;
    private SolveOutputBoundary outputBoundary;

    public SolveInteractor(SolveDataAccessInterface dataAccess, SolveOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void solve(SolveInputData inputData) {
        Puzzle puzzle = dataAccess.getPuzzleById(inputData.getPuzzleId());
        if (puzzle.solve(inputData.getPlayerAnswer())) {
            puzzle.setSolved(true);
            dataAccess.savePuzzle(puzzle);

            User player = dataAccess.getUserById(inputData.getUserId());
            String rewardItemId = puzzle.getRewardItemId();
            if (rewardItemId != null) {
                player.saveItem(dataAccess.getItemById(rewardItemId));
                dataAccess.saveUser(player);
            }

            SolveOutputData outputData = new SolveOutputData(inputData.getUserId(), inputData.getInteractableId(),
                    puzzle.getSuccessMessage());
            outputBoundary.prepareSuccessView(outputData);
        } else {
            outputBoundary.prepareFailureView("Your input was incorrect.");
        }
    }
}
