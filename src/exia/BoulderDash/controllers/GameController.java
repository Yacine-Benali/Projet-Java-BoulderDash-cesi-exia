package exia.BoulderDash.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exia.BoulderDash.controllers.NavigationBetweenViewController;
import exia.BoulderDash.models.LevelModel;
import exia.BoulderDash.views.GameView;
import exia.BoulderDash.views.MenuView;


/**
 * GameController
 *
 * This system creates the view.
 * The game loop is also handled there.
 *
 */
public class GameController implements ActionListener {
	private LevelModel levelModel;
    private MenuView menuView;
	private GameView gameView;
	private NavigationBetweenViewController navigationBetweenViewController;
	
    /**
     * Class constructor
     *
     * @param  levelModel  Level model
     * @param navigationBetweenViewController 
     */
	public GameController(LevelModel levelModel,  NavigationBetweenViewController navigationBetweenViewController) {
        this.navigationBetweenViewController = navigationBetweenViewController;
        
		this.levelModel = levelModel;
        this.gameView = new GameView(this, levelModel); 
        this.menuView = navigationBetweenViewController.getMenuView();

	}

	/**
	 * Handles the 'action performed' event
     *
	 * @param  event  Action event
	 */
	public void actionPerformed(ActionEvent event) {
        switch(event.getActionCommand()) {
            case "menu":
            	this.menuView.setVisible(true);
            	this.resetGame("menu");
                break;
        }
	}

	/**
	 * Function to reset the game
	 */
    public void resetGame(String source) {
		this.gameView.dispose();
		
		if(source.equals("restart")){
	    	this.levelModel = new LevelModel(this.navigationBetweenViewController.getPickedLevelIdentifier());
			this.gameView = new GameView(this, levelModel);
			this.gameView.setVisible(true);
		}
	}


    /**
     * Return the game view
     * @return gameView
     */
	public GameView getGameView() {
		return gameView;
	}

	/**
	 * Set the gameView
	 * @param gameView
	 */
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
}