package model;

public class MagmaBloc extends Bloc{

	public MagmaBloc(Game game, int posX, int posY) {
		super(game, posX, posY);
		// TODO Auto-generated constructor stub
		addChange(new Change(blocLocation, Change.ChangeType.MAGMA));
	}

}
