package model;

import java.util.ArrayList;

import android.graphics.Canvas;


public class GameLayer {
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	public void addSprite(Sprite s) {
		sprites.add(s);
	}
	public void removeSprite(Sprite s) {
		sprites.remove(s);
	}
	public void update(float dt) {
		for(Sprite s : sprites) {
			s.update(dt);
		}
	}
	public void draw(Canvas c) {
		for(Sprite s : sprites) {
			s.draw(c);
		}
	}

}
