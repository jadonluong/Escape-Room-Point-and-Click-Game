package application.use_cases.GamePlay.SelectMode.Navigation;

import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;
import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;

public class StoryMode implements ModeNavigation {
    private final String initialRoomId;

    public StoryMode(String initialRoomId) {
        this.initialRoomId = initialRoomId;
    }

    @Override
    public void navigate(SelectModeOutputBoundary presenter) {

        //Since there is no room selection for story mode, we configure directly
        ConfigurationFactory configFactory = new ConfigurationFactory();
        GameModeConfig chosenMode = configFactory.createConfig("story", "default");
        chosenMode.configure();

        //Call the presenter to prepare view
        presenter.prepareGameStartView(chosenMode.getStartingRoomId());
        //TODO: also pass anything(like objects of Room) thats needed for view.
    }
}
