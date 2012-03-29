package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class SpriteSheet {
	private Bitmap bmp;
	private Rect srcRect=new Rect(0,0,0,0);
	private Rect dstRect=new Rect(0,0,0,0);
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
	public void update(float dt, float x, float y) {
		//Update frame counter
		dtBuffer+=dt;
		if(dtBuffer>step) {
			dtBuffer-=step;
			curFrame++;
			curFrame = (curFrame==nFrames) ? 0 : curFrame;
			srcRect.set(getWidth()*curFrame,0, (1+curFrame)*getWidth(), getHeight());
		}
		dstRect.set((int)x, (int)y, (int)x+getWidth(), (int)y+getHeight());
		
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bmp, srcRect, dstRect, null);
	}
	
	
	public int getWidth() {
		return bmpSize.x;
	}
	public int getHeight() {
		return bmpSize.y;
	}
	public int getNFrames() { return nFrames; }
	
	
		
}
