package init;
import java.util.Scanner;
import java.io.* ;
import games.tictactoe.UIPrompt;
import games.tictactoe.TicTacToe;
import games.tictactoe.Player;

/**
 * Initialization class contains 2 static variables:
 * <ul>
 *  <li> game {@link TicTacToe} </li>
 *  <li> input {@link Scanner} </li>
 * </ul>
 * After initialization starts infinite loop
 * executing user's commands via command line.
 * 
 * @author Yaroslav Shestakov
 * 
 */
public class Main {
    public static TicTacToe game ;
    public static Scanner input = new Scanner(System.in) ;
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in) ;
        UIPrompt.printHello();
        
        while (true){
            String command = input.nextLine().trim().toLowerCase() ;
            if (command.matches("^help$")){
                UIPrompt.printHelp();
            } else if (command.matches("^new$")){
                Main.game = new TicTacToe(new Player("O"), new Player("X"), 10, 10) ;
                UIPrompt.printGrid(Main.game);
            } else if (command.matches("^pos[:]*[\\s]+[a-z][0-9]{1,2}$")){
                if (Main.game != null){
                    String[] coords = command.split("\\s+") ;
                    if (Main.game.move(coords[1].substring(0, 1), coords[1].substring(1))){
                        UIPrompt.printGrid(Main.game) ;
                        if (Main.game.getWinner() != null)
                            UIPrompt.printStats(game);
                    } else {
                        UIPrompt.printError(Main.game.getLastError()) ;
                    }
                } else
                    System.out.println("You must create or load game first.") ;
            } else if (command.matches("^>$")) {
                Main.saveGame() ;
            } else if (command.matches("^<$")){
                Main.loadGame();
            } else if (command.matches("^stats$")){
                if (Main.game != null)
                    UIPrompt.printStats(Main.game);
                else
                    System.out.println("The game has not been started") ;
            } else if (command.matches("^test$")){
                if (Main.game != null){
                    Main.game.loadTest() ;
                    UIPrompt.printGrid(Main.game);
                } else
                    System.out.println("The game has not been started") ;
            } else if (command.matches("^exit$")){
                System.out.println("Bye bye, we wait for you again :)") ;
                System.exit(0) ;
            } else {
                System.out.println("Unknown command. Type 'help' to see commands") ;
            }
        }
    }
    
    private static void loadGame(){
        try {
            FileInputStream fileStream = new FileInputStream("input-files/save.txt");
            ObjectInputStream os = new ObjectInputStream(fileStream);
            Main.game = (TicTacToe) os.readObject() ;
            os.close();
            System.out.println("Game loaded") ;
            UIPrompt.printGrid(Main.game);
        } catch (Exception e) {
            System.out.println("Could not load your game. " + e.getMessage());
            return;
        }
    }
    
    private static void saveGame(){
        if (Main.game !=  null){
            try {
                FileOutputStream fileStream = new FileOutputStream("input-files/Save.txt");
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(Main.game);
                os.close();
                System.out.println("Your game is saved.") ;
            } catch (Exception e){
                System.out.println("Cannot save your game. " + e.getMessage()) ;
                return;
            }
        }
    }
}
