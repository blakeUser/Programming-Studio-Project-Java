package student.adventure;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {
    private String name;
    private String description;
    private String url = "https://api.thecatapi.com/v1/images/search";
    private String soundURL = "https://api.thecatapi.com/v1/images/search";
    private List<Directions> directions;
    private List<String> items;
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<Directions> getDirections() {
        return directions;
    }
    public List<String> getItems() { return items;};
    public String getPicUrl() { return url;}
    public String getVideo() { return soundURL;}
}
