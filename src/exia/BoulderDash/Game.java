package exia.BoulderDash;

import java.io.IOException;

import javax.swing.*;
import exia.BoulderDash.controllers.JavaDataBase;
import exia.BoulderDash.controllers.NavigationBetweenViewController;




/**

* the boulder-dash game uses levels that are stored
* in the database don't forget to create the database  
*
* @author  BENALI Mohammed Yacine
* @version 1.0
* @since   2018-06-01 
*/

/**
 * Game
 *
 * Spawns the game.
 *
 */
public class Game {
	
    /**
     * Class constructor
     *
     * @param  args  Command-line arguments
     */
	
	public static String LEVEL = "level01";
	/*
	 * there are 5 levels :
	 * level01
	 * level02
	 * level03
	 * level04
	 * level05
	 */

    public static void main(String[] args) {
    	
    
			try {
				JavaDataBase.databasetest(LEVEL);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	
    	/*
    	 * it executes your Runnable on the AWT event-dispatching thread
    	 * . But why would you want to do that? Because the Swing data structures aren't thread-safe, 
    	 * so to provide programmers with an easily-achievable way of preventing concurrent access to them, 
    	 * the Swing designers laid down the rule that all code that accesses them must run on the same thread.
    	 *  That happens automatically for event-handling and display maintenance code, but if you've initiated a long-running action - on a new thread, 
    	 *  of course - how can you signal its progress or completion? You have to modify a Swing control, 
    	 * and you have to do it from the event-dispatching thread. Hence invokeLater.
    	 */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NavigationBetweenViewController();
            }
        });
    }
}
