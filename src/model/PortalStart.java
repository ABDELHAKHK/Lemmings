package model;

public class PortalStart extends Bloc{

	public PortalStart(Game game, int posX, int posY) {
		super(game, posX, posY);
		// TODO Auto-generated constructor stub
		addChange(new Change(blocLocation, Change.ChangeType.START));
	}

}
