package student.server;
import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.GameConsole;
import student.adventure.SiebelCenter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.io.IOException;
public class MyService implements AdventureService {
    int instanceID = 0;
    private final Map<Integer, GameStatus> allGames = new HashMap<>();
    private GameConsole console;

    public Map<Integer, GameStatus> getAllGames() {
        return allGames;
    }
    public GameConsole getConsole() {
        return console;
    }

    /**
     * Resets the service to its initial state.
     */
    @Override
    public void reset() {
        System.out.println("started reseted");
        instanceID = 0;
        allGames.clear();
    }
    /**
     * Creates a new Adventure game and stores it.
     *
     * @return the id of the game.
     */
    @Override
    public int newGame() throws AdventureException {
        System.out.println("try create new game");
        try {
            File file = new File("/Users/song15101556787/Desktop/" +
                    "amazing-adventures-blakeUser/src/main/resources/siebel.json");
            SiebelCenter siebelCenter = new ObjectMapper().readValue(file, SiebelCenter.class);
            console = new GameConsole(siebelCenter);
            GameStatus newGame = new GameStatus(true, instanceID, console.getRoomMessage(),
                    console.getImageURL(), console.getVideoUrl(), console.getStartingState(),
                    console.getCommandOptions());
            allGames.put(instanceID, newGame);
        } catch (Exception e) {
            throw new AdventureException("New game create fail");
        }
        return instanceID++;
    }

    /**
     * Returns the state of the game instance associated with the given ID.
     *
     * @param id the instance id
     * @return the current state of the game
     */
    @Override
    public GameStatus getGame(int id) {

        System.out.println("getGame get.. \n");
    return allGames.get(id);
    }

    /**
     * Removes & destroys a game instance with the given ID.
     *
     * @param id the instance id
     * @return false if the instance could not be found and/or was not deleted
     */
    @Override
    public boolean destroyGame(int id) {
        System.out.println("start destroy...");
        if (!allGames.containsKey(id)) {
            return false;
        }
        allGames.remove(id);
            return true;
    }

    /**
     * Executes a command on the game instance with the given id, changing the game state if applicable.
     * @param id the instance id
     * @param command the issued command
     */
    @Override
    public void executeCommand(int id, Command command) {
        System.out.println("start executed...");
        GameStatus newGameStatus = console.processingClientCommand(allGames.get(id), command);
        allGames.replace(id, newGameStatus);
    }

    /**
     * Returns a sorted leaderboard of player "high" scores.
     *
     * @return a sorted map of player names to scores
     */
    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
