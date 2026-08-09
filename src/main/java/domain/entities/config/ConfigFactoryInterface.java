package domain.entities.config;

public interface ConfigFactoryInterface {
    GameModeConfig createConfig(String modeType, String StartingRoom);
}
