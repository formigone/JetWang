package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class SpriteSheet {
	private Bitmap bmp;
	private Rect srcRect=new Rect(0,0,0,0);
	private RectF dstRect=new RectF(0,0,0,0);
	private int nFrames;
	private float step;
	private float dtBuffer=0;
	private int curFrame=0;
	private Point bmpSize;
	public SpriteSheet(int nFrames,float step, Bitmap bmp) {
		this.nFrames = nFrames;
		this.step = step;
		this.bmp=bmp;
		bmpSize=new Point(bmp.getWidth()/nFrames,bmp.getHeight());		
	}
	public void update(float dt) {
		//Update frame counter
		dtBuffer+=dt;
		if(dtBuffer>step) {
			dtBuffer-=step;
			curFrame++;
			curFrame = (curFrame==nFrames) ? 0 : curFrame;
			srcRect.set(bmpSize.x*curFrame,0, (1+curFrame)*bmpSize.x, bmpSize.y);
		}		
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bmp, srcRect, dstRect, null);
	}
	
	
	public Point getSize() {
		return bmpSize;
	}
	
	public int getNFrames() { return nFrames; }
	
	public void setPosition(RectF r) { dstRect.set(r); }
	public RectF getPosition() { return dstRect; }
	public void move(float dx,float dy) { dstRect.offset(dx, dy); }
		
}
