package application.use_cases.GamePlay.SelectMode;

import domain.entities.Config.GameModeConfig;

public class SelectModeInputData {
    private final String chosenModeConfig;

    public SelectModeInputData(String chosenModeConfig) {
        this.chosenModeConfig = chosenModeConfig;
    }

    public String getChosenModeConfig() {
        return chosenModeConfig;
    }
}
