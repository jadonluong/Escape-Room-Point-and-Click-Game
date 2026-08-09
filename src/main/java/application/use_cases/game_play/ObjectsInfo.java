package application.use_cases.game_play;

import domain.entities.room.Position;

/**
 * Contains the information required to display an object in a room.
 *
 * @param imgPath the path to the object's image
 * @param position the position of the object in the room
 * @param type the type of object being displayed
 */
public record ObjectsInfo(String imgPath, Position position, String type) {
}
