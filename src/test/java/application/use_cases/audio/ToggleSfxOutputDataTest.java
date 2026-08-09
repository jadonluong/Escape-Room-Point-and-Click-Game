package application.use_cases.audio.toggle_sfx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToggleSfxOutputDataTest {

    @Test
    void testIsSfxOnTrue() {
        ToggleSfxOutputData outputData = new ToggleSfxOutputData(true);
        assertTrue(outputData.isSfxOn());
    }

    @Test
    void testIsSfxOnFalse() {
        ToggleSfxOutputData outputData = new ToggleSfxOutputData(false);
        assertFalse(outputData.isSfxOn());
    }
}