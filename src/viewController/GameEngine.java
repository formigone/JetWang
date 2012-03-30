package viewController;

import java.util.ArrayList;
import java.util.Vector;

import model.GameLayer;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class GameEngine {
	private Resources res;
	//Game layers
	private ArrayList<GameLayer> layers = new ArrayList<GameLayer>();
	
	public void init(Resources resources) {
		this.res = resources;	
	}
	
	/*
	 * This is called every PaintThread.delay ms. (70ms atm)
	 */
	public void update(float dt) {
		
	}	
	
	/*
	 * Draw is synchronized. Called about as often as the update()
	 */
	public void draw(Canvas canvas) {
		
	}

	/*
	 * Touch handler sent from view.
	 */
	public void onTouch(MotionEvent event) {
	}
	/*
	 * Callback from TouchHandler. Do necessary calls to game layers to update state as needed.
	 * Pos is position on screen?
	 */
	public void touched(Vector pos) {
		//TODO: what kind of position does the touchhandler submit? Needs to be screen ambiguous.
	}
	
}
