package exia.BoulderDash.views;

import javax.swing.*;

import exia.BoulderDash.controllers.GameController;
import exia.BoulderDash.models.LevelModel;
import exia.BoulderDash.views.GameGroundView;
import exia.BoulderDash.views.InformationPanel;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * GameView
 *
 * Specifies the game view itself.
 *
 */
public class GameView extends JFrame implements Observer {
	private GameGroundView gameGroundView;
	private JPanel actionPanel;
	private JPanel informationPanel;
	private GameController gameController;
	private LevelModel levelModel;

    /**
     * Class constructor
     *
     * @param  gameController  Game controller
     * @param  levelModel      Level model
     */
	public GameView(GameController gameController, LevelModel levelModel) {
		this.gameController = gameController;
		this.levelModel = levelModel;
		
        this.initializeView();
        this.createLayout();

        this.gameGroundView.grabFocus();
	}

    /**
     * Initializes the view
     */
    private void initializeView() {
        this.setVisible(false);
        this.setResizable(false);

        // UI parameters
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 432, 536);

        // App parameters
        this.setTitle("Boulder Dash | Game");

        Image appIcon = Toolkit.getDefaultToolkit().getImage("./res/app/app_icon.png");
        this.setIconImage(appIcon);
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        this.gameGroundView = new GameGroundView(this.gameController, this.levelModel);
        this.actionPanel = new JPanel();
        this.informationPanel = new InformationPanel(this.levelModel);
        this.informationPanel.setBackground(Color.white);


        // Add some buttons on the informationPanel
       
        this.createButton("menu", "Menu");

        this.add(this.actionPanel, BorderLayout.SOUTH);
        this.add(this.informationPanel, BorderLayout.NORTH);
        this.add(this.gameGroundView, BorderLayout.CENTER);
    }

    /**
     * Gets the game field view
     *
     * @return  Game field view
     */
	public GameGroundView getGameFieldView() {
		return this.gameGroundView;
	}

    /**
     * Creates the given button
     *
     * @param   name  Button name
     * @return  Created button
     */
	public JButton createButton(String id, String name) {
		JButton button = new JButton(name);
		button.addActionListener(this.gameController);
		button.setActionCommand(id);

		this.actionPanel.add(button);

		return button;
	}

	/**
     * Updates the frame
     *
     * @param   obs  Observable item
     * @param   obj  Object item
     */
	@Override
	public void update(Observable obs, Object obj) {
		// Nothing done.
	}
}