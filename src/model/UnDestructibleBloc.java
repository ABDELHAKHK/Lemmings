package model;

public class UnDestructibleBloc extends Bloc{

	public UnDestructibleBloc(Game game, int posX, int posY) {
		super(game, posX, posY);
		// TODO Auto-generated constructor stub
		addChange(new Change(blocLocation, Change.ChangeType.UNDESTRUCTIBLE));
	}

}
