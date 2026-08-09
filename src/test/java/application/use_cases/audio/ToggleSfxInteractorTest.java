package application.use_cases.audio;

import domain.entities.audio.AudioSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToggleSfxInteractorTest {

    private ToggleSfxInteractor interactor;
    private TestSfxPresenter presenter;
    private AudioSettings audioSettings;

    @BeforeEach
    public void setUp() {
        AudioSettings audioSettings = new AudioSettings();
        this.audioSettings = audioSettings;

        TestSfxPresenter presenter = new TestSfxPresenter();
        this.presenter = presenter;

        ToggleSfxInteractor interactor = new ToggleSfxInteractor(presenter, audioSettings);
        this.interactor = interactor;
    }

    @Test
    void testFirstToggleTurnsSfxOff() {
        interactor.toggleSfx();
        assertFalse(presenter.lastOutputData.isSfxOn());
    }

    @Test
    void testFirstToggleUpdatesTheEntity() {
        interactor.toggleSfx();
        assertFalse(audioSettings.isSfxOn());
    }

    @Test
    void testSecondToggleTurnsSfxBackOn() {
        interactor.toggleSfx();
        interactor.toggleSfx();
        assertTrue(presenter.lastOutputData.isSfxOn());
    }

    @Test
    void testPresenterIsCalledOnceEachToggle() {
        interactor.toggleSfx();
        assertEquals(1, presenter.callCount);

        interactor.toggleSfx();
        assertEquals(2, presenter.callCount);
    }

    @Test
    void testTogglingSfxDoesNotAffectMusicSetting() {
        interactor.toggleSfx();
        assertTrue(audioSettings.isMusicOn());
    }

    /**
     * A fake presenter used to capture the output produced by the Interactor,
     * without depending on the real ToggleSfxPresenter or any JavaFX code.
     */
    private static class TestSfxPresenter implements ToggleSfxOutputBoundary {
        private ToggleSfxOutputData lastOutputData;
        private int callCount;

        @Override
        public void presentSfxState(ToggleSfxOutputData outputData) {
            this.lastOutputData = outputData;
            this.callCount++;
        }
    }
}