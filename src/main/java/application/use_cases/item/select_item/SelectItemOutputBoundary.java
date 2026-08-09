package application.use_cases.item.select_item;

public interface SelectItemOutputBoundary {
    void prepareSuccessView(SelectItemOutputData outputData);
    void prepareFailView(String error);
}
