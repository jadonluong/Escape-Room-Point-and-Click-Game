package application.use_cases.Item.PickUp;

import domain.entities.Item.CommonItemFactory;
import domain.entities.Item.Item;
import domain.entities.Item.ItemFactory;
import domain.entities.User.User;

public interface PickUpUserDataAccessInterface {
    User getCurrentUser();

}