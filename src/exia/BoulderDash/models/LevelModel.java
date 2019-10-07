package exia.BoulderDash.models;

import java.awt.image.BufferedImage;
import java.util.Observable;

import exia.BoulderDash.helpers.LevelLoadHelper;
import exia.BoulderDash.models.DiamondModel;
import exia.BoulderDash.models.DirtModel;
import exia.BoulderDash.models.DisplayableElementModel;
import exia.BoulderDash.models.EmptyModel;
import exia.BoulderDash.models.GameInformationModel;
import exia.BoulderDash.models.RockfordModel;
import exia.BoulderDash.models.SteelWallModel;


/**
 * LevelModel
 *
 * Levels are loaded from XML file. The view knows the model, the controller is
 * going to modify the model in function of the game panel. The model notifies
 * the view when there are changes on it.
 *
 */
public class LevelModel extends Observable implements Runnable {
	private DisplayableElementModel[][] groundGrid;
	private String levelName;
	private int sizeWidth = 0;
	private int sizeHeight = 0;
	private int cursorXPosition = 0;
	private int cursorYPosition = 0;
	private boolean showCursor = false;
	//private CursorModel cursorModel;
	private LevelLoadHelper levelLoadHelper;
	private RockfordModel rockford;
	private GameInformationModel gameInformationModel;
	private int rockfordPositionX, rockfordPositionY;
	private boolean gameRunning;
	private boolean gamePaused;

	/**
	 * Sprite animation thread
	 */
	private Thread spriteAnimator;

	/**
	 * Animation speed
	 */
	private final int DELAY = 25;

	/**
	 * Class constructor
	 *
	 * @param  levelName        Level name
	 * @param  audioLoadHelper  Audio load helper
	 */
	public LevelModel(String levelName) {
		this.levelName = levelName;
		this.gamePaused = false;
		this.gameRunning = true;
	

		this.levelLoadHelper = new LevelLoadHelper(this.levelName);
		
		this.groundGrid = this.levelLoadHelper.getGroundGrid();
		this.sizeWidth  = this.levelLoadHelper.getWidthSizeValue();
		this.sizeHeight = this.levelLoadHelper.getHeightSizeValue();

		//this.cursorModel = new CursorModel();
		this.gameInformationModel = new GameInformationModel(this.levelLoadHelper.getDiamondsToCatch());

		this.createLimits();
        this.initRockford();
        this.initThreadAnimator();
 
	}




	/**
	 * Creates the limits Puts steel walls all around the game panel
	 */
	private void createLimits() {
		int maxWidth = this.sizeWidth -1;
		int maxHeight = this.sizeHeight - 1;

		for (int x = 0; x < this.sizeWidth; x++) {
			this.groundGrid[x][0] = new SteelWallModel();
			this.groundGrid[x][maxHeight] = new SteelWallModel();
		}
		for (int y = 0; y < this.sizeHeight; y++) {
			this.groundGrid[0][y] = new SteelWallModel();
			this.groundGrid[maxWidth][y] = new SteelWallModel();
		}
	}
	/**
	 * Initializes the animator thread
	 */
	private void initThreadAnimator() {
		this.spriteAnimator = new Thread(this);
		this.spriteAnimator.start();
	}

	/**
	 * Initializes the Rockford position attributes
	 */
	private void initRockford() {
		this.rockfordPositionX = this.levelLoadHelper.getRockfordPositionX();
		this.rockfordPositionY = this.levelLoadHelper.getRockfordPositionY();
		this.rockford = this.levelLoadHelper.getRockfordInstance();
	}




	/**
	 * Updates the horizontal & vertical positions of Rockford in the model
	 * 
	 * @param  posX  Horizontal position of Rockford
	 * @param  posY  Vertical position of Rockford
	 */
	public void updateRockfordPosition(int posX, int posY) {
		this.rockfordPositionY = posY;
		this.rockfordPositionX = posX;
	}

	/**
	 * Checks whether position is out-of-bounds or not
	 *
	 * @param  posX  Horizontal position
	 * @param  posY  Vertical position
	 */
	private boolean isOutOfBounds(int posX, int posY) {
		if (posX > 0 && posY > 0 && posX < this.getLevelLoadHelper().getHeightSizeValue() && posY < this.getLevelLoadHelper().getWidthSizeValue()) {
			return false;
		}

		return true;
	}



	/**
	 * Gets the horizontal position of Rockford from the model
	 *
	 * @return  Horizontal position of Rockford
	 */
	public int getRockfordPositionX() {
		return this.rockfordPositionX;
	}

	/**
	 * Sets the new Rockford position
	 * 
	 * @param  posX  Next horizontal position on the grid
	 * @param  posY  Next vertical position on the grid
	 */
	public void setPositionOfRockford(int posX, int posY) {
		int oldX = this.getRockfordPositionX();
		int oldY = this.getRockfordPositionY();

		if (this.groundGrid[posX][posY].getSpriteName() == "diamond") {
			this.gameInformationModel.incrementScore();
			this.gameInformationModel.decrementRemainingsDiamonds();
			
		}
		if (this.groundGrid[posX][posY].getSpriteName() == "door") {
			this.gameRunning = false;
		}


		// Check that we are not out of bound...
		if (this.isOutOfBounds(posX, posY) == false) {
			// Create a new empty model in the old pos of Rockford
			this.groundGrid[oldX][oldY] = new EmptyModel();

			// Save the x / y pos of Rockford in the levelModel only
			this.updateRockfordPosition(posX, posY);

			this.groundGrid[posX][posY] = this.getRockford();
		}
	}

	

	
	/**
	 * Gets the vertical position of Rockford from the model
	 *
	 * @return  Vertical position of Rockford
	 */
	public int getRockfordPositionY() {
		return this.rockfordPositionY;
	}

	/**
	 * Gets the Rockford object instance
	 *
	 * @return  Rockford object
	 */
	public RockfordModel getRockford() {
		return this.rockford;
	}

	/**
	 * Gets the displayable element at given positions
	 *
	 * @param  x  Block horizontal position
	 * @param  y  Block vertical position
	 * @return Displayable element at given positions
	 */
	public DisplayableElementModel getDisplayableElement(int x, int y) {
		return this.groundGrid[x][y];
	}

	/**
	 * Gets the image at given positions
	 *
	 * @param  x  Block horizontal position
	 * @param  y  Block vertical position
	 * @return Image at given positions
	 */
	public BufferedImage getImage(int x, int y) {
        DisplayableElementModel elementModel = this.getDisplayableElement(x, y);

        if(elementModel == null) {
            return new DirtModel().getSprite();
        }

		return elementModel.getSprite();
	}


    /**
     * Return whether rockford is in model or not
     * Notice: not optimized, be careful
     *
     * @return  Whether rockford is in model or not
     */
    public boolean isRockfordInModel() {
        boolean isInModel = false;

        // Iterate and catch it!
        for (int x = 0; x < this.getSizeWidth() && !isInModel; x++) {
            for (int y = 0; y < this.getSizeHeight() && !isInModel; y++) {
                if(this.groundGrid[x][y] != null && this.groundGrid[x][y].getSpriteName() == "rockford") {
                    isInModel = true;
                }
            }
        }

        return isInModel;
    }

 



	/**
	 * Gets the level horizontal size
	 *
	 * @return   Horizontal size
	 */
	public int getSizeWidth() {
		return this.sizeWidth;
	}

	/**
	 * Sets the level horizontal size
	 *
	 * @param   sizeWidth  Horizontal size
	 */
	public void setSizeWidth(int sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	/**
	 * Gets the level vertical size
	 *
	 * @return  Vertical size
	 */
	public int getSizeHeight() {
		return this.sizeHeight;
	}

	/**
	 * Sets the level vertical size
	 *
	 * @return  sizeHeight  Vertical size
	 */
	public void setSizeHeight(int sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	/**
	 * Gets the ground level model
	 *
	 * @return Ground level model
	 */
	public DisplayableElementModel[][] getGroundLevelModel() {
		return groundGrid;
	}

	/**
	 * Notify observers about a model change
	 */
	private void localNotifyObservers() {
		this.notifyObservers();
		this.setChanged();
	}

	/**
	 * Update the current sprite Notifies the observers
	 * 
	 * @param  x  Sprite block horizontal position
	 * @param  y  Sprite block vertical position
	 */
	public void updateSprites(int x, int y) {
        if(groundGrid[x][y] == null) {
            groundGrid[x][y] = new DirtModel();
        }

        groundGrid[x][y].update(System.currentTimeMillis());
        this.localNotifyObservers();
	}

	/**
	 * Update all the sprites So that they can be animated
	 */
	public void run() {
		while (gameRunning) {
			if (!gamePaused) {
				for (int x = 0; x < this.getSizeWidth(); x++) {
					for (int y = 0; y < this.getSizeHeight(); y++) {
						this.updateSprites(x, y);
					}
				}
			}

			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
		}
	}

	

	/**
	 * Gets the associated level load helper
	 *
	 * @return  Level load helper
	 */
	public LevelLoadHelper getLevelLoadHelper() {
		return this.levelLoadHelper;
	}

	/**
	 * Gets the cursor position X value
	 *
	 * @return  Cursor position X value
	 */
	public int getCursorXPosition() {
		return this.cursorXPosition;
	}

	/**
	 * Gets the cursor position Y value
	 *
	 * @return  Cursor position Y value
	 */
	public int getCursorYPosition() {
		return this.cursorYPosition;
	}




	

	

	/**
	 * sets the game to a defined state
	 *
	 * @param  gameRunning  Whether game is running or not
	 */
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
		// Timer to refresh the view properly...
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.localNotifyObservers();
	}

	/**
	 * tells if the game is running
	 *
	 * @return  whether the game is running or not
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}

	/**
	 * Gets whether cursor is to be shown or not
	 *
	 * @return  whether cursor needs to be shown or not
	 */
	public boolean getShowCursor() {
		return this.showCursor;
	}

	/**
	 * Sets whether cursor is to be shown or not
	 *
	 * @param   showCursor  whether cursor needs to be shown or not
	 */
	public void setShowCursor(boolean showCursor) {
		this.showCursor = showCursor;
	}

	/**
	 * When a boulder is falling on Rockford there is an explosion around him
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void exploseGround(int x, int y) {
		this.groundGrid[x][y] = new EmptyModel();
		this.groundGrid[x + 1][y] = new EmptyModel();
		this.groundGrid[x - 1][y] = new EmptyModel();
		this.groundGrid[x][y + 1] = new EmptyModel();
		this.groundGrid[x + 1][y + 1] = new EmptyModel();
		this.groundGrid[x - 1][y + 1] = new EmptyModel();
		this.groundGrid[x][y - 1] = new EmptyModel();
		this.groundGrid[x + 1][y - 1] = new EmptyModel();
		this.groundGrid[x - 1][y - 1] = new EmptyModel();
		this.rockford.setHasExplosed(true);

		// Again a sleep to notify the observers properly
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.localNotifyObservers();
	}

	/**
	 * Makes the DisplayableElement[x][y] fall one box down
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void makeThisDisplayableElementFall(int x, int y) {
		this.groundGrid[x][y].setFalling(true);
		this.groundGrid[x][y + 1] = this.groundGrid[x][y];
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Makes the BoulderModel[x][y] slide left
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void makeThisBoulderSlideLeft(int x, int y) {
		this.groundGrid[x][y].setFalling(true);
		this.groundGrid[x - 1][y + 1] = this.groundGrid[x][y];
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Makes the BoulderModel[x][y] slide right
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void makeThisBoulderSlideRight(int x, int y) {
		this.groundGrid[x][y].setFalling(true);
		this.groundGrid[x + 1][y + 1] = this.groundGrid[x][y];
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Makes the BoulderModel[x][y] transform into a diamond
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void transformThisBoulderIntoADiamond(int x, int y) {
		this.groundGrid[x][y + 2] = new DiamondModel();
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Makes the BoulderModel[x][y] moving to right
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void moveThisBoulderToRight(int x, int y) {
		this.groundGrid[x + 1][y] = this.groundGrid[x][y];
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Makes the BoulderModel[x][y] moving to left
	 *
	 * @param  x  Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void moveThisBoulderToLeft(int x, int y) {
		this.groundGrid[x - 1][y] = this.groundGrid[x][y];
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Deletes the BoulderModel[x][y]
	 *
	 * @param  x   Object horizontal position
	 * @param  y  Object vertical position
	 */
	public void deleteThisBoulder(int x, int y) {
		this.groundGrid[x][y] = new EmptyModel();
	}

	/**
	 * Gets gameInformationModel
	 *
	 * @return gameInfos like score, remainings Diamonds etc
	 */
	public GameInformationModel getGameInformationModel() {
		return this.gameInformationModel;
	}

	/**
	 * Explose the brick wall
	 * 
	 * @param  x
	 * @param  y
	 */
	public void exploseThisBrickWall(int x, int y) {
		this.groundGrid[x][y] = new EmptyModel();
		this.groundGrid[x][y + 1] = new EmptyModel();
	}



	/**
	 * Set the gamePaused variable
	 * 
	 * @param  gamePaused
	 */
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	/**
	 * Get the gamePaused variable
	 * 
	 * @return  gamePaused
	 */
	public boolean getGamePaused() {
		return this.gamePaused;
	}

	

}