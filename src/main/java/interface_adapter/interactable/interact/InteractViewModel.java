package interface_adapter.interactable.interact;

import interface_adapter.ViewModel;

public class InteractViewModel extends ViewModel<InteractState> {
    public InteractViewModel() {
        super("Interact");
        setState(new InteractState());
    }
}
