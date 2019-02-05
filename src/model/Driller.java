package model;

public class Driller implements State{
	private int step = 0;
	
	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		lemming.setOnState(false);
		
		//on prend le coordonne de la case au dessous du lemming
		Coordinate loc = new Coordinate(lemming.getLocation().getX(), lemming.getLocation().getY() +1);
		
		//on fore vers le bas pour 5 cases si cest un destructible et que sa descente est moins de 5 cases
		if( step < 5 && lemming.getBlocs()[loc.getY()][loc.getX()] instanceof DestructibleBloc && lemming.getFall() < 5){
			lemming.setDirection(Direction.Down);
			Coordinate nextBloc = new Coordinate(lemming.getLocation().getX() + lemming.getDirection().getX(), lemming.getLocation().getY() + lemming.getDirection().getY());
			
			//pour chaque case creuse on le met en vide
			lemming.getBlocs()[loc.getY()][loc.getX()] = new VoidBloc(lemming.getGame(), loc.getX(), loc.getY()); 
			lemming.changeToVoid();
			lemming.setLocation(nextBloc);
			lemming.changeStateLemming();
			lemming.setClicked(false);
			lemming.setOnState(true);
			step++;
		}
		else{
			//lemming.setState(null);//on revient a marcheur
			//on si teste si l'etape a atteint 5 pas, on le change en marcheur
			if(step == 5){
				lemming.setState(null);
			}
			lemming.setClicked(false);
			lemming.move();
		}
	}

}
