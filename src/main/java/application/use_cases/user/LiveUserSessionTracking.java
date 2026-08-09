package application.use_cases.user;

import application.use_cases.game_play.UserDataAccessInterface;
import application.use_cases.hint.get_hint.GetHintUserDataAccessInterface;
import application.use_cases.interactable.interact.InteractUserDataAccessInterface;
import application.use_cases.item.pick_up.PickUpUserDataAccessInterface;
import application.use_cases.item.select_item.SelectItemUserDataAccessInterface;
import application.use_cases.puzzle.enter_exit.EnterExitUserDataAccessInterface;
import application.use_cases.puzzle.solve.SolveUserDataAccessInterface;
import application.use_cases.user.login.LoginUserSessionDataAccessInterface;
import application.use_cases.user.logout.LogoutUserDataAccessInterface;
import application.use_cases.user.save_progress.SaveProgressUserSessionDataAccessInterface;
import domain.entities.user.User;

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
