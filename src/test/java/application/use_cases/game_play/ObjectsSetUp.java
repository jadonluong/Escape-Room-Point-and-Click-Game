package application.use_cases.game_play;

import domain.entities.hint.CommonHint;
import domain.entities.hint.Hint;
import domain.entities.interactable.CommonInteractable;
import domain.entities.interactable.Interactable;
import domain.entities.item.CommonItem;
import domain.entities.item.Item;
import domain.entities.room.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectsSetUp {

    private Interactable interactable;
    private Item item;
    private Hint hint;

    public List<Item> itemSetUp(){
        List<Item> items = new ArrayList<>();
        Item key = new CommonItem("key1",
                "/images/ui/buttons/QuickButton.png",
                "description",
                true,
                "/images/ui/buttons/QuickButton.png");

        items.add(key);

        item = key;

        return items;
    }

    public List<Interactable> interactableSetUp(){

        List<Interactable> interactables = new ArrayList<>();

        // Setup dummy domain objects
        Interactable door = new CommonInteractable(
                "door1",
                "Wooden Door",
                "A sturdy wooden door.",
                "/images/ui/buttons/QuickButton.png",
                "Unlocked Door",
                "The door is now wide open.",
                "/images/ui/buttons/TutorialButton.png",
                false,
                true,
                true,
                "brass_key",
                "reward_coin",
                "puzzle_01",
                "room_02",
                "You used the key and unlocked the door!"
        );
        interactables.add(door);

        interactable = door;

        return interactables;
    }

    public List<Hint> hintSetUp(){
        List<Hint> hints = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        messages.add("message");
        messages.add("message, message");
        Hint hint = new CommonHint("hint1",
                "/images/ui/buttons/QuickButton.png",
                messages);
        hints.add(hint);

        this.hint = hint;
        return hints;
    }

    public Map<String, Position> positionSetUp(){
        Map<String, Position> positions = new HashMap<>() {
        };
        Position pos1 = new Position(10.0, 20.0);
        Position pos2 = new Position(30.0, 40.0);
        Position pos3 = new Position(50.0, 60.0);
        positions.put(interactable.getId(),pos1);
        positions.put(item.getId(),pos2);
        positions.put(hint.getObjectID(), pos3);

        return positions;
    }
}
