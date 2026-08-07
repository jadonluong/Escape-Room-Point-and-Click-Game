package view.common;

@FunctionalInterface
public interface OverlayFactory {
    AbstractModalOverlay create(Runnable onClose);
}