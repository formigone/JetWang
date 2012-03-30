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
		// TODO Check what we collided with and take appropriate action!
		
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
}
