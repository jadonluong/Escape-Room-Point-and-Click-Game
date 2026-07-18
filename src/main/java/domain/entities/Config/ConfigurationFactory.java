package domain.entities.Config;

public class ConfigurationFactory implements ConfigFactoryInterface {

    /**
     * Creates a GameModeConfig based on the provided mode name.
     * @param modeType The string identifier ("quick", "story", etc.)
     * @return The corresponding GameModeConfig entity
     */
    public GameModeConfig createConfig(String modeType, String StartingRoom) {
        if (modeType == null) {
            throw new IllegalArgumentException("Mode type cannot be null");
        }

        // Using a switch block to map strings to concrete entities cleanly
        return switch (modeType.toLowerCase().trim()) {
            case "story" -> new StoryModeConfig();
            case "quick" -> new QuickModeConfig(StartingRoom);
            default -> throw new IllegalArgumentException("Unknown game mode: " + modeType);
        };
    }
}
