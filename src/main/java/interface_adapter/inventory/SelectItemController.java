package interface_adapter.inventory;

import application.use_cases.Item.SelectItem.SelectItemInputBoundary;
import application.use_cases.Item.SelectItem.SelectItemInputData;

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