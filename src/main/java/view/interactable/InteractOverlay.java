package view.interactable;

import interface_adapter.Interactable.Interact.InteractViewModel;
import interface_adapter.ViewManagerModel;
import view.common.ModalOverlay;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class InteractOverlay extends ModalOverlay implements ActionListener, PropertyChangeListener {
    private final InteractViewModel interactViewModel;
    private final ViewManagerModel viewManagerModel;
}
