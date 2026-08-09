package application.use_cases.Item.SelectItem;

public interface SelectItemOutputBoundary {
    void prepareSuccessView(SelectItemOutputData outputData);
    void prepareFailView(String error);
}
