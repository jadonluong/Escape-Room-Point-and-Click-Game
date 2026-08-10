package application.use_cases.registries;

import application.game_registry.InteractableRegistry;
import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InteractableRegistryTest {
    private TestInteractableRegistry testRegistry;
    private String validInteractableId1;
    private String validInteractableId2;
    private Interactable sampleInteractable1;
    private Interactable sampleInteractable2;

    private static class TestInteractableRegistry implements InteractableRegistry {
        private final Map<String, Interactable> registry = new HashMap<>();

        public void addInteractable(String id, Interactable interactable) {
            registry.put(id, interactable);
        }

        @Override
        public Interactable getInteractableById(String id) {
            return registry.get(id);
        }
    }

    @BeforeEach
    void setup() {
        testRegistry = new TestInteractableRegistry();

        validInteractableId1 = "chest_01";
        validInteractableId2 = "door_main";

        sampleInteractable1 = (Interactable) new CommonInteractable(validInteractableId1, "book",
                "A text book",
                "fake_default_sprite",
                "Open book",
                "An open text book",
                "fake_interacted_sprite",
                false, true, false,
                null, null, null, null,
                "Congrats");
        sampleInteractable2 = (Interactable) new CommonInteractable(validInteractableId2, "straw",
                "A straw",
                "fake_default_sprite",
                "clean straw",
                "A dirty straw",
                "fake_interacted_sprite",
                false, true, false,
                null, null, null, null,
                "Congrats");

        testRegistry.addInteractable(validInteractableId1, sampleInteractable1);
        testRegistry.addInteractable(validInteractableId2, sampleInteractable2);
    }

    @Test
    void testGetInteractableByIdSuccess() {
        Interactable retrievedInteractable = testRegistry.getInteractableById(validInteractableId1); //[cite: 3]

        assertNotNull(retrievedInteractable, "Interactable object should be retrieved successfully."); //[cite: 3]
        assertEquals(sampleInteractable1, retrievedInteractable, "Retrieved interactable should match the registered instance."); //[cite: 3]
    }

    @Test
    void testGetInteractableByIdNotFound() {
        Interactable retrievedInteractable = testRegistry.getInteractableById("non_existent_id"); //[cite: 3]

        assertNull(retrievedInteractable, "Retrieving an unregistered interactable ID should return null."); //[cite: 3]
    }

    @Test
    void testGetInteractableByIdMultipleEntries() {
        Interactable interactable1 = testRegistry.getInteractableById(validInteractableId1); //[cite: 3]
        Interactable interactable2 = testRegistry.getInteractableById(validInteractableId2); //[cite: 3]

        assertNotNull(interactable1);
        assertNotNull(interactable2);
        assertNotEquals(interactable1, interactable2, "Registry should distinguish between different interactable IDs.");
    }
}
