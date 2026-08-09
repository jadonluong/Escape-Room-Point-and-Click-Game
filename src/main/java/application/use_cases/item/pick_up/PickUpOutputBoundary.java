package application.use_cases.item.pick_up;

public interface PickUpOutputBoundary {
    void prepareSuccessView(PickUpOutputData outputData);
    void prepareFailView(String error);
}
