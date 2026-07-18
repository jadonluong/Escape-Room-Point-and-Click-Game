package application.use_cases.GamePlay.SelectMode;

import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;


public class SelectModeInteractor implements SelectModeInputBoundary {
    private final SelectModeOutputBoundary selectModePresenter;
    private final ConfigurationFactory configFactory;

    public SelectModeInteractor(SelectModeOutputBoundary selectModePresenter, ConfigurationFactory configFactory) {
        this.selectModePresenter = selectModePresenter;
        this.configFactory = configFactory;
    }


    @Override
    public void execute(SelectModeInputData inputData) {
        // Use the config Factory to generate the actual configuration object.
        GameModeConfig chosenMode = configFactory.createConfig(inputData.getChosenModeConfig());

        // 1. Process configuration
        if (chosenMode != null) {

            chosenMode.configure();

            // 2. Alert the presenter, and pass back the room that user is starting at.
            selectModePresenter.prepareGameStartView(chosenMode.getStartingRoomId());
        }
    }
}
