package application.use_cases.Item.Drop;

public interface DropOutputBoundary {
    void prepareSuccessView(DropOutputData outputData);
    void prepareFailView(String error);
}
