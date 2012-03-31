package progark.a15.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerSprite extends AbstractPhysSprite {
	//All sprites need a paint class to draw.
	private Paint dummyPaint = new Paint();
	//Images for player left, right and center
	private Bitmap pLeft;
	private Bitmap pRight;
	private Bitmap pCenter;
	//An animated sprite for the jetpack flame. TODO Needs to be positioned relative to the avatar
	private AnimatedSprite flame;
	
	public PlayerSprite(Bitmap left,Bitmap right,Bitmap center,SpriteSheet flame) {
		this.pLeft=left;
		this.pRight=right;
		this.pCenter=center;
		this.flame = new AnimatedSprite(flame);
		//Set sprite size
		this.position.inset(-pCenter.getWidth(), -pCenter.getHeight());
	}
	
	@Override
	public void collided(CollisionListener c) {
		if(c instanceof ObstacleSprite) {
			//Cast to ObstacleSprite
			fixCollision((ObstacleSprite)c);
		}	
	}

	
	@Override
	public void draw(Canvas c) {
		//x acceleration is much less than y acceleration. Jetpack man accelerating straight up
		if(Math.abs(acceleration.x/acceleration.y)<0.1) {
			c.drawBitmap(pCenter, position.left, position.top, dummyPaint);
			// TODO Add flame here
		}
		//Going to the left, as x acceleration <0
		else if(acceleration.x<0) {
			c.drawBitmap(pLeft, position.left, position.top, dummyPaint);
			//TODO add flame here
		}
		//Else right
		else {
			//TODO add flame here
			c.drawBitmap(pRight, position.left, position.top, dummyPaint);
		}	
	}
	
	
	private void fixCollision(ObstacleSprite d) {
		//All these set speed towards colliding entity to 0 and nudges the player a bit away. No bounce.
		if((d.getPosition().right-position.left<5)) { //Collided with obstacle on left side
			speed.x=0;
			position.offset(d.getPosition().right-position.left,0);
		}
		else if((position.right-d.getPosition().left<5)) { //Collided with obstacle on right side
			speed.x=0;
			position.offset(d.getPosition().left-position.right,0);
		}
		else if((position.bottom-d.getPosition().top<5)) { //Landed on obstacle
			speed.y=0;
			position.offset(0,d.getPosition().top-position.bottom);
		}
		else if(d.getPosition().bottom-position.top<5) { //Collided with obstacle bottom
			speed.y=0;
			position.offset(0,d.getPosition().bottom-position.top);
		}
	}
}
