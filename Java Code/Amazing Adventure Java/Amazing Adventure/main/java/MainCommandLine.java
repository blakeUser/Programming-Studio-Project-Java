import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.GameConsole;
import student.adventure.SiebelCenter;
import java.io.File;
import java.io.IOException;

public class MainCommandLine {
    public static void main(String[] args) throws IOException {
        File file = new File("" + "/Users/song15101556787/Desktop/" +
            "amazing-adventures-blakeUser/src/main/resources/siebel.json");
        SiebelCenter siebelCenter = new ObjectMapper().readValue(file, SiebelCenter.class);
        GameConsole newGame = new GameConsole(siebelCenter);
        newGame.startGame();
    }
}
