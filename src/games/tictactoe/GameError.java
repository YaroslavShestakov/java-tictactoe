package games.tictactoe;

/**
 * The class contains a set of constants that 
 * correspond possible errors that can occur during a game.
 * @author Yaroslav Shestakov
 */
public class GameError {
    /**
     * Some unknown or default error.
     */
    public static final int CUSTOM                = 0 ;
    /**
     * Tile is already marked by another player.
     */
    public static final int TILE_ALREADY_TAKEN    = 1 ;
    /**
     * Coordinates do not exist.
     */
    public static final int WRONG_POSITION        = 2 ;
    /**
     * Game is not instantiated yet.
     */
    public static final int GAME_NOT_STARTED      = 3 ;
    /**
     * Game is already finished and no longer playable.
     */
    public static final int GAME_OVER             = 4 ;
    
}
