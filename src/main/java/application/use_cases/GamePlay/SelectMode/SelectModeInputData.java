package application.use_cases.GamePlay.SelectMode;

import application.use_cases.GamePlay.SelectMode.Navigation.ModeNavigation;
import domain.entities.Config.GameModeConfig;

public class SelectModeInputData {
    private final ModeNavigation mode;

    public SelectModeInputData(ModeNavigation mode) {
        this.mode = mode;
    }

    public ModeNavigation getChosenMode() {
        return mode;
    }
}
