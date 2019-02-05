package model;

public class Bomber implements State{
	private int step = 0;
	
	@Override
	public void action(Lemming lemming) {
		// TODO Auto-generated method stub
		lemming.setOnState(false);
		
		//on prend le coordonne x et y du lemming
		int locationX = lemming.getLocation().getX(); 
		int locationY = lemming.getLocation().getY();
		
		//apres 3 pas le lemming explose
		if(step >= 3){
			//le lemming explose dans le rayon de 2 case suivant x et y apres 3 pas
			for(int x = locationX - 2; x <= locationX + 2; x++){ //on boucle a partir de 2 case avant jusqu à 2 case apres suivant X 
				for(int y = locationY - 2; y <= locationY + 2; y++){ //pareillement mais suivant Y
				
					//on teste si les blocs aux alentours sont des blocs destructibles
					if( lemming.getBlocs()[y][x] instanceof DestructibleBloc){
						
						//ecrase les blocs dans le rayon de 2 case en vide
						lemming.getBlocs()[y][x] = new VoidBloc(lemming.getGame(), x, y);
						//notifie
						lemming.getBlocs()[y][x].display();
					}
				}
			}
			lemming.setAlive(false);
			lemming.changeToVoid();//change le lemming lui meme en vide
		}
		else{
			step++;
			lemming.move();
		}
	}

}
