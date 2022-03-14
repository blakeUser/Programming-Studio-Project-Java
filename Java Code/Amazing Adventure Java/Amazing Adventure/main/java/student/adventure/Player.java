package student.adventure;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private Room currentPlace;
    private List<String> itemsBag;
    public Player(Room room) {
        currentPlace = room;
        itemsBag = new ArrayList<String>();;
    }
    public Room getCurrentPlace() {
        return currentPlace;
    }
    public List<String> getItemsBag() {
        return itemsBag;
    }
    public void setCurrentPlace(Room currentPlace) {
        this.currentPlace = currentPlace;
    }
}

