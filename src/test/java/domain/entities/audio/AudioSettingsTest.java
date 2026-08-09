package domain.entities.audio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AudioSettingsTest {

    private AudioSettings audioSettings;

    @BeforeEach
    public void setUp() {
        AudioSettings audioSettings = new AudioSettings();
        this.audioSettings = audioSettings;
    }

    @Test
    void testDefaultSfxIsOn() {
        assertTrue(audioSettings.isSfxOn());
    }

    @Test
    void testDefaultMusicIsOn() {
        assertTrue(audioSettings.isMusicOn());
    }

    @Test
    void testToggleSfxTurnsItOff() {
        audioSettings.toggleSfx();
        assertFalse(audioSettings.isSfxOn());
    }

    @Test
    void testToggleSfxTwiceTurnsItBackOn() {
        audioSettings.toggleSfx();
        audioSettings.toggleSfx();
        assertTrue(audioSettings.isSfxOn());
    }

    @Test
    void testToggleMusicTurnsItOff() {
        audioSettings.toggleMusic();
        assertFalse(audioSettings.isMusicOn());
    }

    @Test
    void testToggleMusicTwiceTurnsItBackOn() {
        audioSettings.toggleMusic();
        audioSettings.toggleMusic();
        assertTrue(audioSettings.isMusicOn());
    }

    @Test
    void testTogglingSfxDoesNotAffectMusic() {
        audioSettings.toggleSfx();
        assertTrue(audioSettings.isMusicOn());
    }

    @Test
    void testTogglingMusicDoesNotAffectSfx() {
        audioSettings.toggleMusic();
        assertTrue(audioSettings.isSfxOn());
    }
}