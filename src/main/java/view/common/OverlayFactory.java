package view.common;

/**
 * Factory interface for creating modal overlays.
 *
 * <p>Implementations use the provided close action to configure the
 * behaviour that occurs when the overlay is closed.</p>
 */
@FunctionalInterface
public interface OverlayFactory {

    /**
     * Creates a modal overlay with the specified close action.
     *
     * @param onClose action to execute when the overlay is closed
     * @return the created modal overlay
     */
    AbstractModalOverlay create(Runnable onClose);
}
