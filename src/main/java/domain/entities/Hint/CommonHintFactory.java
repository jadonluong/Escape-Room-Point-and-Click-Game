package domain.entities.Hint;

import java.util.UUID;

/**
 * Factory for creating Hint objects.
 */
public class CommonHintFactory implements HintFactory{

    @Override
    public Hint createHint(UUID ID, String message) {
        return new CommonHint(ID, message);
    }
}
