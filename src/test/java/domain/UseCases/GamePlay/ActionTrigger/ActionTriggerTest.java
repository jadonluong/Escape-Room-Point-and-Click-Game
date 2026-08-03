package domain.UseCases.GamePlay.ActionTrigger;

import application.use_cases.GamePlay.ActionTrigger.ActionTriggerDataAccessInterface;
import application.use_cases.GamePlay.ActionTrigger.ActionTriggerInputData;
import application.use_cases.GamePlay.ActionTrigger.ActionTriggerInteractor;
import application.use_cases.GamePlay.UserDataAccessInterface;
import application.use_cases.Hint.GetHint.GetHintInputBoundary;
import application.use_cases.Hint.GetHint.GetHintInputData;
import application.use_cases.Interactable.Zoom.*;
import application.use_cases.Item.PickUp.PickUpInputBoundary;
import application.use_cases.Item.PickUp.PickUpInputData;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.CommonItem;
import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.User.CommonUserFactory;
import domain.entities.User.CommonUserFactoryClass;
import domain.entities.User.User;
import interface_adapter.Interactable.Zoom.ZoomPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTriggerTest {

    private TestUserDataAccess dataAccess;

    private interactor testInteracter;

    private ActionTriggerInteractor actionTriggerInteractor;

    // Dummies
    private User user;
    private Item item;

    private static class interactor implements ZoomInputBoundary,
            PickUpInputBoundary, GetHintInputBoundary {

        boolean zoomCalled;
        boolean pickUpCalled;
        boolean hintCalled;

        ZoomInputData zoomInputData;
        PickUpInputData pickUpInputData;
        GetHintInputData hintInputData;

        @Override
        public void zoomIn(ZoomInputData inputData) {
            zoomCalled = true;
            zoomInputData = inputData;
        }

        @Override
        public void zoomOut() {
        }

        @Override
        public void execute(GetHintInputData inputData) {
            hintCalled = true;
            hintInputData = inputData;
        }

        @Override
        public void execute(PickUpInputData inputData) {
            pickUpCalled = true;
            pickUpInputData = inputData;
        }
    }

    private static class TestUserDataAccess implements ActionTriggerDataAccessInterface {
        private User currentUser;
        private Item item;

        public void setCurrentUser(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        @Override
        public Item getItemById(String itemID) {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }


    }

    @BeforeEach
    void setUp() {
        testInteracter = new interactor();
        dataAccess = new TestUserDataAccess();

        actionTriggerInteractor = new ActionTriggerInteractor(
                testInteracter,
                testInteracter,
                testInteracter,
                dataAccess
        );

        CommonUserFactory factory = new CommonUserFactoryClass();
        String username = "test_user";
        String password = "secure_password";

        this.user = factory.createCommonUser(username, password);

        this.item =  new CommonItem("key1",
                "/images/ui/buttons/QuickButton.png",
                "description",
                true,
                "/images/ui/buttons/QuickButton.png");

        dataAccess.setCurrentUser(user);
        dataAccess.setItem(item);
    }

    @Test
    void testInteractableTrigger() {

        actionTriggerInteractor.execute(
                new ActionTriggerInputData("door1", "Interactable"));

        assertTrue(testInteracter.zoomCalled);
        assertFalse(testInteracter.pickUpCalled);
        assertFalse(testInteracter.hintCalled);

        assertEquals("door1", testInteracter.zoomInputData.getInteractableId());
    }

    @Test
    void testItemTrigger() {


        actionTriggerInteractor.execute(
                new ActionTriggerInputData(item.getId(), "item"));

        assertTrue(testInteracter.pickUpCalled);
        assertFalse(testInteracter.zoomCalled);
        assertFalse(testInteracter.hintCalled);

        assertEquals(item, testInteracter.pickUpInputData.getItem());
    }

    @Test
    void testItemNotFound() {

        dataAccess.setItem(null);

        actionTriggerInteractor.execute(
                new ActionTriggerInputData(item.getId(), "item"));

        assertFalse(testInteracter.zoomCalled);
        assertFalse(testInteracter.pickUpCalled);
        assertFalse(testInteracter.hintCalled);
    }

    @Test
    void testHintTrigger() {

        actionTriggerInteractor.execute(
                new ActionTriggerInputData("hint1", "Hint"));

        assertTrue(testInteracter.hintCalled);
        assertFalse(testInteracter.zoomCalled);
        assertFalse(testInteracter.pickUpCalled);

        assertEquals("hint1", testInteracter.hintInputData.getObjectID());
        assertEquals("test_user", testInteracter.hintInputData.getCurrentUserId());
    }

    @Test
    void testUnknownMode() {


        actionTriggerInteractor.execute(
                new ActionTriggerInputData("id", "Unknown"));

        assertFalse(testInteracter.zoomCalled);
        assertFalse(testInteracter.pickUpCalled);
        assertFalse(testInteracter.hintCalled);
    }
}