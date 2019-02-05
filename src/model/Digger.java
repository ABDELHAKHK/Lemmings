package model;

public class Digger implements State{

	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		lemming.setOnState(false);
		
		//on prend le coordonne de la case en dessous du lemming
		Coordinate loc = new Coordinate(lemming.getLocation().getX(), lemming.getLocation().getY() +1); 
		
		//on prend la case suivante du lemming par rapport a la direction
		Coordinate nextBloc = new Coordinate(lemming.getLocation().getX() + lemming.getDirection().getX(), lemming.getLocation().getY() + lemming.getDirection().getY());
		
		//on teste si la case suivante est un destructible ET tout sauf vide
		if( lemming.getBlocs()[nextBloc.getY()][nextBloc.getX()] instanceof DestructibleBloc 
				&& !(lemming.getBlocs()[loc.getY()][loc.getX()] instanceof VoidBloc)				
				){
			
			//la case suivante devient un vide
			lemming.getBlocs()[nextBloc.getY()][nextBloc.getX()] = new VoidBloc(lemming.getGame(), nextBloc.getX(), nextBloc.getY()); 
			lemming.changeToVoid();
			lemming.setLocation(nextBloc);//la position du lemming devient sur la case vide cree
			lemming.changeStateLemming();
			
			lemming.setOnState(true);
		}
		//sinon si vide on revient a l'etat marcheur
		//et le jeu continue
		else{
			lemming.setState(null);
			lemming.setClicked(false);
			lemming.move();
		}
		
	}

}
