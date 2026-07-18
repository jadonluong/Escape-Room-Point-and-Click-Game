package application.use_cases.Item.Drop;

public interface DropOutputBoundary {
    void prepareSuccessView( DropOutputData outputData);
    void prepareFailure(String error);
}
