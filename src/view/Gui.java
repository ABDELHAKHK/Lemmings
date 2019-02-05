package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Game;

public class Gui {
	public static JFrame createGui(Game game, int scale){
		return createGui(0, 0, game, scale);
	}
	
	public static JFrame createGui(int posX, int posY, Game game, int scale){
		GamePanel gamePanel;	
		JFrame frame = new JFrame("Lemming");
		gamePanel = new GamePanel(game, scale);
		frame.add(gamePanel);
		frame.pack();
		frame.setLocation(posX, posY);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
		gamePanel.requestFocusInWindow();
		frame.setVisible(true);
		return frame;
	}
}
