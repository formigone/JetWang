package progark.a15.model;

import progark.a15.viewController.GameEngine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.FloatMath;
import android.util.Log;

public class PlayerSprite extends AbstractPhysSprite {
	//Player settings
	private int fuel=Integer.MAX_VALUE;
	private final int fuelMax=Integer.MAX_VALUE;
	private float gravity=0.0005f;
	private final float maxAcc=0.001f;
	private boolean isBoosting=false;
	//All sprites need a paint class to draw.
	private Paint dummyPaint = new Paint();
	//Images for player left, right and center
	private Bitmap pLeft;
	private Bitmap pRight;
	private Bitmap pCenter;
	//An animated sprite for the jetpack flame. TODO Needs to be positioned relative to the avatar
	private SpriteSheet flame;
	//Callback to game engine: update on points earned.
	private GameEngine gEngine;
	
	public PlayerSprite(Bitmap left,Bitmap right,Bitmap center,SpriteSheet flame) {
		this.pLeft=left;
		this.pRight=right;
		this.pCenter=center;
		this.flame = flame;
		//Set sprite size
		setSize(pCenter.getWidth(), pCenter.getHeight());
		//Add gravity to player
		this.setAcceleration(0, gravity);
		this.setSpeed(0, 0);
	}
	
	public void setPointListener(GameEngine g) {
		this.gEngine = g;
	}
	
	//Method called on touch to accelerate PlayerSprite. Takes a vector (x, y) and sets acceleration vector.
	public void accelerate(float x,float y) {
		/*
		 * Must scale the vector given to make it have length=maxAcc
		 * A little vector math gives the formulas (formula tested): 
		 * xScl=maxAcc*sqrt(1/(1+y²/x²)) 
		 * yScl=maxAcc*sqrt(1/(1+x²/y²))
		 */
		isBoosting=true;
		setAcceleration(Math.signum(x)*maxAcc*FloatMath.sqrt(1/(1+(y*y)/(x*x))), 
						-maxAcc*FloatMath.sqrt(1/(1+(x*x)/(y*y)))+gravity);
		Log.d("NEWACC",this.getAcceleration().x+" x "+this.getAcceleration().y);
		Log.d("NEWSPEED",this.getSpeed().x+" x "+this.getSpeed().y);
		
	}
	public void decelerate() {
		isBoosting=false;
		setAcceleration(0,gravity);
	}
	
	@Override
	public void collided(CollisionListener c) {
		if(c instanceof ObstacleSprite) {
			//Cast to ObstacleSprite
			fixCollision((ObstacleSprite)c);
		}	
		if(c instanceof CollectableSprite ) {
			//Sniff out CollectableSprite attributes and delete the sprite
			collectSprite((CollectableSprite)c);
			
		}
	}

	
	@Override
	public void draw(Canvas c) {
		//x acceleration is much less than y acceleration. Jetpack man accelerating straight up
		if(Math.abs(getAcceleration().x/getAcceleration().y)<0.1) {
			c.drawBitmap(pCenter, getPosition().left, getPosition().top, dummyPaint);
			// TODO Add flame here
		}
		//Going to the left, as x acceleration <0
		else if(getAcceleration().x<0) {
			c.drawBitmap(pLeft, getPosition().left, getPosition().top, dummyPaint);
			//TODO add flame here
		}
		//Else right
		else {
			//TODO add flame here
			c.drawBitmap(pRight, getPosition().left, getPosition().top, dummyPaint);
		}	
		
		//Set the position of the flame relative to the player!
		flame.setPosition(getPosition().centerX()-flame.getSize().x/2,getPosition().bottom-5);
		//Draw flame if player is boosting
		if(isBoosting) flame.draw(c);
		
	}
	public void update(float dt) {
		super.update(dt);
		//Update the flame sprite!
		flame.update(dt);
		
		//Jetpack on. Decrease fuel. For simplicity, decrease is time elapsed. (adjust max fuel for game balance instead.)
		if(isBoosting) {
			fuel-=dt;
			//We're empty! Decelerate and set fuel to 0 (no negatives accepted)
			if(fuel<=0) {
				decelerate();
				fuel=0;
			}
		}
	}
	
	
	private void fixCollision(ObstacleSprite d) {
		//All these set speed towards colliding entity to 0 and nudges the player a bit away. No bounce.
		if((d.getPosition().right-getPosition().left<5)) { //Collided with obstacle on left side
			setSpeed(0,getSpeed().y);
			move(d.getPosition().right-getPosition().left+1,0);
		}
		else if((getPosition().right-d.getPosition().left<5)) { //Collided with obstacle on right side
			setSpeed(0,getSpeed().y);
			move(d.getPosition().left-getPosition().right-1,0);
		}
		else if((getPosition().bottom-d.getPosition().top<5)) { //Landed on obstacle
			setSpeed(getSpeed().x,0);
			move(0,d.getPosition().top-getPosition().bottom-1);
		}
		else if(d.getPosition().bottom-getPosition().top<5) { //Collided with obstacle bottom
			setSpeed(getSpeed().x,0);
			move(0,d.getPosition().bottom-getPosition().top+1);
		}
	}
	private void collectSprite(CollectableSprite c) {
		switch (c.getType()) {
		//Small and large fuel bonus
		case FUEL_ADD: case FUEL_FILL:
			addFuel(getMagnitude(c.getType()));
			break;
		//Small and large point bonus
		case BONUSPOINT_SMALL: case BONUSPOINT_BIG:
			gEngine.addPoints(getMagnitude(c.getType()));
			break;
		}
		
	}
	
	private int getMagnitude(BonusType b) {
		if(gEngine.getDifficulty()==0) return b.getEasy();
		if(gEngine.getDifficulty()==1) return b.getMedium();
		if(gEngine.getDifficulty()==2) return b.getHard();
		else return 0;
	}
	private void addFuel(int num) {
		fuel+=num;
		if(fuel>fuelMax) fuel=fuelMax;
	}
	
}
