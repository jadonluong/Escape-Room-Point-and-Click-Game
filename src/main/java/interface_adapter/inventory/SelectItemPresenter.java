package interface_adapter.inventory;

import application.use_cases.item.select_item.SelectItemOutputBoundary;
import application.use_cases.item.select_item.SelectItemOutputData;

public class SelectItemPresenter implements SelectItemOutputBoundary {
    @Override
    public void prepareSuccessView(SelectItemOutputData outputData) {
        System.out.println("Active Item Selected: " + outputData.selectedItemId());
        // Update Inventory ViewModel state here if highlighting selected item slot
    }

    @Override
    public void prepareFailView(String error) {
        System.out.println("Failed to select item: " + error);
    }
}
