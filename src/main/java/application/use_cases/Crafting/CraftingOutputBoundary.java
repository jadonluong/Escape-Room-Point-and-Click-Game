package application.use_cases.Crafting;

public interface CraftingOutputBoundary {
    void prepareSuccessView(CraftingOutputData resultData);
    void prepareFailView(String error);
}
