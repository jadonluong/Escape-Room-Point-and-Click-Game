package domain.entities.Hint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonHintTest {
    private Hint hint;
    private List<String> messages;

    @BeforeEach
    public void setUp() {
        List<String> messages = new ArrayList<>();
        messages.add("Hahaha!");
        messages.add("What? Second Hint? Man, you really are slipping!");
        this.messages = messages;
        Hint hint = new CommonHint("hint1", "fake_path", messages);
        this.hint = hint;
    }

    @Test
    void testGetObjectID() {
        assertEquals("hint1", hint.getObjectID());
    }

    @Test
    void testGetHintMessageCount() {
        assertEquals(messages.size(), hint.getHintMessageCount());
    }

    @Test
    void testGetHintMessageForRequestCount() {
        String hint_msg_1 = hint.getHintMessageForRequestCount(0);
        String msg_1 = messages.get(0);
        assertEquals(msg_1, hint_msg_1);

        String hint_msg_2 = hint.getHintMessageForRequestCount(1);
        String msg_2 = messages.get(1);
        assertEquals(msg_2, hint_msg_2);
    }

    @Test
    void testGetImagePath() {
        assertEquals("fake_path", hint.getImagePath());
    }
}
