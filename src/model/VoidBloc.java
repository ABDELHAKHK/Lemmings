package model;

public class VoidBloc extends Bloc{

	public VoidBloc(Game game, int posX, int posY) {
		super(game, posX, posY);
		// TODO Auto-generated constructor stub
		addChange(new Change(blocLocation, Change.ChangeType.VOID));
	}

}
