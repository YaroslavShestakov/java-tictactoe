package games.tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;
/**
 * The class contains some basic information about player.
 * Every player has his own timer that counts time spend.
 * @author Yaroslav Shestakov
 * 
 */
public class Player implements Serializable {
    public static String HUMAN = "Human" ;
    public static String COMPUTER = "Computer" ;
  
    protected Integer timeSpent = 0 ;
    protected Integer turnsUsed = 0 ;
    protected String type ;
    /**
     * Sign of a player. In fact any String can be used to fill tiles.
     */
    protected String mark ;
    
    private Timer timer  ;
    
    public Player(String mark, String type){
        this.type = type ;
        this.mark = mark ;
        
        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                timeSpent++ ;
            }
        });
    }

    public Player(String mark){
        this(mark, Player.HUMAN) ;
    }
    
    /**
     * Starts incrementing time spent by Player
     */
    public void startCount(){
        timer.start();
    }
    
    /**
     * Stops incrementing time spent by Player
     */
    public void stopCount(){
        timer.stop();
    }
}
