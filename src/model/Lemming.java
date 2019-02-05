package model;

public class Lemming extends Observable{
	private static final int DEADFALL = 5; //chute mortelle est de 5 cases
	
	private Game game;
	private Coordinate entryPoint;
	private Coordinate location;
	private Direction direction;
	private Direction previousDirection;
	private int fall = 0;
	private Bloc[][] blocs;
	private State state;
	private boolean alive; //qui dit que le lemming est en vie
	private boolean finished; //qui dit que le lemming a franchi la sortie 
	private boolean isBlocked;
	
	private boolean clicked; //qui dit que le lemming est selectionné
	private boolean onState; //qui dit que le lemming a un role
	
	/* CONSTRUCTOR */
	
	public Lemming(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		registerObserver(game);
		previousDirection = Direction.Right;
		fall = 0;
		blocs = game.getBlocs();
		state = null;
		alive = true;
		finished = false;
		isBlocked = false;
		clicked = false;
		onState = false;
		for(int row = 0; row < game.getMapHeight(); row++){
			for(int col = 0; col < game.getMapWidth(); col++){
				if(blocs[row][col] instanceof PortalStart){
					entryPoint = blocs[row][col].getBlocLocation();
				}
			}
		}
		
		location = entryPoint;
	}
	
	/* METHOD */
	
	public void changeToVoid(){ //change le bloc precedent en vide
		if(blocs[location.getY()][location.getX()] instanceof VoidBloc){
			addChange(new Change(location, Change.ChangeType.VOID));
		}
	}
	

	public void move(){
		direction = previousDirection;
		
		//si le lemming a un role
		if(state != null && isOnState() == true){
			state.action(this);
		}
		
		//sinon 
		else{
			Coordinate locate = new Coordinate(location.getX(), location.getY() +1);
			if(blocs[locate.getY()][locate.getX()] instanceof PortalEnd){
				setFinished(true);
				changeToVoid();
			}
			
			//si la case au dessous (getY() +1) du coordonne actuel est different du vide
			else if( ! (blocs[locate.getY()][locate.getX()] instanceof VoidBloc) ){
				
				//si la chute >= chute mortelle (5 case +) OU que c'est une lave
				if( fall >= DEADFALL || blocs[locate.getY()][locate.getX()] instanceof MagmaBloc){
					alive = false;
					changeToVoid();
				}
				
				//sinon
				else{
					//on reinit la chute à 0, on est sur la terre ferme
					fall = 0; 
					//on prend le coordonne de la case suivante par rapport a la direction
					Coordinate nextBloc = new Coordinate(location.getX() + direction.getX(), location.getY() + direction.getY());
					
					for(Lemming lem : game.getLemmings()){
						//si le lemming recontre un lemming bloqueur a la prochaine case
						if(lem.getLocation().getX() == nextBloc.getX() && lem.getLocation().getY() == nextBloc.getY() && lem.state instanceof Blocker){
							isBlocked = true;
						}			
					}
					
					//si la prochaine est une porte de sortie
					if(blocs[nextBloc.getY()][nextBloc.getX()] instanceof PortalEnd){
						finished = true;
						changeToVoid();
					}
					
					//sinon si c'est un vide sans bloquage
					else if(blocs[nextBloc.getY()][nextBloc.getX()] instanceof VoidBloc && isBlocked == false){
						changeToVoid();
						location = nextBloc;
						changeStateLemming();
					}
					
					//sinon si c'est une lave
					else if(blocs[nextBloc.getY()][nextBloc.getX()] instanceof MagmaBloc){
						alive = false;
						changeToVoid();
					}
					
					/* sinon si c'est un teleporteur*/
					//....
					
					//sinon si (la case au dessus du prochain coordonne est un vide OU une porte de sortie) 
					//ET que le coordonne au dessus du coordonne actuel est un vide et pas bloque
					else if( (blocs[nextBloc.getY() -1][nextBloc.getX()] instanceof VoidBloc || blocs[nextBloc.getY() -1][nextBloc.getX()] instanceof PortalEnd) 
							&& blocs[location.getY() -1][location.getX()] instanceof VoidBloc && isBlocked == false ){
						
						//si la case au dessus du prochaine case est une porte de sortie
						if(blocs[nextBloc.getY() -1][nextBloc.getX()] instanceof PortalEnd){
							finished = true;
							changeToVoid();
						}
						
						//sinon si la case au dessus de la prochaine case est un vide
						//on monte au dessus de la prochaine case car c'est la meme hauteur 
						else{
							//on prend le coordonne de la case au dessus de la prochaine case suivant la direction
							nextBloc = new Coordinate(location.getX() + direction.getX(), location.getY() + direction.getY() -1);
							changeToVoid();
							location = nextBloc; //on affecte la nouvelle coordonnee
							changeStateLemming();
						}			
					}
					
					//sinon, le reste des conditions, on rencontre un bloqueur, une case 2+ de hauteur,.... 
					//on change de direction
					else{
						if(direction == Direction.Right){
							direction = Direction.Left;
							previousDirection = direction;
						}
						else{
							direction = Direction.Right;
							previousDirection = direction;
						}
					}	
				}
			}
			
			//sinon la case au dessous du coordonne actuel est un vide
			else{
				previousDirection = direction;
				direction = Direction.Down;
				Coordinate nextBloc = new Coordinate(location.getX() + direction.getX(), location.getY() + direction.getY());
				changeToVoid(); 
				location = nextBloc;
				changeStateLemming();
				fall ++;
			}
			isBlocked = false;
			setOnState(true);
		}
		this.notifyObserver();
	}
	
	
	public void changeStateLemming(){ // changement de l'etat de lemming 
		if(state instanceof Blocker){
			addChange(new Change(location, Change.ChangeType.BLOCKER));
		}
		else if(state instanceof Digger){
			addChange(new Change(location, Change.ChangeType.DIGGER));
		}
		else if(state instanceof Driller){
			addChange(new Change(location, Change.ChangeType.DRILLER));
		}
		else if(state instanceof Bomber){
			addChange(new Change(location, Change.ChangeType.BOMBER));
		}
		else if(state instanceof Parachutist){
			addChange(new Change(location, Change.ChangeType.PARACHUSTIST));
		}
		else if(state instanceof Carpenter){
			addChange(new Change(location, Change.ChangeType.CARPENTER));
		}
		else if(state instanceof Climber){
			addChange(new Change(location, Change.ChangeType.CLIMBER));
		}
		else{
			addChange(new Change(location, Change.ChangeType.WALKER));
		}
	}
	
	/*  GETTER AND SETTER  */
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public int getFall() {
		return fall;
	}
	
	public Coordinate getStart() {
		return entryPoint;
	}
	
	public Coordinate getLocation() {
		return location;
	}
	
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Bloc[][] getBlocs() {
		return blocs;
	}
	
	public boolean isCliked() {
		return clicked;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public boolean isOnState() {
		return onState;
	}
	
	public void setOnState(boolean onState) {
		this.onState = onState;
	}
	
	public Direction getPreviousDirection() {
		return previousDirection;
	}
}
