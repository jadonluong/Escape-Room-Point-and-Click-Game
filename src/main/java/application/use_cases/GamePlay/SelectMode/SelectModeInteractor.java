package application.use_cases.GamePlay.SelectMode;

import domain.entities.Config.ConfigurationFactory;
import domain.entities.Config.GameModeConfig;


public class SelectModeInteractor implements SelectModeInputBoundary {
    private final SelectModeOutputBoundary selectModePresenter;

    public SelectModeInteractor(SelectModeOutputBoundary selectModePresenter) {
        this.selectModePresenter = selectModePresenter;
    }


    @Override
    public void execute(SelectModeInputData inputData) {

        //Since we need to start the game for some mode, and browse rooms for some other,
        //so we apply Behavioral Strategy Pattern here.
        inputData.getChosenMode().navigate(selectModePresenter);
    }
}
