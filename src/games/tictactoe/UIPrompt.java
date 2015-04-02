package games.tictactoe;

import java.util.concurrent.TimeUnit;

/**
 * UIPrompt class provides command line primitive user interface.
 * It contains only public static methods.
 * 
 * @author Yaroslav Shestakov
 * @version 1.3
 */
public class UIPrompt {
    /**
     * Prints hello message 
     */
    public static void printHello(){
        String output = 
            "                                |v2.0|  \n" +    
            "+-------------------------------------+ \n" +
            "+--- Welcome to the TicTacToe game! --+ \n" +
            "+-------------------------------------+ \n " +
            "   Type 'help' to start and enjoy!   "
                    
        ;
        System.out.println(output) ;
    }
    
    /**
     * Prints help message
     */
    public static void printHelp(){
        String output = 
                "------------- COMMANDS --------------\n" +
                "NEW            Create a new game \n" +
                "POS <YX>       Make a move (current player). Example: 'pos: A1' or 'pos B9' \n" +
                ">              Save current game state \n" +
                "<              Load saved game state \n" + 
                "STATS          Display current game stats \n" +
                "TEST           Create grid to test win system \n" +
                "HELP           Display instructions \n" +
                "EXIT           Close the game \n" +
                "-------------------------------------\n"
        ;
        System.out.println(output);
    }
    
    /**
     * Prints grid and it's content. Also, reminds
     * what player must move now.
     * @param game Instance of TicTacToe
     */
    public static void printGrid(TicTacToe game){
        Tile[][] grid = game.grid ;
        int height = grid.length ;
        int width = grid[0].length ;
        char letter = 65 ;
        String output = new String() ;
        output += "  " + UIPrompt.getHelper("dec", width)  + "\n" ;
        output += "  " + UIPrompt.getHelper("prim", width) + "\n" ;
        output += "  " + UIPrompt.getHelper("-", width)    + "\n" ;
        
        for (int y = 0 ; y < grid.length ; y++){
            output += letter + "|" ;
            for (int x = 0 ; x < grid[y].length ; x++){
                output += grid[y][x].type ;
            }
            output += "|" + letter + "\n" ;
            letter++ ;
        }
        
        output += "  " + UIPrompt.getHelper("-", width)    + "\n" ;
        output += "  " + UIPrompt.getHelper("prim", width) + "\n" ;
        output += "  " + UIPrompt.getHelper("dec", width)  + "\n" ;
        
        output += "Player's " + game.players[game.currentPlayer].mark + " move" ;

        System.out.println(output) ;
    }
    
    /**
     * Assists in printing grid.
     * @param type type of the helper
     * @param width width of the grid
     * @return string with helpers
     */
    private static String getHelper(String type, int width){
        String output = "" ;
        if (type.equals("dec")){
            int dec = 1 ;
            for (int i = 0 ; i < width ; i++){
                output +=  ((((i+1)%10) != 0) ? " " : dec++) ;
            }
        } else if (type.equals("prim")){
            int prim = 1 ;
            for (int i = 0 ; i < width ; i++){ 
                output += prim ;
                prim = prim == 9 ? 0 : (++prim) ;
            }
        } else if (type.equals("-")){
            for (int i = 0 ; i < width ; i++){
                output +=  "-" ;
            }
        } 
        return output ;
    }
    
    /**
     * Displays some information about errors.
     * @see GameError
     * @param error 
     */
    public static void printError(Integer error){
        String output ;
        switch (error){
            case GameError.WRONG_POSITION:
                output = "Error: Non-existent tile." ;
                break ;
            case GameError.TILE_ALREADY_TAKEN:
                output = "Error: The tile is already taken" ;
                break ;
            case GameError.GAME_OVER:
                output = "Error: The game is over." ;
                break ;
            case GameError.CUSTOM:
            default:
                output = "Error: Unknown error" ;
                break ;
        }
        System.out.println(output) ;
    }
    
    /**
     * Prints statistics of the players and the game.
     * @param game Instance of TicTacToe
     */
    public static void printStats(TicTacToe game){
        Player player1 = game.players[0] ;
        Player player2 = game.players[1] ;
        
        int totalTiles = game.width *  game.height ;
        int usedTiles = player1.turnsUsed + player2.turnsUsed ;
        int filled = (int) (((float) usedTiles / (float) totalTiles)*100) ;
        String phase = (filled < 30) ? "Beginning" : (filled <= 70) ? "Mid" : "Ending" ;
        
        
        int totalTime = player1.timeSpent + player2.timeSpent ;
        
        String output = "\n" +
                "-------- WINNER ------ \n" +
                ((game.winner != null) ? "Player "+game.winner.mark : "[NONE]") + "\n" +
                "-------- STATS ------- \n" +
                "Player "+ player1.mark + " (" + player1.type + ") \n" +
                "   Avg time spent per turn: " + player1.timeSpent/((player1.turnsUsed != 0) ? player1.turnsUsed : 1) +  "s\n" +
                "   Put marks: " + player1.turnsUsed + "\n" +
                
                "Player "+ player2.mark + " (" + player2.type + ") \n" +
                "   Avg time spent per turn: " + player2.timeSpent/((player2.turnsUsed != 0) ? player2.turnsUsed : 1) +  "s\n" +
                "   Put marks: " + player2.turnsUsed + "\n" +
                "-------- TOTAL -------- \n" +
                "Game time: " +   TimeUnit.SECONDS.toHours(totalTime) + "hours " + 
                                        TimeUnit.SECONDS.toMinutes(totalTime%3600) + "minutes " + 
                                        TimeUnit.SECONDS.toSeconds(totalTime%60) + "seconds " + "\n" +
                "Field usage: " + filled + "% \n" +
                "Game phase: " + phase      
        ;       
        System.out.println(output) ;
    }
}
