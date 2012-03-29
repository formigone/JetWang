package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

public class SpriteSheet {
	private Bitmap bmp;
	private Rect srcRect=new Rect(0,0,0,0);
	private Rect dstRect=new Rect(0,0,0,0);
	private PointF position = new PointF(0,0);
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
		dstRect.set((int)position.x, (int)position.y, (int)position.x+bmpSize.x, (int)position.y+bmpSize.y);
		
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bmp, srcRect, dstRect, null);
	}
	
	
	public Point getSize() {
		return bmpSize;
	}
	
	public int getNFrames() { return nFrames; }
	
	public void setPosition(PointF p) { position = p; }
	public void move(float dx,float dy) { position.offset(dx, dy); }
		
}
