package domain.entities.Config;

public interface ConfigFactoryInterface {
    GameModeConfig createConfig(String modeType, String StartingRoom);
}
