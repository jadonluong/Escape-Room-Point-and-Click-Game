package application.use_cases.GamePlay.ActionTrigger;

import application.game_registry.ItemRegistry;
import domain.entities.User.User;

public interface ActionTriggerDataAccessInterface extends ItemRegistry{
    public User getCurrentUser();
}
