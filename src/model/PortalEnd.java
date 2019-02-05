package model;

public class PortalEnd extends Bloc{

	public PortalEnd(Game game, int posX, int posY) {
		super(game, posX, posY);
		// TODO Auto-generated constructor stub
		addChange(new Change(blocLocation, Change.ChangeType.END));
	}

}
