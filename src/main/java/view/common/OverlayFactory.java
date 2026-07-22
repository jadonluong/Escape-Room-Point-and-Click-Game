package view.common;

@FunctionalInterface
public interface OverlayFactory {
    ModalOverlay create(Runnable onClose);
}