package model;

import android.graphics.Point;
import android.graphics.PointF;

public abstract class AbstractSprite implements CollisionListener,Sprite {
	private PointF position = new PointF(0,0);
	
	
	
	
	public void setPosition(PointF p) { position = p; }
	public PointF getPosition() { return position; }
}
