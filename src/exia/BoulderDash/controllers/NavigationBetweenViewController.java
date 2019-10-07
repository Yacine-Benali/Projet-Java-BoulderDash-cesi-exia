package exia.BoulderDash.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exia.BoulderDash.Game;
import exia.BoulderDash.controllers.GameController;
import exia.BoulderDash.models.LevelModel;
import exia.BoulderDash.views.MenuView;

/**
 * Controller to navigate between the different views
 * 
 * 
 *
 */
public class NavigationBetweenViewController implements ActionListener {
	private MenuView menuView;
	private LevelModel levelModelForGame;
	private GameController gameController;
	private String pickedLevelIdentifier;

    /**
     * Class constructor
     */
	public NavigationBetweenViewController() {


		// Creation of the first view
		//this works as a reference to the current Objec
		this.menuView = new MenuView(this);
		
	}

    /**
     * Action performed event handler
     *
     * @param  event  Action event
     */
	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
            case "quit":
                System.exit(0);
                break;

            case "game":
                // Reinit the levelModelForGame...
                pickedLevelIdentifier = Game.LEVEL;
                System.out.println(pickedLevelIdentifier);

                this.levelModelForGame = new LevelModel(pickedLevelIdentifier);
                this.gameController = new GameController(levelModelForGame, this);

               
                this.gameController.getGameView().setVisible(true);
                this.gameController.getGameView().getGameFieldView().grabFocus();
			    break;
		}

		this.menuView.setVisible(false);
	}

  

    /**
     * Get the first view
     *
     * @return  First view
     */
    public MenuView getMenuView() {
        return this.menuView;
    }



	/**
	 * Get the pickedLevel
     *
	 * @return  pickedLevelIdentifier  Picked level identifier
	 */
	public String getPickedLevelIdentifier() {
		return pickedLevelIdentifier;
	}

	/**
	 * Set the pickedLevelIdentifier
     *
	 * @param  pickedLevelIdentifier  Picked level identifier
	 */
	public void setPickedLevelIdentifier(String pickedLevelIdentifier) {
		this.pickedLevelIdentifier = pickedLevelIdentifier;
	}
	
	
}
