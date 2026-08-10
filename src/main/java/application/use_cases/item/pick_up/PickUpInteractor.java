package application.use_cases.item.pick_up;

import application.game_registry.ItemRegistry;
import domain.entities.item.Item;
import domain.entities.user.User;

public class PickUpInteractor implements PickUpInputBoundary {
    private final PickUpUserDataAccessInterface userSession;
    private final ItemRegistry itemRegistry;
    private final PickUpOutputBoundary presenter;

    public PickUpInteractor(PickUpUserDataAccessInterface userSession,
                            ItemRegistry itemRegistry,
                            PickUpOutputBoundary presenter) {
        this.userSession = userSession;
        this.itemRegistry = itemRegistry;
        this.presenter = presenter;
    }

    @Override
    public void execute(PickUpInputData inputData) {
        String itemId = inputData.itemId();

        // 1. Lookup item entity in ItemRegistry (not userSession)
        Item item = itemRegistry.getItemById(itemId);

        if (item == null) {
            return;
        }

        // 2. Fetch current active session user
        User user = userSession.getCurrentUser();
        if (user != null) {
            user.saveItem(item);
        }

        // 3. Pass data across output boundary to presenter
        PickUpOutputData outputData = new PickUpOutputData(item);
        presenter.prepareSuccessView(outputData);
    }
}