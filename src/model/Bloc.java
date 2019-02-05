package model;

public abstract class Bloc extends Observable{
	protected Coordinate blocLocation;
	protected Game game;
	
	public Bloc(Game game, int posX, int posY) {
		// TODO Auto-generated constructor stub
		this.game = game;
		registerObserver(game);
		blocLocation = new Coordinate(posX, posY);
	}
	
	public void display(){
		notifyObserver();
	}
	
	public Coordinate getBlocLocation() {
		return blocLocation;
	}

}
