package model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * Essentially a class declaring some methods for importing an image, 
 * so this don't have to be defined in every sprite class using images.
 */
public abstract class AbstractImageSprite  implements Sprite {
	protected Bitmap sprite;
	
	//Takes the resources class for access to image store, and a resource identifier
	public void loadImage(Resources res, int id) {
		sprite = readImage(res, id);
	}
	//Returns bitmap given resources and id. Useful for sprites using more than one bitmap (PlayerSprite)
	public Bitmap readImage(Resources res,int id) {
		return BitmapFactory.decodeResource(res, id);
	}
	
	//Return the dimensions of the sprite on need
	public int getImageHeight() { return sprite.getHeight(); }
	public int getImageWidth() { return sprite.getWidth(); }

}
