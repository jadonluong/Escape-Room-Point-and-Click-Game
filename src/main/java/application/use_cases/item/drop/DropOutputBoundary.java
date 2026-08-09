package application.use_cases.item.drop;

public interface DropOutputBoundary {
    void prepareSuccessView(DropOutputData outputData);
    void prepareFailView(String error);
}
