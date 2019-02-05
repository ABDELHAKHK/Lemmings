package model;

public class Blocker implements State{

	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		
		lemming.setOnState(false);
		//on prend le coordoone de ce qui a au dessous de notre lemming
		Coordinate loc = new Coordinate(lemming.getLocation().getX(), lemming.getLocation().getY() + 1);
		
		//si la case au dessous du lemming est un destructible
		if( lemming.getBlocs()[loc.getY()][loc.getX()] instanceof DestructibleBloc){
			lemming.addChange(new Change(lemming.getLocation(), Change.ChangeType.BLOCKER));
			lemming.setOnState(true);//pour dire que le lemming a un role
		}
		
		//sinon on le change en marcheur
		//et il continue sa route
		//on enleve la selection
		else{
			lemming.setState(null);
			lemming.move();
			lemming.setClicked(false);
		}
	}

}
