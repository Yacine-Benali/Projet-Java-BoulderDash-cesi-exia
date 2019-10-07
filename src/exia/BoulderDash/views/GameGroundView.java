package exia.BoulderDash.views;

import javax.swing.*;

import exia.BoulderDash.controllers.GameController;
import exia.BoulderDash.controllers.GameKeyController;
import exia.BoulderDash.models.LevelModel;
import exia.BoulderDash.views.GroundView;

import java.awt.*;


/**
 * GameFieldView
 *
 * Game field view for the game itself.
 *
 *.
 */
public class GameGroundView extends GroundView {
    private GameController gameController;

    /**
     * Class constructor
     *
     * @param  gameController  Game controller
     * @param  levelModel      Level model
     */
    public GameGroundView(GameController gameController, LevelModel levelModel) {
        super(levelModel);

        this.gameController = gameController;

        this.addKeyListener(new GameKeyController(this.levelModel));

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFocusable(true);
    }
}
