package application.use_cases.GamePlay.SelectMode.Navigation;

import application.use_cases.GamePlay.SelectMode.SelectModeOutPutData;
import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;

public class TutorialMode implements ModeNavigation{

    private final TutorialModeDataAccessInterface dataAccess;

    public TutorialMode(TutorialModeDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void navigate(SelectModeOutputBoundary presenter){

        //Since there is no room selection for story mode, we configure directly
        ConfigurationFactory configFactory = new ConfigurationFactory();
        GameModeConfig chosenMode = configFactory.createConfig("story", dataAccess.findStartingRoom());

        //Call the presenter to prepare view. First wrap the data.
        SelectModeOutPutData outPutData = new SelectModeOutPutData(chosenMode.getStartingRoomId());
        presenter.prepareGameStartView(outPutData);
    }
}
