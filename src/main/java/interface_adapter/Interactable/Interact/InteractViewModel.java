package interface_adapter.Interactable.Interact;

import interface_adapter.ViewModel;

public class InteractViewModel extends ViewModel<InteractState> {
    public InteractViewModel() {
        super("Interact");
        setState(new InteractState());
    }
}
