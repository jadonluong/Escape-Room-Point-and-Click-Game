package domain.entities.Hint;

import domain.entities.Room.Position;

import java.util.List;

/**
 * Factory for creating Hint objects.
 */
public class CommonHintFactory implements HintFactory{

    @Override
    public Hint createHint(String ID, String imagePath, List<String> messages, Position position) {
        return new CommonHint(ID, imagePath, messages, position);
    }
}
