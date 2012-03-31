package progark.a15.viewController;

import java.util.ArrayList;
import java.util.Vector;

import progark.a15.model.GameLayer;
import progark.a15.model.PlayerSprite;



import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class GameEngine {
	private Resources res;
	//Game layers
	private ArrayList<GameLayer> layers = new ArrayList<GameLayer>();
	//Player
	private PlayerSprite player;
	//Screen size
	private Point screenSize = new Point(0,0);
	
	public void init(Resources resources) {
		this.res = resources;
		layers.add(new GameLayer(false)); //Background layer 1
		layers.add(new GameLayer(false)); //Background layer 2
		layers.add(new GameLayer(true)); //Foreground layer (player, obstacles, enemies,) 
	}
	
	/*
	 * This is called every PaintThread.delay ms. (70ms atm)
	 */
	public void update(float dt) {
		for(GameLayer l : layers)
			l.update(dt);
	}	
	
	/*
	 * Draw is synchronized. Called about as often as the update()
	 */
	public void draw(Canvas canvas) {
		for(GameLayer l : layers)
			l.draw(canvas);
	}

	/*
	 * Touch handler sent from view. Only thing controlled in this view is the player.
	 */
	public void onTouchDown(MotionEvent event) {
		//On touch, calculate acceleration vector.
		player.accelerate(screenSize.x/2-event.getHistoricalX(event.getHistorySize()-1),
						  event.getHistoricalY(event.getHistorySize()-1)-screenSize.y);		
	}
	public void onTouchUp(MotionEvent event) {
		//Player now starts falling again.
		player.decelerate();
	}
	
	

	public void setScreenSize(int w, int h) {
		screenSize.set(w, h);
	}	
}
