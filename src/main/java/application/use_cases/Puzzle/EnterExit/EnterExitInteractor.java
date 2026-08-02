package application.use_cases.Puzzle.EnterExit;

import domain.entities.Puzzle.AnagramPuzzle;
import domain.entities.Puzzle.CodeLockPuzzle;
import domain.entities.Puzzle.CryptogramPuzzle;
import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;

public class EnterExitInteractor implements EnterExitInputBoundary {
    private EnterExitDataAccessInterface dataAccess;
    private EnterExitOutputBoundary outputBoundary;
    private EnterExitUserDataAccessInterface userDataAccess;

    public EnterExitInteractor(EnterExitDataAccessInterface dataAccess, EnterExitOutputBoundary outputBoundary,
                               EnterExitUserDataAccessInterface userDataAccess) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void enter(EnterExitInputData inputData) {
        Puzzle puzzle = dataAccess.getPuzzleById(inputData.getPuzzleId());
        User player = userDataAccess.getCurrentUser();

        if (puzzle instanceof CryptogramPuzzle && !player.hasItemID(((CryptogramPuzzle) puzzle).getCipherKeyId())) {
            outputBoundary.prepareFailureView("You need a cipher key to decode this!");
            return;
        }

        outputBoundary.prepareEnterView(makeOutputData(puzzle));
    }

    @Override
    public void exit() {
        outputBoundary.prepareExitView();
    }

    private EnterExitOutputData makeOutputData(Puzzle puzzle) {
        if (puzzle instanceof AnagramPuzzle) {
            return new EnterExitOutputData(puzzle.getId(), "Anagram", puzzle.getDescription(),
                    ((AnagramPuzzle) puzzle).getHint(), ((AnagramPuzzle) puzzle).getScrambled());
        } else if (puzzle instanceof CryptogramPuzzle) {
            return new EnterExitOutputData(puzzle.getId(), "Cryptogram", puzzle.getDescription(),
                    ((CryptogramPuzzle) puzzle).getEncrypted(), ((CryptogramPuzzle) puzzle).getCipher());
        } else if (puzzle instanceof CodeLockPuzzle) {
            return new EnterExitOutputData(puzzle.getId(), "CodeLock", puzzle.getDescription(),
                    ((CodeLockPuzzle) puzzle).getHint());
        }
        return null; // Won't happen unless a new type of Puzzle class is added.
    }
}
