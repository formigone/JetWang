package progark.a15.model;

import android.graphics.PointF;
import android.graphics.RectF;

/*
 * AbstractSprite handles bounds and physics of a sprite.
 */

public abstract class AbstractPhysSprite implements CollisionListener,Sprite {
	protected RectF position = new RectF(0,0,0,0);
	protected PointF speed = new PointF(0,0);
	protected PointF acceleration = new PointF(0,0);
	
	public void update(float dt) {
		//Move object according to equation of motion: r=r0+vt+at²/2
		position.offset(speed.x*dt+acceleration.x*dt*dt/2,
						speed.y*dt+acceleration.y*dt*dt/2);
		//Accelerate velocity (integration) v=v0+a*dt
		speed.offset(acceleration.x*dt, acceleration.y*dt);
		
		
	}
	
	
	public void setPosition(RectF r) { position = r; }
	public RectF getPosition() { return position; }
	//Some more physical parameters most sprites share.
	public void setSpeed(PointF spd) { speed = spd; }
	public PointF getSpeed() { return speed; }
	public void setAcceleration(PointF acc) { acceleration=acc; }
	public PointF getAcceleration() { return acceleration; }
	
	
}
