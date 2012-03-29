package model;

import java.util.ArrayList;


public class GameLayer {
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	public void addSprite(Sprite s) {
		sprites.add(s);
	}
	public void removeSprite(Sprite s) {
		sprites.remove(s);
	}

}
