package application.use_cases.GamePlay.QuickPlay.QuickModeStartUp;

import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;


public class SelectRoomInteractor implements SelectRoomInputBoundary {
    private final SelectRoomOutputBoundary presenter;

    public SelectRoomInteractor(SelectRoomOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(SelectRoomInputData inputData) {
        String targetRoom = inputData.getTargetRoom();

        //This targertRoom represent which room we are going to.
        //TODO: set up quick mode(like put the user in their starting room, clean the inventory)
        ConfigurationFactory configFactory = new ConfigurationFactory();

        GameModeConfig chosenMode = configFactory.createConfig("quick", inputData.getTargetRoom());

        SelectRoomOutputData outputData = new SelectRoomOutputData(targetRoom);

        presenter.prepareSuccessView(outputData);
    }

}
