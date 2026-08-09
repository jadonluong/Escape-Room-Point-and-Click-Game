package domain.entities.hint;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HintFactoryTest {

    @Test
    public void testHintFactory() {
        HintFactory hintFactory = new CommonHintFactory();
        List<String> messages = new ArrayList<>();
        messages.add("Gotcha!");
        Hint hint = hintFactory.createHint("hint1", "fake_path", messages);

        assertNotNull(hint, "Factory should not return null.");
        assertInstanceOf(Hint.class, hint, "Factory should return a concrete Hint instance.");
    }

}
