package domain.UseCases.GamePlay.ActionTrigger;

import application.use_cases.GamePlay.ActionTrigger.ActionTriggerDataAccessInterface;
import application.use_cases.GamePlay.ActionTrigger.ActionTriggerInteractor;
import application.use_cases.GamePlay.UserDataAccessInterface;
import application.use_cases.Hint.GetHint.GetHintInputBoundary;
import application.use_cases.Hint.GetHint.GetHintInputData;
import application.use_cases.Interactable.Zoom.*;
import application.use_cases.Item.PickUp.PickUpInputBoundary;
import application.use_cases.Item.PickUp.PickUpInputData;
import domain.entities.Interactable.Interactable;
import domain.entities.Item.Item;
import domain.entities.Puzzle.Puzzle;
import domain.entities.User.User;
import interface_adapter.Interactable.Zoom.ZoomPresenter;
import org.junit.jupiter.api.BeforeEach;

public class ActionTriggerTest {

    private ZoomInputBoundary zoomInteractor;
    private PickUpInputBoundary pickUpInteractor;
    private GetHintInputBoundary getHintInteractor;
    private TestUserDataAccess dataAccess;

    private interactor testInteracter;

    private ActionTriggerInteractor actionTriggerInteractor;

    private static class interactor implements ZoomInputBoundary,
    PickUpInputBoundary, GetHintInputBoundary {

        @Override
        public void zoomIn(ZoomInputData inputData) {
        }
        @Override
        public void zoomOut() {
        }
        @Override
        public void execute(GetHintInputData getHintInputData) {
        }
        @Override
        public void execute(PickUpInputData inputData) {
        }
    }

    private static class TestUserDataAccess implements ActionTriggerDataAccessInterface {
        private User currentUser;

        public void setCurrentUser(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        @Override
        public Item getItemById(String itemID) {
            return null;
        }
    }

    @BeforeEach

    void setUp() {
        testInteracter = new interactor();
        zoomInteractor = testInteracter;
        pickUpInteractor = testInteracter;
        getHintInteractor = testInteracter;
        dataAccess = testInteracter;

        actionTriggerInteractor = new ActionTriggerInteractor(
                testInteracter,
                pickUpInteractor,
                getHintInteractor,
                dataAccess
        );
    }
}
