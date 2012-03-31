package progark.a15.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * Class for all items that disappear when collected
 */
public class CollectableSprite extends AbstractPhysSprite {
	//All sprites need a paint class to draw.
	private static Paint dummyPaint = new Paint();
	private Bitmap image;
	private BonusType type;
	public CollectableSprite(Bitmap img,BonusType b) {
		image=img;
		type=b;
		//Set bounding box
		setSize(img.getWidth(), img.getHeight());
	}

	@Override
	public void collided(CollisionListener c) {}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(image, getPosition().left, getPosition().top, dummyPaint);	
	}
	public BonusType getType() { return type; }
	
	
}
