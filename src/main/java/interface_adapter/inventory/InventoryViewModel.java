package interface_adapter.inventory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InventoryViewModel {
    public static final String STATE_PROPERTY = "inventoryState";

    private InventoryState state = new InventoryState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public InventoryState getState() {
        return state;
    }

    public void setState(InventoryState state) {
        this.state = state;
    }

    /**
     * Notifies all registered listeners (like InventoryOverlay) that the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange(STATE_PROPERTY, null, this.state);
    }

    /**
     * Allows JavaFX views or overlays to subscribe to state updates.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
