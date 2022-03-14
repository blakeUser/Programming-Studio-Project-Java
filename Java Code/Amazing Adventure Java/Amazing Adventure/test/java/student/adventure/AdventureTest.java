package student.adventure;
import static org.junit.Assert.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import student.server.AdventureException;
import student.server.AdventureService;
import student.server.Command;
import student.server.MyService;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
public class AdventureTest {

    private GameConsole newGame;
    private SiebelCenter siebelCenter;

    @Before
    public void setUp() throws IOException {
        // This is run before every test.
        File file = new File("/Users/song15101556787/Desktop/" +
                "amazing-adventures-blakeUser/src/main/resources/siebel.json");
        siebelCenter = new ObjectMapper().readValue(file, SiebelCenter.class);
        newGame = new GameConsole(siebelCenter);

    }

    @Test
    public void sanityCheck() {
        assert(newGame != null);
    }

    /* Sanity Check of Map and dataset */

    @Test
    public void dataSetCheckingStartRoom() {
        assert(siebelCenter.getStartingRoom().equals("MatthewsStreet"));
    }


    @Test
    public void dataSetCheckingEndingRoom() {
        assert(siebelCenter.getEndingRoom().equals("Siebel1314"));
    }

    @Test
    public void checkMapSiebelCenter() {
        assert(newGame.theAdventureMap.get("MatthewsStreet").getName().equals("MatthewsStreet"));
    }

    @Test
    public void checkMapSiebelEntry() {
        assert(newGame.theAdventureMap.get("SiebelEntry").getDirections().get(0).
                getDirectionName().equals("West"));
    }

    @Test
    public void checkMapAcmOffice() {
        assert(newGame.theAdventureMap.get("AcmOffice").getDirections().get(0).
                getRoom().equals("SiebelEntry"));
    }

    @Test
    public void checkMapSiebelNorthHallway() {
        assert(newGame.theAdventureMap.get("SiebelNorthHallway").getDirections().get(0).
                getDirectionName().equals("South"));
    }

    @Test
    public void checkMapSiebel1112() {
        assert(newGame.theAdventureMap.get("Siebel1112").getItems().get(1).
                equals("money"));
    }


    /* Game Engine Test */

    @Test
    public void checkQuit() { //Need to check for input from user - if user types in quit or exit.
        // This doesn't actually test if your logic for ending the game works, just that the game can end.
        newGame.quitGame();
        assert(!newGame.getState());
    }

    @Test
    public void checkInvalidStepMatthewsStreet() {
        newGame.takeActions("go left");
        assert(newGame.getPlayer().getCurrentPlace().getName().equals("MatthewsStreet"));
    }

    @Test
    public void checkValidStepSiebelEntry() {
        newGame.takeActions("go east");
        assert(newGame.getPlayer().getCurrentPlace().getName().equals("SiebelEntry"));
    }

    @Test
    public void theGameCanWin() {
        newGame.takeActions("go east");
        newGame.takeActions("go east");
        newGame.takeActions("go south");
        newGame.takeActions("somethingRandom");
        assert(newGame.getPlayer().getCurrentPlace().getName().equals("Siebel1314") &&
                !newGame.getState());
    }

    @Test
    public void testExamine() {
        newGame.examinePoint();
        assert(newGame.getPlayer().getCurrentPlace().getName().equals("MatthewsStreet"));
    }

    /* Take Item Check */

    @Test
    public void takeItemWhenItExistsInBeginging() {
        newGame.takeItem("take coin"); //Not a valid command
        assert(!siebelCenter.getRooms().get(0).getItems().contains("coin"));
    }
    @Test
    public void takeItemWhenItExistsInSiebelEntry() {
        newGame.moveAround("go east");
        newGame.takeItem("take tv");
        assert(!siebelCenter.getRooms().get(1).getItems().contains("tv"));
    }
    @Test
    public void takeItemWhenItDoesNotExist() {
        newGame.takeItem("take  tv");
        assert(!newGame.getPlayer().getItemsBag().contains("tv"));
    }

    /* Drop Item and Test User Friendly comman */

    @Test
    public void dropItemTakenFromAnotherRoom() {
        newGame.takeActions("TAKE  COIN");
        newGame.takeActions("go east");
        newGame.takeActions("drop coin");
        assert(newGame.getPlayer().getCurrentPlace().getItems().contains("coin"));
    }

    @Test
    public void dropItemTakenFromAnotherRoomCheckPlayerBag() {
        newGame.takeActions("taKe   coin");
        newGame.takeActions("go east");
        newGame.takeActions("drop   COIN ");
        assert(!newGame.getPlayer().getItemsBag().contains("coin"));
    }

    @Test
    public void dropItemExistInRoomAndDouble() {
        newGame.takeActions("DROP COIN");
        int counterCoin = 0;
        for (int i = 0; i < newGame.getPlayer().getCurrentPlace().getItems().size(); i++) {
            if (newGame.getPlayer().getCurrentPlace().getItems().get(i).equals("coin")) {
                counterCoin++;
            }
        }
        assert(counterCoin == 2);
    }

    /* Invalid Command */

    @Test
    public void dropItemInvalid() {
        newGame.takeActions("asdfasdfa");
        assert(!newGame.getPlayer().getCurrentPlace().getItems().contains("asdfasdfa"));
    }

    @Test
    public void moveAroundInvalid() {
        newGame.takeActions("asdsssdv");
        assert(newGame.getPlayer().getCurrentPlace().getName().equals("MatthewsStreet"));
    }

    /* Test Adventure Service */

    @Test
    public void testCreateNewService() throws IOException, AdventureException {
        MyService service = new MyService();
        service.newGame();
        assert(service.getAllGames().containsKey(0));
    }

    @Test
    public void testGetNewGamesWork() throws IOException, AdventureException {
        MyService service = new MyService();
        service.newGame();
        assert(service.getGame(0) != null);
    }

    @Test
    public void testDestroySuccessful() throws IOException, AdventureException {
        MyService service = new MyService();
        service.newGame();
        service.destroyGame(0);
        assert(!service.getAllGames().containsKey(0));
    }

    @Test
    public void testReset() throws IOException, AdventureException {
        MyService service = new MyService();
        service.newGame();
        service.reset();
        assert(service.getAllGames().isEmpty());
    }

    @Test
    public void testValidCommandPlayerCanMove() throws IOException, AdventureException {
        MyService service = new MyService();
        Command goGommand = new Command("go", "east");
        service.newGame();
        service.executeCommand(0, goGommand);
        System.out.println(service.getConsole().getPlayer().getCurrentPlace().getName());
        assert(service.getAllGames().get(0).getState().getCurrentRoom().getName().equals("SiebelEntry"));
    }

    @Test
    public void testValidCommandPlayerCanPick() throws IOException, AdventureException {
        MyService service = new MyService();
        service.newGame();
        Command goGommand = new Command("take ", "coin");
        service.executeCommand(0, goGommand);
        assert(!service.getAllGames().get(0).getState().getCurrentRoom().getItems().contains("coin"));
    }

    @Test
    public void testValidCommandPlayerCanDROP() throws IOException, AdventureException {
        MyService service = new MyService();
        service.newGame();
        Command goGommand = new Command("drop ", "coin");
        service.executeCommand(0, goGommand);
        assert(service.getAllGames().get(0).getState().getCurrentRoom().getItems().contains("coin"));
    }
}