package model;

import java.util.ArrayList;
import java.util.List;

import controller.GameObserver;

public abstract class Observable {
	private ArrayList<Change> changes = new ArrayList<>();
	private ArrayList<GameObserver> observers = new ArrayList<>();
	
	protected void addChange(Change c) {
	    changes.add(c);
	}
	
	public void registerObserver(GameObserver observer) {
	    observers.add(observer);
	}
	
	public void unregisterObserver(GameObserver observer) {
	    observers.remove(observer);
	}	

	public void notifyObserver() {
	    List<Change> ch = getChanges();
	    for(GameObserver obs : observers){
	    	obs.update(ch);
	    }
	}
	
	private List<Change> getChanges() {
		List<Change> result = new ArrayList<>(changes);
		changes.clear();
		return result;
	}
}
