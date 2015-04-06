package games.tictactoe;

import java.io.Serializable;
/**
 * Class contains information about a tile.
 * Tiles then should be stored and accessed 
 * through an array.
 * 
 * @author Yaroslav Shestakov
 */
public class Tile implements Serializable {
    public static String FREE = " " ;
    public static String X = "X" ;
    public static String O = "O" ;
    
    protected String type = Tile.FREE ;
}
