package application.use_cases.User;

import application.game_registry.ItemRegistry;
import application.use_cases.game_play.action_trigger.ActionTriggerDataAccessInterface;
import application.use_cases.game_play.UserDataAccessInterface;
import application.use_cases.Hint.GetHint.GetHintUserDataAccessInterface;
import application.use_cases.Interactable.Interact.InteractUserDataAccessInterface;
import application.use_cases.Item.PickUp.PickUpUserDataAccessInterface;
import application.use_cases.Item.SelectItem.SelectItemUserDataAccessInterface;
import application.use_cases.Puzzle.EnterExit.EnterExitUserDataAccessInterface;
import application.use_cases.Puzzle.Solve.SolveUserDataAccessInterface;
import application.use_cases.User.Login.LoginUserSessionDataAccessInterface;
import application.use_cases.User.Logout.LogoutUserDataAccessInterface;
import application.use_cases.User.SaveProgress.SaveProgressUserSessionDataAccessInterface;
import domain.entities.Item.Item;
import domain.entities.Item.CommonItemFactory;
import domain.entities.Item.ItemFactory;
import domain.entities.User.User;

public class LiveUserSessionTracking implements GetHintUserDataAccessInterface,
        LoginUserSessionDataAccessInterface,
        LogoutUserDataAccessInterface,
        SaveProgressUserSessionDataAccessInterface,
        UserDataAccessInterface,
        ActionTriggerDataAccessInterface,
        InteractUserDataAccessInterface,
        EnterExitUserDataAccessInterface,
        SolveUserDataAccessInterface,
        PickUpUserDataAccessInterface,
        SelectItemUserDataAccessInterface {

    private User currentUser;
    private final ItemRegistry itemRegistry;

    // added
    public LiveUserSessionTracking(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public Item getItemById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        // Clean any formatted "id:name" string down to raw ID
        String cleanId = id.contains(":") ? id.split(":")[0] : id;

        // Fetch the real domain Item entity loaded from items.json!
        return itemRegistry.getItemById(cleanId);
    }
}
