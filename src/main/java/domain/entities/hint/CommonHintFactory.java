package domain.entities.hint;

import java.util.List;

/**
 * Factory for creating Hint objects.
 */
public class CommonHintFactory implements HintFactory {

    @Override
    public Hint createHint(String ID, String imagePath, List<String> messages) {
        return new CommonHint(ID, imagePath, messages);
    }
}
