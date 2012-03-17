/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
***/
package viewController;

import java.util.Vector;

import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;

/*
 * Dummy class directly imported from PlanetWars. Simpler one needed. DELETE!
 */
public class TouchHandler {
   //Maximum time in ms a touch can last. All gestures lasting less than this will be interpreted as a touch
   private int touchMaxTimeDownMs=300;
   //Store instance of game engine for click callback
   private GameEngine gEngine;

   // We can be in one of these 3 states
   private static final int NONE = 0;
   private static final int DRAG = 1;
   private static final int ZOOM = 2;
   private int mode = NONE;

   private float oldDist = 1f;

   public TouchHandler(GameEngine gEngine) {
	   this.gEngine=gEngine;
   }

   public boolean onTouch(MotionEvent event) {
      // Handle touch events here...
      switch (event.getAction() & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN:
         mode = DRAG;
         break;
      case MotionEvent.ACTION_POINTER_DOWN:
         oldDist = spacing(event);
         if (oldDist > 10f) {
            mode = ZOOM;
         }
         break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
    	 //Touch time less than max time. Notify GameEngine of click
    	 if(event.getEventTime()-event.getDownTime()<touchMaxTimeDownMs) {
    		 float[] point = {event.getX(),event.getY()};
    		 
    		 gEngine.touched(new Vector(point[0],point[1]));
    	 }
    	 
         mode = NONE;
         break;
      case MotionEvent.ACTION_MOVE:
         if (mode == DRAG) {
        	 
         }
         else if (mode == ZOOM) {
            float newDist = spacing(event);
            if (newDist > 10f) {
               float scale = newDist / oldDist;
               matrix.postScale(scale, scale, mid.x, mid.y);
            }
         }
         break;
      }
      return true; // indicate event was handled
   }

   /** Determine the space between the first two fingers */
   private float spacing(MotionEvent event) {
      float x = event.getX(0) - event.getX(1);
      float y = event.getY(0) - event.getY(1);
      return FloatMath.sqrt(x * x + y * y);
   }

   /** Calculate the mid point of the first two fingers */
   private void midPoint(Vector point, MotionEvent event) {
      float x = event.getX(0) + event.getX(1);
      float y = event.getY(0) + event.getY(1);
      point.set(x / 2, y / 2);
   }
}




