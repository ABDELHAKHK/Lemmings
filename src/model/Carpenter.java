package model;

public class Carpenter implements State{
	private int step = 0;
	
	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		lemming.setOnState(false);
		
		//le coordonne de la case au dessous du lemming
		Coordinate locDown = new Coordinate(lemming.getLocation().getX(), lemming.getLocation().getY() + 1);
		
		//coordonne de la case apres le lemming suivant la direction
		Coordinate nextBloc = new Coordinate(lemming.getLocation().getX() + lemming.getDirection().getX(), lemming.getLocation().getY() + lemming.getDirection().getY());
		
		//Coordonne de la case au dessus du lemming
		Coordinate locTop = new Coordinate(lemming.getLocation().getX(), lemming.getLocation().getY() - 1);
		
		//on teste si la case au dessous est destructibible
		//puis si la case suivant et au dessus du suivant est vide
		//ensuite si la case au dessus du lemming est vide aussi
		//on construit un bloc destructible pour 5 case si les conditions sont satisfaites
		if( step < 5 && lemming.getBlocs()[locDown.getY()][locDown.getX()] instanceof DestructibleBloc  
				&& lemming.getBlocs()[nextBloc.getY()][nextBloc.getX()] instanceof VoidBloc
				&& lemming.getBlocs()[nextBloc.getY() - 1][nextBloc.getX()] instanceof VoidBloc
				&& lemming.getBlocs()[locTop.getY()][locTop.getX()] instanceof VoidBloc) {
			
			/*for(Lemming lem : lemming.getGame().getLemmings()){
				if(lem.getLocation().getX() == nextBloc.getX() && lem.getLocation().getY() == nextBloc.getY()){
					lem.move();
				}
			}*/
			//on construit un nouveau bloc a chaque pas
			lemming.getBlocs()[nextBloc.getY()][nextBloc.getX()] = new DestructibleBloc(lemming.getGame(), nextBloc.getX(), nextBloc.getY());
			lemming.addChange(new Change(nextBloc, Change.ChangeType.DESTRUCTIBLE));
			//on instancie la nouvelle coordonne de la montee
			nextBloc = new Coordinate(nextBloc.getX(), nextBloc.getY() - 1);
			lemming.changeToVoid();
			lemming.setLocation(nextBloc);
			lemming.changeStateLemming();
			lemming.setOnState(true);
			lemming.setClicked(false);
			step++;
		}
		else{
			if(step != 0){
				lemming.setState(null);
			}
			lemming.move();
			lemming.setClicked(false);
		}
	}

}
