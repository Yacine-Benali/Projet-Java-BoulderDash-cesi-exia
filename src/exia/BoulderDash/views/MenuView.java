package exia.BoulderDash.views;

import java.awt.BorderLayout;

import javax.swing.*;

import exia.BoulderDash.controllers.NavigationBetweenViewController;
import exia.BoulderDash.views.MenuImage;


/**
 * MenuView
 *
 * Menu view
 *
 *.
 * .
 */
public class MenuView extends JFrame {
	private NavigationBetweenViewController navigationBetweenViewController;
    private MenuImage menuImage;
	private JPanel actionPanel;
    private JPanel targetPanel;

    /**
     * Class constructor
     */
	public MenuView(NavigationBetweenViewController navigationBetweenViewController) {
		this.navigationBetweenViewController = navigationBetweenViewController;
		this.initializeView();
		this.createLayout();
	}

    /**
     * Initializes the view
     */
    private void initializeView() {
        this.setVisible(true);
        this.setResizable(false);

        // UI parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 200, 100);
        this.setSize(432, 536);

        // App parameters
        this.setTitle("Boulder Dash | Menu");
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        //LevelSelectorHelper levelSelectorHelper = new LevelSelectorHelper(false);
        
        //this.menuLevelSelector = levelSelectorHelper.createLevelList();

        this.targetPanel = new JPanel();

        this.menuImage = new MenuImage();
    	this.actionPanel = new JPanel();
    	
        // Add some buttons on the actionPanel
        this.createButton("game", "start");
        
        
        this.createButton("quit", "Quit");

        this.add(this.menuImage, BorderLayout.CENTER);
        this.add(targetPanel, BorderLayout.SOUTH);

        this.targetPanel.add(this.actionPanel, BorderLayout.WEST);
    }

    /**
     * Creates the given button
     *
     * @param   name  Button name
     * @return  Created button
     */
	public JButton createButton(String id, String name) {
		JButton button = new JButton(name);
		button.addActionListener(this.navigationBetweenViewController);
        button.setActionCommand(id);

		this.actionPanel.add(button);

		return button;
	}

   
}
