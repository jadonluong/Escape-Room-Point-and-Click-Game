package application.use_cases.GamePlay.QuickPlay.SelectRoom;

import domain.entities.Config.QuickModeConfig;
import domain.entities.Config.StoryModeConfig;

import java.util.List;

public class SelectRoomInteractor implements SelectRoomInputBoundary {
    private final SelectRoomOutputBoundary presenter;

    public SelectRoomInteractor(SelectRoomOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(SelectRoomInputData inputData) {
        String targetRoom = inputData.getTargetRoom();

        if (targetRoom == null || targetRoom.trim().isEmpty()) {
            presenter.prepareFailView("No room target selected.");
            return;
        }

        //This targertRoom should somehow represent which room we are going to.

        //TODO: Write separate quick Room config after they are done.
        QuickModeConfig quickModeConfig = new QuickModeConfig(targetRoom);

        SelectRoomOutputData outputData = new SelectRoomOutputData(quickModeConfig.getStartingRoomId(),
                quickModeConfig.getInteractableObjects(),false);

        presenter.prepareSuccessView(outputData);
    }

}
