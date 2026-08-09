package application.use_cases.item.select_item;

import domain.entities.user.User;

public class SelectItemInteractor implements SelectItemInputBoundary{
    private final SelectItemUserDataAccessInterface userSession;
    private final SelectItemOutputBoundary outputBoundary;

    public SelectItemInteractor(SelectItemUserDataAccessInterface userSession,
                                SelectItemOutputBoundary outputBoundary) {
        this.userSession = userSession;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SelectItemInputData inputData) {
        User currentUser = userSession.getCurrentUser();

        if (currentUser != null) {
            // Save the selected item ID into the active user session!
            currentUser.saveSelectedItemID(inputData.itemId());

            // Send output data to presenter if you need to update UI state
            outputBoundary.prepareSuccessView(new SelectItemOutputData(inputData.itemId()));
        }
    }
}
