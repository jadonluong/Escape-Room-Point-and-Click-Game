package application.use_cases.GamePlay.SelectMode.Navigation;

import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;

public class StoryMode implements ModeNavigation {
    private final StoryModeDataAccessInterface DataAccess;

    public StoryMode(StoryModeDataAccessInterface DataAccess) {

        this.DataAccess = DataAccess;
    }

    @Override
    public void navigate(SelectModeOutputBoundary presenter) {

        //Since there is no room selection for story mode, we configure directly
        ConfigurationFactory configFactory = new ConfigurationFactory();
        GameModeConfig chosenMode = configFactory.createConfig("story", DataAccess.findStartingRoom());

        //Call the presenter to prepare view
        presenter.prepareGameStartView(chosenMode.getStartingRoomId());
        //TODO: also pass anything(like objects of Room) thats needed for view.
    }
}
