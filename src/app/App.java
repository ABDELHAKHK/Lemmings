package app;

import model.Game;
import view.Gui;

public class App {
	private static final int SCALE = 25;
	private static final int SPEED = 600;
	private static final int WIDTH = 30;
	private static final int HEIGTH = 17;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game(SPEED, HEIGTH, WIDTH, "maps/level1.txt");
		Gui.createGui(game, SCALE);
		game.run();
	}

}
