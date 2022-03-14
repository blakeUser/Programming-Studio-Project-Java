package student.adventure;
import student.server.AdventureState;
import student.server.Command;
import student.server.GameStatus;
import java.util.*;
public class GameConsole {
    public Map<String, Room> theAdventureMap = new HashMap<>();
    private boolean gameIsProcessing = true; //camel case
    private Player player;
    private final String GO = "go";
    private final String DROP = "drop";
    private final String TAKE = "take";
    SiebelCenter siebelCenter;
    private final int MIN_LEN = 7;
    private String roomMessage;
    private AdventureState startingState;
    public boolean getState() {
        return gameIsProcessing;
    }
    public Player getPlayer() {
        return player;
    }
    List<Room> traversedRoom = new LinkedList<>();
    private final String END_POINT;

    public AdventureState getStartingState() {
        if (startingState == null) {
            startingState = new AdventureState(theAdventureMap.get(siebelCenter.getStartingRoom()));
        }
        return startingState;
    }

    public String getRoomMessage() {
        return printInitialGameMessage();
    }

    public String getImageURL() {
        return getPlayer().getCurrentPlace().getPicUrl();
    }

    private String subStringHelperFunction(String templateString, String toTrim) {
        return toTrim.substring(templateString.length()).trim();
    }

    public String getVideoUrl() {
        return getPlayer().getCurrentPlace().getVideo();
    }

    /**
     Description:Initialize the Map from the Data Set
     */
    public GameConsole(SiebelCenter siebelCenter) {
        List<Room> roomList = siebelCenter.getRooms();
        for (Room room : roomList) {
            theAdventureMap.put(room.getName(), room);
        }
        this.siebelCenter = siebelCenter;
        player = new Player(theAdventureMap.get(siebelCenter.getStartingRoom()));
        END_POINT = siebelCenter.getEndingRoom();
    }

    /**
     *  Description: Set the GAME_IS_PROCESSING to false to stop the Game.
     */
    public String quitGame() {
        gameIsProcessing = false;
        return "I will see you around then. ";
    }

    /**
     *  Description: At the current place, get a list of all rooms that I have been through
     */
    public List<Room> getTraversedRoom() {
        return traversedRoom;
    }

    /**
     Description: set the player to move back and forth
     @param command The command of move with directions, get from users.
     */
    public String moveAround(String command) {
        String theTrimmedCommand = subStringHelperFunction(GO, command);
        for (int i = 0; i < player.getCurrentPlace().getDirections().size(); i++) {
            if (player.getCurrentPlace().getDirections().get(i).getDirectionName().
                    toLowerCase(Locale.ROOT).equals(theTrimmedCommand)) {
                player.setCurrentPlace(theAdventureMap.
                        get(player.getCurrentPlace().getDirections().get(i).getRoom()));
                traversedRoom.add(player.getCurrentPlace());
                System.out.println(examinePoint());
                if (player.getCurrentPlace().getName().equals(END_POINT)) {
                    return quitGame();
                }
                return "Move successfully ";
            }
        }
        return "I can't go " + theTrimmedCommand;
    }

    /**
     Description: examine the current place
     */
    public String examinePoint() {
        StringBuilder toReturnInExaminePoint = new StringBuilder(player.getCurrentPlace().getDescription() + "\n");
        for (int i = 0; i < player.getCurrentPlace().getItems().size(); i++) {
            toReturnInExaminePoint.append("In here, you can get ").append(player.getCurrentPlace().
                    getItems().get(i)).append("\n");
        }
        for (int i = 0; i < player.getCurrentPlace().getDirections().size(); i++) {
           toReturnInExaminePoint.append("From here, you can go ").append(player.getCurrentPlace().
                   getDirections().get(i).
                   getDirectionName()).append("\n");
        }
        return toReturnInExaminePoint.toString();
    }

    /**
     * Description: Drop the Item in the current place
     * It can drop items to the floor if you have item,
     * and copy the item to the floor if the room already have that item.
     * @param command A trimmed command get from users.
     */
    public String dropItem(String command) {
        String theTrimmedCommand = subStringHelperFunction(DROP, command);
        System.out.println("the Trimmed Command in drop is " + theTrimmedCommand);
        if (player.getCurrentPlace().getItems().contains(theTrimmedCommand)) {
            player.getCurrentPlace().getItems().add(theTrimmedCommand);
            return "Item copied ";
        }
        if (!player.getItemsBag().contains(theTrimmedCommand)) {
            return ("You don't have " + theTrimmedCommand);
        } else if (player.getItemsBag().contains(theTrimmedCommand)) {
            player.getCurrentPlace().getItems().add(theTrimmedCommand);
            player.getItemsBag().remove(theTrimmedCommand);
            return ("The item is removed. ");
        }
        return "";
    }

    /**
     * Description: Take an existed Item in the current place.
     * If there is no such an item, no taking anything.
     * If there is, take it into the backpack.
     * @param command A trimmed command get from users.
     */
    public String takeItem(String command) {
        String theTrimmedCommand = subStringHelperFunction(TAKE, command);
        if (!player.getCurrentPlace().getItems().contains(theTrimmedCommand)) {
            return "There is no item " + theTrimmedCommand + " in the room";
        } else {
            player.getItemsBag().add(theTrimmedCommand);
            player.getCurrentPlace().getItems().remove(theTrimmedCommand);
            return "Item add in your bag ! ";
        }
    }

    /**
     * Description: The Core part of the code.
     * It does two jobs:
     * 1. It trims the command get from user.
     * 2. Based on the content of the command, it brings the command to
     *    other functions for further processing works.
     * @param command A raw command get from users.
     */
    public String takeActions(String command) {
        String trimmedCommand = command.trim().toLowerCase(Locale.ROOT);
        if (trimmedCommand.equals("quit") || trimmedCommand.equals("exit")) {
            return quitGame();
        } else if (("go").equals((trimmedCommand).substring(0, ("go").length()))) {
            return moveAround(trimmedCommand);
        } else if (("examine").equals(trimmedCommand.substring(0, ("examine").length()))) {
           return examinePoint();
        } else if (("drop").equals(trimmedCommand.substring(0,("drop").length()))) {
            return dropItem(trimmedCommand);
        } else if (("take").equals(trimmedCommand.substring(0, ("take").length()))) {
            return takeItem(trimmedCommand);
        } else {
            return "Invalid command: " + command;
        }
    }

    /**
     * Description: Print the initial Game message to the client at
     * the beginning.
     */
    private String printInitialGameMessage() {
        return "Welcome to the Siebel Center. \n" +
        "To win this game, you have to get to Room " +
                 "1667 and do a CS 126 code review. \n" +
        "In this game, you can do couple of things "
                + "to have fun. \n" +
        "You can go to the next available rooms. \n" +
        "You can pick up / drop the things you see in" +
                 " rooms. \n" +
        "If you want to quit the game, just type 'quit' \n" +
        "Lets go!' \n";
    }

    /**
     * Description: Activate the terminal and start the game.
     * Enable users to input their command at the terminal.
     * While the GAME_IS_PROCESSING equals is true, the game will keep
     * processing. If GAME_IS_PROCESSING is set to false, the terminal
     * will be close.
     */
    public void startGame() {
        Scanner input = new Scanner(System.in);
        player.setCurrentPlace(theAdventureMap.get(siebelCenter.getStartingRoom()));
        if (traversedRoom.isEmpty()) {
            traversedRoom.add(player.getCurrentPlace());
        }
        examinePoint();
        System.out.println(printInitialGameMessage());
        while (gameIsProcessing) {
            System.out.print(">> ");
            String clientInput = input.nextLine();
            takeActions(clientInput);
        }
    }

    /**
     * Description: Get all of the possible command options at the current room.
     * @return a Map that has String as the key and the List of possible command as value.
     */
    public Map<String, List<String>> getCommandOptions() {
        Map<String, List<String>> allCommandOptions = new HashMap<>();
        allCommandOptions.put("go", directionList(getPlayer().getCurrentPlace().getDirections()));
        List<String> emptyList = new ArrayList<>();
        allCommandOptions.put("examine", emptyList);
        allCommandOptions.put("drop", getPlayer().getItemsBag());
        allCommandOptions.put("take", getPlayer().getCurrentPlace().getItems());
        allCommandOptions.put("quit", emptyList);
        return allCommandOptions;
    }

    /**
     * Description: From Adventure Resources, we get client's command and use the command to update the
     * current game.
     * @param current is the current game status. command is the client's command.
     * @return the updated status
     */
    public GameStatus processingClientCommand(GameStatus current, Command command) {
        String outPutMessageOfReturnAction = takeActions(command.getCommandName() + "" +
                command.getCommandValue());
        AdventureState newState = new AdventureState(player.getCurrentPlace());
        return new GameStatus(true, current.getId(), outPutMessageOfReturnAction, getImageURL(),
                getVideoUrl(), newState, getCommandOptions());
    }

    /**
     * Description: Helper function that transfer all directions into a list of String
     * @param directions a list of directions get from the current room
     * @return the updated status
     */
    List<String> directionList(List<Directions> directions) {
        List<String> theListToReturn = new ArrayList<>();
        for (Directions direction : directions) {
            theListToReturn.add(direction.getDirectionName());
        }
        return theListToReturn;
    }
}