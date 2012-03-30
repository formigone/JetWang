package progark.a15.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ObstacleSprite extends AbstractPhysSprite{
	//All sprites need a paint class to draw.
	private static Paint dummyPaint = new Paint();
	private Bitmap image;
	public ObstacleSprite(Bitmap img) {
		image=img;
		//Set bounding box
		position.inset(-img.getWidth(), -img.getHeight());
	}

	@Override
	public void collided(CollisionListener c) {}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(image, position.left, position.top, dummyPaint);	
	}
	
}
