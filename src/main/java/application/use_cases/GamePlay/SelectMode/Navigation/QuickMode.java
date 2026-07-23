package application.use_cases.GamePlay.SelectMode.Navigation;

import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsInputBoundary;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsInputData;
import application.use_cases.GamePlay.QuickPlay.BrowseRooms.BrowseRoomsOutputBoundary;
import application.use_cases.GamePlay.SelectMode.SelectModeOutputBoundary;

public class QuickMode implements ModeNavigation {

    private final BrowseRoomsInputBoundary browseRoomsUseCase;

    public QuickMode(BrowseRoomsInputBoundary browseRoomUseCase) {
        this.browseRoomsUseCase = browseRoomUseCase;
    }
    @Override
    public void navigate(BrowseRoomsOutputBoundary presenter) {
        //Wrap the data
        BrowseRoomsInputData inputData = new BrowseRoomsInputData("quick");

        // 2. Delegate execution to the use case, handing it the select mode presenter
        // to handle the output when it's done!
        browseRoomsUseCase.execute(inputData, presenter);
    }
}
