package model;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

public class AnimatedSprite extends AbstractSprite {
	private SpriteSheet spriteSheet;

	@Override
	public void update(float dt) {
		spriteSheet.update(dt);
		
	}

	@Override
	public void draw(Canvas c) {
		spriteSheet.draw(c);
	}

	@Override
	public Point getSize() {
		return spriteSheet.getSize();
	}
	
	@Override
	public void setPosition(PointF p) { 
		super.setPosition(p);
		spriteSheet.setPosition(p);
	}

}
