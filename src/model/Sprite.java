package model;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

public interface Sprite {
	public void update(float dt);
	public void draw(Canvas c);
	
	//All sprites have a position
	public void setPosition(PointF p);
	public PointF getPosition();
	
	//All sprites have a size, but it cannot be set; it is given by the sprite contents
	public Point getSize();
}
