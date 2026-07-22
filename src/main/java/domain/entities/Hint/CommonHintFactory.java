package domain.entities.Hint;

import java.util.List;

/**
 * Factory for creating Hint objects.
 */
public class CommonHintFactory implements HintFactory{

    @Override
    public Hint createHint(String ID, List<String> messages) {
        return new CommonHint(ID, messages);
    }
}
