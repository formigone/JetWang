package model;

import java.util.ArrayList;
import java.util.Vector;

import viewController.TouchHandler;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class GameEngine {
	private Resources res;
	//Game layers
	private ArrayList<GameLayer> layers = new ArrayList<GameLayer>();
	
	//Controller class for handling touch events
	private TouchHandler tHandler;
	
	public void init(Resources resources) {
		this.res = resources;	
		tHandler = new TouchHandler(this);
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
	 * Touch handler sent from view. Pass this to TouchHandler. It will callback with required changes to UI.
	 */
	public void onTouch(MotionEvent event) {
		tHandler.onTouch(event);
	}
	/*
	 * Callback from TouchHandler. Do necessary calls to game layers to update state as needed.
	 * Pos is position on screen?
	 */
	public void touched(Vector pos) {
		//TODO: what kind of position does the touchhandler submit? Needs to be screen ambiguous.
	}
	
}
