package interface_adapter.hint;

import interface_adapter.ViewModel;

public class GetHintViewModel extends ViewModel<GetHintState> {

    public GetHintViewModel() {
        super("get hint");
        setState(new GetHintState());
    }
}
