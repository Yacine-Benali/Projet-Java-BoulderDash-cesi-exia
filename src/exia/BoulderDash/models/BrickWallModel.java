package exia.BoulderDash.models;

import exia.BoulderDash.models.DisplayableElementModel;


/**
 * BrickWallModel
 *
 * Represents the brick wall in the game.
 *
 */
public class BrickWallModel extends DisplayableElementModel {
	private static String spriteName;
	private static boolean isDestructible;
	private static boolean canMove;
	private static boolean impactExplosive;
	private static boolean animate;
	private static int priority;
	private static boolean falling;
	private static String collideSound;

    /**
     * Static dataset
     * Specifies the physical parameters of the object
     */
	static {
		spriteName = "brickwall";
		isDestructible = true;
		canMove = false;
		impactExplosive = false;
		animate = false;
		priority = 3;
		falling = false;
		collideSound = "touch";
	}

    /**
     * Class constructor
     */
	public BrickWallModel() {
		super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        this.loadSprite(spriteName);
	}
}
