package model;

import android.graphics.PointF;
import android.graphics.RectF;

/*
 * AbstractSprite handles bounds and physics of a sprite.
 */

public abstract class AbstractSprite implements CollisionListener,Sprite {
	private RectF position = new RectF(0,0,0,0);
	private PointF speed = new PointF(0,0);
	private PointF accelleration = new PointF(0,0);
	
	public void update(float dt) {
		//Move object according to equation of motion: r=r0+vt-at²/2
		move(speed.x*dt-accelleration.x*dt*dt/2,
			 speed.y*dt-accelleration.y*dt*dt/2);
		
	}
	
	
	public void setPosition(RectF r) { position = r; }
	public RectF getPosition() { return position; }
	public void move(float dx, float dy) { position.offset(dx, dy); }
	//Some more physical parameters most sprites share.
	public void setSpeed(PointF spd) { speed = spd; }
	public PointF getSpeed() { return speed; }
	public void setAccelleration(PointF acc) { accelleration=acc; }
	public PointF getAccelleration() { return accelleration; }
	
	
}
