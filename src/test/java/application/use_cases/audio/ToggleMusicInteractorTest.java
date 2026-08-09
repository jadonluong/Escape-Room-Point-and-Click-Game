package application.use_cases.audio;

import domain.entities.audio.AudioSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToggleMusicInteractorTest {

    private ToggleMusicInteractor interactor;
    private TestMusicPresenter presenter;
    private AudioSettings audioSettings;

    @BeforeEach
    public void setUp() {
        AudioSettings audioSettings = new AudioSettings();
        this.audioSettings = audioSettings;

        TestMusicPresenter presenter = new TestMusicPresenter();
        this.presenter = presenter;

        ToggleMusicInteractor interactor = new ToggleMusicInteractor(presenter, audioSettings);
        this.interactor = interactor;
    }

    @Test
    void testFirstToggleTurnsMusicOff() {
        interactor.toggleMusic();
        assertFalse(presenter.lastOutputData.isMusicOn());
    }

    @Test
    void testFirstToggleUpdatesTheEntity() {
        interactor.toggleMusic();
        assertFalse(audioSettings.isMusicOn());
    }

    @Test
    void testSecondToggleTurnsMusicBackOn() {
        interactor.toggleMusic();
        interactor.toggleMusic();
        assertTrue(presenter.lastOutputData.isMusicOn());
    }

    @Test
    void testPresenterIsCalledOnceEachToggle() {
        interactor.toggleMusic();
        assertEquals(1, presenter.callCount);

        interactor.toggleMusic();
        assertEquals(2, presenter.callCount);
    }

    @Test
    void testTogglingMusicDoesNotAffectSfxSetting() {
        interactor.toggleMusic();
        assertTrue(audioSettings.isSfxOn());
    }

    /**
     * A fake presenter used to capture the output produced by the Interactor,
     * without depending on the real ToggleMusicPresenter or any JavaFX code.
     */
    private static class TestMusicPresenter implements ToggleMusicOutputBoundary {
        private ToggleMusicOutputData lastOutputData;
        private int callCount;

        @Override
        public void presentMusicState(ToggleMusicOutputData outputData) {
            this.lastOutputData = outputData;
            this.callCount++;
        }
    }
}