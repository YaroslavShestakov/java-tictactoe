package games.tictactoe;

import java.io.Serializable;
import java.util.HashMap;
/**
 * TicTacToe is a the main game engine class. 
 * <li>All logical operations are collected here</li>
 * <li>Calling any of class constructors will automatically
 * start a new game.</li>
 * 
 * @author Yaroslav Shestakov
 * @version 2.0
 * @see Tile
 * @see Player
 * @see GameError
 * @see UIPrompt
 */
public class TicTacToe implements Serializable {
    protected final int width, height ;
    /**
     *  2D - Array containing Tile objects.
     *  Indeces are used as coordinates.
     *  @see Tile
     */
    protected Tile[][] grid ;
    /**
     * Array of players. Actually supported 2 players only.
     */
    protected Player[] players ;    
    protected Player winner = null ;
    /**
     * Index of current player in array {@link Player[]}
     */
    protected int currentPlayer = 0 ;
    /**
     * HashMap storing String keys and Integer coordinates of grid
     */
    private HashMap<String, Integer> yMap, xMap ;
    private Integer lastError = GameError.CUSTOM ;
    
    /**
     * The main class constructor
     * Performs actions:
     * <ul>
     *  <li>Assigns instance variables </li>
     *  <li>Creates grid </li>
     * </ul>
     * 
     * @param player1 First {@link Player}
     * @param player2 Second {@link Player}
     * @param width   Game width in units
     * @param height  Game height in units
     */
    public TicTacToe(Player player1, Player player2, int width, int height){
        this.players = new Player[]{ player1, player2 } ;
        this.players[currentPlayer].startCount();
        this.width = width ;
        this.height = height ;
        yMap = new HashMap() ;
        xMap = new HashMap() ;
        createGrid() ;
    }
    
    /**
     * Additional class constructor.
     * Provides default values:
     * <ul>
     *  <li> height = 20 </li>
     *  <li> width  = 20 </li>
     * </ul>
     * @param player1
     * @param player2 
     */
    public TicTacToe(Player player1, Player player2){
        this(player1, player2, 20, 20) ;
    }
    /**
     * Converts string coordinates into real array coordinates.
     * Searches in HashMap for the real values.
     * If real values are found, calls basic {@link move()} function.
     * 
     * @param y Row on the field (as letter)
     * @param x Column on the field (as number)
     * @return True on success and false on failure
     */
    public boolean move(String y, String x){
        Integer xPos = null;
        Integer yPos = null;
        try {
            xPos = Integer.valueOf(xMap.get(x)) ;
            yPos = Integer.valueOf(yMap.get(y.toUpperCase())) ;
        } catch (Exception e){
            this.lastError = GameError.WRONG_POSITION ; 
            return false ;
        }

        return move(yPos, xPos) ;
    }
    
    /**
     * Performs a move by current player. 
     * <ul>
     *  <li> Checks if the game is over </li>
     *  <li> Checks if the on grid is free </li>
     *  <li> Changes sign of a grid for current player's sign</li>
     *  <li> Checks for the win </li>
     * </ul>
     * 
     * On success switches player and changes type of the tile.
     * Sets lastError on failure.
     * 
     * @see GameError
     * 
     * @param y Row on the field
     * @param x Column on the field
     * @return True on success and false on failure
     */
    public boolean move(int y, int x){
        try {
            if (this.winner == null){
                if (grid[y][x].type.equals(Tile.FREE)){
                    grid[y][x].type = players[currentPlayer].mark ;
                    if (checkWin(y, x)){
                        this.winner = players[currentPlayer] ;
                        players[currentPlayer].stopCount();
                        return true ;
                    }
                    this.switchPlayer();
                    return true ;
                } else {
                    this.lastError = GameError.TILE_ALREADY_TAKEN ;
                }
            } else {
                this.lastError = GameError.GAME_OVER ;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()) ;
            this.lastError = GameError.WRONG_POSITION ;
            return false ;
        }
        return false ;
    }
    
    /**
     * Returns last error set by a public function.
     * 
     * @see GameError
     * @return Integer that corresponds to an error.
     */
    public Integer getLastError(){
        return this.lastError ;
    }
    
    /**
     * Returns instance of {@link Player} that won the game.
     * If the game is not finished or is drawn, returns null.
     * @return Winner player
     */
    public Player getWinner(){
        return this.winner ;
    }
    
    private void switchPlayer(){
        players[currentPlayer].turnsUsed++ ;
        players[currentPlayer].stopCount();
        this.currentPlayer = Math.abs(currentPlayer - 1) ;
        players[currentPlayer].startCount();
    }
    
    private void createGrid(){
        char yKey = 65 ;
        int  xKey = 1 ;
        this.grid = new Tile[this.height][this.width] ;
        for (int y = 0 ; y < this.height ; y++){
            yMap.put(String.valueOf(yKey), y) ;
            for (int x = 0 ; x < this.width ; x++){
                xMap.put(String.valueOf(xKey), x) ;
                this.grid[y][x] = new Tile() ;
                xKey++ ;
            }
            xKey = 1 ;
            yKey++ ;
        }
    }
    
    private Boolean checkWin(int y, int x){
        String type = grid[y][x].type ;

        int horizontal = 0, vertical = 0, dia = 0, antidia = 0 ;
        
        for (int dist = -4 ; dist <= 4 ; dist++){
            //Horizontal check
            if (x+dist >= 0 && x+dist < this.width - 1){
                if (type == grid[y][x+dist].type && type == grid[y][x+dist+1].type)
                    horizontal++ ;
                else if (horizontal < 4)
                    horizontal = 0 ;
                else
                    break ;
            }
            //Vertical check
            if (y+dist >= 0 && y+dist < this.height - 1){
                if (type == grid[y+dist][x].type && type == grid[y+dist+1][x].type)
                    vertical++ ;
                else if (vertical < 4)
                    vertical = 0 ;
                else
                    break ;
            }
            //Diagonal check
            if (y+dist >= 0 && y+dist < this.height - 1 && x+dist >= 0 && x+dist < this.width - 1){
                if (type == grid[y+dist][x+dist].type && type == grid[y+dist+1][x+dist+1].type)
                    dia++ ;
                else if (dia < 4)
                    dia = 0 ;
                else
                    break ;
            }
            // Antidiagonal check
            if (y-dist > 0 && y-dist <= this.height - 1 && x+dist >= 0 && x+dist < this.width - 1){
                if (type == grid[y-dist][x+dist].type && type == grid[y-dist-1][x+dist+1].type)
                    antidia++ ;
                else if (antidia < 4)
                    antidia = 0 ;
                else
                    break ;
            }
        }
        return (horizontal == 4 || vertical == 4 || dia == 4 || antidia == 4) ;
    }
    
    /**
     * Loads grid with 4 winning situations
     */
    public void loadTest(){
        createGrid() ;
        grid[0][1].type = "O" ;
        grid[0][2].type = "O" ;    
        grid[0][3].type = "O" ;
        grid[0][4].type = "O" ;
        
        grid[0][1].type = "O" ;
        grid[1][2].type = "O" ;
        grid[2][3].type = "O" ;
        grid[3][4].type = "O" ;
                
        grid[2][0].type = "O" ;
        grid[3][0].type = "O" ;    
        grid[4][0].type = "O" ;
        grid[5][0].type = "O" ;
        
        grid[5][2].type = "O" ;
        grid[4][3].type = "O" ;
        grid[3][4].type = "O" ;
        grid[2][5].type = "O" ;
    }
}
