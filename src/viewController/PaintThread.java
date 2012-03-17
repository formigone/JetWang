package viewController;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class PaintThread extends Thread{
	private SurfaceHolder mSurfaceHolder;
	private Handler mHandler;
	private Context mContext;
	private Paint mLinePaint;
	private Paint blackPaint;
	GameEngine gEngine;

	//for consistent rendering
	private float sleepTime;
	//amount of time between frames (in milliseconds), optimally
	private final float delay=0.7f;

	//state of game (Running or Paused).
	int state = 1;
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;

	public PaintThread(SurfaceHolder surfaceHolder, Context context, Handler handler, GameEngine gEngineS) {

		//data about the screen
		mSurfaceHolder = surfaceHolder;
		this.mHandler = handler;
		this.mContext = context;

		//black painter below to clear the screen before the game is rendered
		blackPaint = new Paint();
		blackPaint.setARGB(255, 0, 0, 0);

		gEngine=gEngineS;
	}


	//This is the most important part of the code. It is invoked when the call to start() is
	//made from the SurfaceView class. It loops continuously until the game is finished or
	//the application is suspended.
	@Override
	public void run() {
		long beforeTime;
		//UPDATE
		while (state==RUNNING) {
			//time before update
			beforeTime = System.nanoTime();
			//This is where we update the game engine
			gEngine.update(sleepTime);
			//DRAW
			Canvas c = null;
			try {
				//lock canvas so nothing else can use it
				c = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					//clear the screen with the black painter.
					c.drawRect(0, 0, c.getWidth(), c.getHeight(), blackPaint);
					//This is where we draw the game engine.
					gEngine.draw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}

			//SLEEP
			//Sleep time. Time required to sleep to keep game consistent
			//This starts with the specified delay time (in milliseconds) then subtracts from that the
			//actual time it took to update and render the game. This allows our game to render smoothly.
			//This synch will fail miserably if CPU is too slow to render a frame in the allotted Delay time.
			this.sleepTime = delay-((System.nanoTime()-beforeTime)/1000000L);

			try {
				//actual sleep code
				if(sleepTime>0){
					Thread.sleep((long) sleepTime);
				}
				else {
					Log.d("TIMEOUT!", "Frame drawn too slow. Next frame dropped.");
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(PaintThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}