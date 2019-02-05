package model;

public class Climber implements State {

	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		lemming.setOnState(false);
		
		//on prend le coordonne de la case suivante par rapport a la direction
		Coordinate nextBloc = new Coordinate(lemming.getLocation().getX() + lemming.getDirection().getX(), lemming.getLocation().getY() + lemming.getDirection().getY());
		
		lemming.setDirection(Direction.Up);
		//on prend le coordonne de la case avec la direction haut
		Coordinate toUp = new Coordinate(lemming.getLocation().getX() + lemming.getDirection().getX(), lemming.getLocation().getY() + lemming.getDirection().getY());
		
		//on teste si la case suivante est un bloc destructible OU indestructible ET que la case suivant la direction haut du lemming est vide 
		if( lemming.getBlocs()[nextBloc.getY()][nextBloc.getX()] instanceof DestructibleBloc 
				|| lemming.getBlocs()[nextBloc.getY()][nextBloc.getX()] instanceof UnDestructibleBloc
				&& lemming.getBlocs()[toUp.getY()][toUp.getX()] instanceof VoidBloc){
			
			//on teste si la case au dessus de la case suivante est un vide
			if(lemming.getBlocs()[nextBloc.getY() -1][nextBloc.getX()] instanceof VoidBloc){
				lemming.setDirection(lemming.getPreviousDirection());//on prend la derniere direction
				
				//on prend le coordonne d'une case plus haute par rapport a la derniere direction
				Coordinate now = new Coordinate(lemming.getLocation().getX() + lemming.getPreviousDirection().getX(), lemming.getLocation().getY() + lemming.getPreviousDirection().getY() -1);
				lemming.addChange(new Change(lemming.getLocation(), Change.ChangeType.VOID));
				lemming.setLocation(now);
				lemming.changeStateLemming();//on change son etat
				lemming.setState(null);//le lemming devient marcheur
			}
			//sinon si la case au dessus de la case suivante n est pas vide
			//on monte
			else{
				lemming.addChange(new Change(lemming.getLocation(), Change.ChangeType.VOID));
				lemming.setLocation(toUp);//on affecte le coordonne de la direction haut
				lemming.changeStateLemming();
				lemming.setOnState(true);
				lemming.setClicked(false);
			}
		}
		else{
			lemming.move();
			lemming.setClicked(false);
		}
	
	}

}
