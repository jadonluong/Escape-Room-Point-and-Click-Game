package application.use_cases.puzzle.enter_exit;

import domain.entities.puzzle.AnagramPuzzle;
import domain.entities.puzzle.CodeLockPuzzle;
import domain.entities.puzzle.CryptogramPuzzle;
import domain.entities.puzzle.Puzzle;
import domain.entities.user.User;

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
        final Puzzle puzzle = dataAccess.getPuzzleById(inputData.getPuzzleId());
        final User player = userDataAccess.getCurrentUser();

        if (puzzle instanceof CryptogramPuzzle) {
            final String cipherKeyId = ((CryptogramPuzzle) puzzle).getCipherKeyId();
            if (cipherKeyId != null && !player.hasItemID(cipherKeyId)) {
                outputBoundary.prepareFailureView("You need a cipher key to decode this!");
            }
            else {
                outputBoundary.prepareEnterView(makeOutputData(puzzle, inputData.getInteractableId()));
            }
        }
        else {
            outputBoundary.prepareEnterView(makeOutputData(puzzle, inputData.getInteractableId()));
        }
    }

    @Override
    public void exit() {
        outputBoundary.prepareExitView();
    }

    private EnterExitOutputData makeOutputData(Puzzle puzzle, String interactableId) {
        if (puzzle instanceof AnagramPuzzle) {
            return new EnterExitOutputData(puzzle.getId(), "Anagram", puzzle.getDescription(),
                    ((AnagramPuzzle) puzzle).getHint(), ((AnagramPuzzle) puzzle).getScrambled(), interactableId);
        } else if (puzzle instanceof CryptogramPuzzle) {
            return new EnterExitOutputData(puzzle.getId(), "Cryptogram", puzzle.getDescription(),
                    ((CryptogramPuzzle) puzzle).getEncrypted(), ((CryptogramPuzzle) puzzle).getCipher(),
                    interactableId);
        } else if (puzzle instanceof CodeLockPuzzle) {
            return new EnterExitOutputData(puzzle.getId(), "CodeLock", puzzle.getDescription(),
                    ((CodeLockPuzzle) puzzle).getHint(), interactableId);
        }
        return null; // Won't happen unless a new type of Puzzle class is added.
    }
}
