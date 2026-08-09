package interface_adapter.inventory;

import application.use_cases.item.select_item.SelectItemInputBoundary;
import application.use_cases.item.select_item.SelectItemInputData;

public class SelectItemController {
    private final SelectItemInputBoundary selectItemInteractor;

    public SelectItemController(SelectItemInputBoundary selectItemInteractor) {
        this.selectItemInteractor = selectItemInteractor;
    }

    public void execute(String itemId) {
        SelectItemInputData inputData = new SelectItemInputData(itemId);
        selectItemInteractor.execute(inputData);
    }
}