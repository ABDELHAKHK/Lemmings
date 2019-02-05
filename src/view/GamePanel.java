package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import javax.swing.JComponent;

import controller.GameObserver;
import model.Blocker;
import model.Bomber;
import model.Carpenter;
import model.Change;
import model.Change.ChangeType;
import model.Climber;
import model.Digger;
import model.Driller;
import model.Game;
import model.Lemming;
import model.Parachutist;

public class GamePanel extends JComponent implements GameObserver{

	private static final long serialVersionUID = 1L;

	private final EnumMap<Change.ChangeType, Color> color;
	
	private BufferedImage image;
	private int scale;
	private int height;
	private int width;
	private Game game;
	
	public GamePanel(Game game, int scale) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.scale = scale;
		height = game.getHeight() * scale + 5*scale/2;
		width = game.getWidth() + 27 * scale;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
		game.registerObserver(this);
		color = new EnumMap<>(Change.ChangeType.class);
		
		color.put(Change.ChangeType.WALKER, new Color(84, 153, 199));//bleu
		color.put(ChangeType.BLOCKER, new Color( 81, 90, 90 ));//gris
		color.put(Change.ChangeType.DIGGER, new Color( 244, 208, 63 ));//jaune
		color.put(Change.ChangeType.DRILLER, new Color( 175, 122, 197 ));//violet
		color.put(Change.ChangeType.BOMBER, new Color(198, 41, 22));//rouge
		color.put(Change.ChangeType.PARACHUSTIST, new Color( 236, 112, 99 ));//rose
		color.put(Change.ChangeType.CARPENTER, new Color( 52, 73, 94));//bleu fonce
		color.put(Change.ChangeType.CLIMBER, new Color( 160, 13, 94));//margenta
		
		color.put(Change.ChangeType.VOID, Color.WHITE);
		color.put(Change.ChangeType.DESTRUCTIBLE, new Color(211, 127, 70));//marron
		color.put(Change.ChangeType.UNDESTRUCTIBLE, Color.BLACK);
		color.put(Change.ChangeType.END, new Color( 82, 190, 128));//vert
		color.put(Change.ChangeType.START, new Color( 118, 215, 196 )); //bleu ciel
		color.put(Change.ChangeType.MAGMA, new Color(234, 13, 53));//rouge
		
		addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				for(Lemming lem : game.getLemmings()){
					lem.setClicked(false);
					if(lem.getLocation().getX() == (e.getX()/scale) && lem.getLocation().getY() == (e.getY()/scale ) ){
						System.out.println("lemming selected"); 
						lem.setClicked(true);
					}	
				}
			}
		});
		
		addKeyListener(new KeyAdapter() {
			
			@Override 
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
					case KeyEvent.VK_NUMPAD0 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(null);
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD1 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Blocker());
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD2 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Digger());
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD3 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Driller());
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD4 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Bomber());
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD5 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Parachutist());
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD6 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Carpenter());
							}
						}
						break;
						
					case KeyEvent.VK_NUMPAD7 :
						for(Lemming lem : game.getLemmings()){
							if(lem.isCliked() == true){
								lem.setState(new Climber());
							}
						}
						break;
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		
		//liste de panneau a droite
		
		/**
		 * le score */
		g.setColor(Color.WHITE);
		g.fillRect(23 * scale, scale, 4*scale, 3*scale);
		
		g.setColor(Color.BLACK);
		g.drawString("SCORE", 24 * scale + scale/4, scale + scale / 2);
		
		g.drawString("Saved:", 23 * scale+ scale/2, 2 * scale + scale/2);
		g.drawString("Killed:", 23 * scale+ scale/2, 3 * scale + scale/2);
		
		/**
		 * l'instruction */
		g.setColor(Color.WHITE);
		g.fillRect(23 * scale, 7*scale, 4*scale, 9*scale);
		
		g.setColor(Color.BLACK);
		g.drawString("INSTRUCTION", 23 * scale + scale/2, 7*scale + scale/2);
		
		g.drawString("Click on ", 23 * scale + scale/2, 9*scale + scale/2);
		g.drawString("the lemming", 23 * scale + scale/2, 10*scale + scale/2);
		g.drawString("then assign", 23 * scale + scale/2, 11* scale + scale/2);
		g.drawString("a role ", 23 * scale + scale/2, 12* scale + scale/2);
		g.drawString("following ", 23 * scale + scale/2, 13* scale + scale/2);
		g.drawString("the numpad", 23 * scale + scale/2, 14*scale + scale/2);
		
		
		/**Liste panneau bas */
		g.setColor(new Color(84, 153, 199));
		g.fillRect(scale, 17 * scale, scale / 2, scale / 2);
		g.setColor(Color.WHITE);
		g.drawString("0 - WALKER", 2 * scale , 17 * scale + scale /2);
		
		g.setColor(new Color( 81, 90, 90 ));
		g.fillRect(6 * scale, 17 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("1 - BLOCKER", 7 * scale , 17 * scale + scale / 2);
		
		g.setColor(new Color( 244, 208, 63 ));
		g.fillRect(12 * scale, 17 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("2 - DIGGER", 13 * scale , 17 * scale + scale / 2);
		
		g.setColor(new Color( 175, 122, 197 ));
		g.fillRect(17 * scale + scale/2, 17 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("3 - DRILLER", 18 * scale + scale /2, 17 * scale + scale / 2);
		
		g.setColor(new Color(198, 41, 22));
		g.fillRect(scale, 18 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("4 - BOMBER", 2 * scale , 18 * scale + scale / 2);
		
		g.setColor(new Color( 236, 112, 99 ));
		g.fillRect(6 * scale, 18 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("5 - PARACHUTIST", 7 * scale , 18 * scale + scale / 2);
		
		g.setColor(new Color( 52, 73, 94));
		g.fillRect(12 * scale, 18 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("6 - CARPENTER", 13 * scale , 18 * scale + scale / 2);
		
		g.setColor(new Color( 160, 13, 94));
		g.fillRect(17 * scale + scale /2, 18 * scale, scale /2, scale /2);
		g.setColor(Color.WHITE);
		g.drawString("7 - CLIMBER", 18 * scale + scale /2, 18 * scale + scale / 2);
		
		g.setColor(new Color(84, 153, 199));
		g.drawString("0", 25 * scale+ scale/2, 2 * scale + scale/2);
		g.setColor(new Color(234, 13, 53));
		g.drawString("0", 25 * scale+ scale/2, 3 * scale + scale/2);
	}
	
	@Override
	public void update(List<? extends Change> o) {
		// TODO Auto-generated method stub
		Graphics g = image.getGraphics();	
		for(Lemming lem : game.getLemmings()){//la case de selection
			if(lem.isCliked() == true && lem.isFinished() == false && lem.isAlive()){
				g.drawRect(lem.getLocation().getX() * scale  + scale /4, lem.getLocation().getY() * scale + scale /4, scale /2, scale /2);
			}
		}	
		for(Change ch : o){
			g.setColor(color.get(ch.getChangeType()));
			g.fillRect(ch.getCoordinate().getX() * scale, ch.getCoordinate().getY() * scale, scale, scale); 
		}
		g.dispose();
		repaint();
	}

}
