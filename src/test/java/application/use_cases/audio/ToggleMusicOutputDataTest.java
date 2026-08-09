package application.use_cases.audio.toggle_music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToggleMusicOutputDataTest {

    @Test
    void testIsMusicOnTrue() {
        ToggleMusicOutputData outputData = new ToggleMusicOutputData(true);
        assertTrue(outputData.isMusicOn());
    }

    @Test
    void testIsMusicOnFalse() {
        ToggleMusicOutputData outputData = new ToggleMusicOutputData(false);
        assertFalse(outputData.isMusicOn());
    }
}