package model;

public class Parachutist implements State{
	private int timeToFall = 0;//temps de chute
	
	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		lemming.setOnState(false);
		
		//on prend le coordonne de la case au dessous du lemming
		Coordinate loc = new Coordinate(lemming.getLocation().getX(), lemming.getLocation().getY() + 1);
		
		//on teste si la case au dessous du lemming est un vide
		if( lemming.getBlocs()[loc.getY()][loc.getX()] instanceof VoidBloc){
			
			//le temps de chute est 2 fois moins vite
			if( timeToFall %2 == 0 ){
				lemming.setDirection(Direction.Down);//on affecte sa direction en 'bas'
				lemming.changeToVoid();
				lemming.setLocation(loc);
				lemming.changeStateLemming();
			}
			
			timeToFall++;
			lemming.setOnState(true);
		}
		//sinon 
		else{
			timeToFall = 0;
			lemming.setState(null); //on change le lemming en marcheur
			lemming.setClicked(false);
			lemming.move();
		}
	}

}
