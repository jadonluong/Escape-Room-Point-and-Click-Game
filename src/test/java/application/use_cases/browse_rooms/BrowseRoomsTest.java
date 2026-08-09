package application.use_cases.browse_rooms;
import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsDataAccessInterface;

import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsInteractor;
import application.use_cases.game_play.quick_play.browse_rooms.BrowseRoomsOutputData;
import domain.entities.room.CommonRoom;
import domain.entities.room.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class BrowseRoomsTest {

    private TestPresenter testPresenter;
    private TestDataAccess testDataAccess;
    private BrowseRoomsInteractor browseRoomsInteractor;
    // Fake dataAccess
    private static class TestDataAccess implements BrowseRoomsDataAccessInterface {
        private final List<Room> rooms = new ArrayList<>();

        public void addRoom(String id, Room room) {
            rooms.add(room);
        }

        @Override
        public List<Room> getRoomsForQuickMode() {
            return rooms;
        }
    }

    @BeforeEach
    void setUp() {
        testPresenter = new TestPresenter();
        testDataAccess = new TestDataAccess();
        browseRoomsInteractor = new BrowseRoomsInteractor(testDataAccess, testPresenter);

        for(int i = 0; i < 5; i++) {
            String id = "room" + i;
            Room room = new CommonRoom(id, "Room", "/img/story_room.png",
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
            testDataAccess.addRoom(id, room);
        }
    }
    @Test
    void testSuccessfulStartPreparesBrowseRoomsView() {
        browseRoomsInteractor.execute();

        assertNull(testPresenter.getErrorMessage());

        BrowseRoomsOutputData outputData = testPresenter.getSuccessData();

        assertEquals(5, outputData.getInfo().size());

        assertEquals("/img/story_room.png", outputData.getInfo().get("room0").imagePath());

        assertEquals("Room", outputData.getInfo().get("room1").description());
    }

    @Test
    void testExecuteWhenNoRoomsExistPreparesFailView() {
        // Create a data access with no rooms
        TestDataAccess emptyDataAccess = new TestDataAccess();
        BrowseRoomsInteractor emptyInteractor = new BrowseRoomsInteractor(emptyDataAccess, testPresenter);

        emptyInteractor.execute();

        assertEquals("No rooms found", testPresenter.getErrorMessage());
        assertNull(testPresenter.getSuccessData());
    }


    }

