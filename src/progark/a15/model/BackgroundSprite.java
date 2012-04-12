package progark.a15.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class BackgroundSprite implements Sprite {
	private Bitmap img;
	private RectF pos = new RectF(0,0,0,0);
	private Rect bound = new Rect(0,0,0,0);
	//All sprites need a paint class to draw.
	private Paint dummyPaint = new Paint();
	
	public BackgroundSprite(Bitmap img) {
		this.img = img;
		bound.set(0,0,img.getWidth(),img.getHeight());
		pos.set(bound);
	}
	/*
	 * TODO Tile based backgrounds for space saving? Not implementing shit before I know.
	 */
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(img,bound,pos, dummyPaint);		
	}

	@Override
	public void setPosition(RectF p) {
		pos.set(p);
	}

	@Override
	public RectF getPosition() {
		return pos;
	}

}
