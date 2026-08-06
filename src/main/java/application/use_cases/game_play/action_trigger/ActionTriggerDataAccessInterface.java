package application.use_cases.game_play.action_trigger;

import domain.entities.User.User;

public interface ActionTriggerDataAccessInterface {
    User getCurrentUser();
}
