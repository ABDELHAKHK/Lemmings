package model;

public class DestructibleBloc extends Bloc{

	public DestructibleBloc(Game game, int posX, int posY) {
		super(game, posX, posY);
		// TODO Auto-generated constructor stub
		addChange(new Change(blocLocation, Change.ChangeType.DESTRUCTIBLE));
	}

}
