package progark.a15.model;

import progark.a15.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/*
 * Singleton factory for creating sprites.
 * MUST BE INITIALIZED BY PROVIDING SCALING PERCENTAGE AND IMAGE RESOURCE CLASS PRIOR TO USE!
 */

public class SpriteFactory {
	//Singleton instance
	private static final SpriteFactory instance = new SpriteFactory();
	//Variables needing to be set prior to use
	private float scalation=-1;
	private Resources res;
	//Input list of images to be cached here Format is as the example under with the launcher icon.
	private final int[] cachedImgKeys = {R.drawable.ic_launcher};
	//imageCache is synchronized with cachedImages: cachedImgKeys.key->imageCache.value
	private Bitmap[] imageCache = new Bitmap[cachedImgKeys.length];
	
	/*
	 * Run these prior to use! If both are set, the factory automatically starts preloading images.
	 */
	public void setScalation(float scl) {
		scalation = scl;
		//Both values set. Start preloading
		if(res!=null) preloadImages();
	}
	public void setResources(Resources r) {
		res = r;
		//Both values set. Start preloading
		if(scalation!=-1) preloadImages();
	}
	
	//Getter method for singleton instance
	public static SpriteFactory getInstance() {
		return instance;
	}
	
	
	/*
	 * TODO:
	 * Methods for generating completely rigged sprites here
	 * 
	 * 
	 */
	
	/*
	 * Private helper method for preloading and prescaling all sprites to be used.
	 */
	private void preloadImages() {
		//Preload in new thread so this doesn't fudge up the draw thread
		new Thread(new Runnable() {
			public void run() {
				//Iterate over keys in defined images to be cached
				for(int i=0;i<cachedImgKeys.length;i++) {
					imageCache[i] = getBitmap(cachedImgKeys[i]);
				}
			}
		}).start();
	}
	/*
	 * Private helper method for creating Bitmap images for the cache (let's hope this fits in RAM). Outputs scaled images.
	 */
	private Bitmap getBitmap(int id) {
		return scalePercentage(BitmapFactory.decodeResource(res, id));
	}
	
	/*
	 * Scale an image by a given percentage (to fit screen)
	 */
	private Bitmap scalePercentage(Bitmap src) {
		return Bitmap.createScaledBitmap(src, (int)(src.getWidth()*scalation), (int)(src.getHeight()*scalation), true);
	}	

}
