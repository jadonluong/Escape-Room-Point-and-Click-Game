package application.use_cases.Item.PickUp;

public interface PickUpOutputBoundary {
    void prepareSuccessView(PickUpOutputData outputData);
    void prepareFailView(String error);
}
