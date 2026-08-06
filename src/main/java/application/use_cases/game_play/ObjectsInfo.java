package application.use_cases.game_play;

import domain.entities.Room.Position;

public record ObjectsInfo(String imgPath, Position position, String type) {
}
