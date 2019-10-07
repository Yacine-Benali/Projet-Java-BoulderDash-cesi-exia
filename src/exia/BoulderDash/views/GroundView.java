package exia.BoulderDash.views;

import javax.swing.*;

import exia.BoulderDash.models.LevelModel;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * FieldView
 *
 * FieldView, created by controller; we notice that we don't need to make
 * levelModel observable; Because of the sprites we have to refresh the game
 * windows very often so don't need of observers/observable mechanism
 *
 *
 *        This view is basically drawing into the Frame the levelModel.
 *
 */
public abstract class GroundView extends JPanel implements Observer {
	protected LevelModel levelModel;

	/**
	 * Class constructor
	 *
	 * @param  levelModel  Level model
	 */
	public GroundView(LevelModel levelModel) {
		this.levelModel = levelModel;
		this.levelModel.addObserver(this);
	}

	/**
	 * Draws the map
	 *
	 * @param  width   Map width
	 * @param  height  Map height
	 * @param  g       Map graphical object
	 */
	public void drawTerrain(int width, int height, Graphics g) {
		// Draw items
		
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					g.drawImage(this.levelModel.getImage(x, y), (x * 16), (y * 16), this);
				}
			}

			if(!this.levelModel.isGameRunning()) {
				if(!this.levelModel.getRockford().getHasExplosed()) {
					this.displayWin();
				} else {
					this.displayLose();
				}
			}
				if(!this.levelModel.getGamePaused() && this.levelModel.getGameInformationModel().getRemainingsDiamonds() ==  0)
				{					//System.out.print("u win");
					WinLoseView win =  new WinLoseView("win");
					levelModel.setGamePaused(true); 
				}
				
			
			
			
		
	}

	/**
	 * Set the view to inform the user that he won
	 */
	private void displayWin() {
        new WinLoseView("win");
        
        
	}

	/**
	 * Set the view to inform the user that he is not good at this game
	 */
	private void displayLose() {
		new WinLoseView("lose");
	}

	/**
	 * Paints the map
	 *
	 * @param  g  Map graphical object
	 */
	public void paint(Graphics g) {
		this.drawTerrain(this.levelModel.getSizeWidth(), this.levelModel.getSizeHeight(), g);
	}

	/**
	 * Updates the view
	 *
	 * @param  obs  Observable item
	 * @param  obj  Object item
	 */
	@Override
	public void update(Observable obs, Object obj) {
		repaint();
		
	}
}