package interface_adapter.GamePlay.SelectMode;

public class SelectModeState {
    private String roomId;
    private String errorMessage;

    public void  setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getRoomId() {
        return roomId;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
