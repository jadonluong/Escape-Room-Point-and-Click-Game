package application.use_cases.GamePlay.SelectMode.Navigation;

import application.use_cases.GamePlay.SelectMode.SelectModeOutPutData;
import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;

public class StoryMode implements ModeNavigation {
    private final StoryModeDataAccessInterface dataAccess;

    public StoryMode(StoryModeDataAccessInterface DataAccess) {

        this.dataAccess = DataAccess;
    }

    @Override
    public void navigate(SelectModeOutputBoundary presenter) {
        //TODO: set up story mode(like put the user in their starting room, clean the inventory)
        //Since there is no room selection for story mode, we configure directly
        ConfigurationFactory configFactory = new ConfigurationFactory();
        GameModeConfig chosenMode = configFactory.createConfig("story", dataAccess.findStartingRoom());

        //Call the presenter to prepare view. First wrap the data.
        SelectModeOutPutData outPutData = new SelectModeOutPutData(chosenMode.getStartingRoomId());
        presenter.prepareGameStartView(outPutData);
    }
}
