package interface_adapter.Hint;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetHintViewModel extends ViewModel<GetHintState> {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


    public GetHintViewModel() {
        super("get hint");
        setState(new GetHintState());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.getState());
    }
}
