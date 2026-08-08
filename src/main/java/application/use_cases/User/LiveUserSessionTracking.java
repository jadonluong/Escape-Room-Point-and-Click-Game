package application.use_cases.User;

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
        InteractUserDataAccessInterface,
        EnterExitUserDataAccessInterface,
        SolveUserDataAccessInterface,
        PickUpUserDataAccessInterface,
        SelectItemUserDataAccessInterface {

    private User currentUser;

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    // @Override
    // public Item getItemById(String id) {
    //     if (id == null || id.isEmpty()) {
    //         return null;
    //     }

        // 1. Extract raw ID and display name (handles "itemId:itemName" formats)
    //    String cleanId = id.contains(":") ? id.split(":")[0] : id;
    //    String displayName = id.contains(":") ? id.split(":")[1] : cleanId;

        // 2. Rebuild and return the Item entity
    //    ItemFactory itemFactory = new CommonItemFactory();
    //    return itemFactory.restoreItem(
    //            cleanId,
    //            displayName,
    //            "",                  // description
    //            true,                // craftable
    //            "assets/" + cleanId  // image path
    //    );
    // }
}
