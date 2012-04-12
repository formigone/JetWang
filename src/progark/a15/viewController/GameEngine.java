package progark.a15.viewController;

import java.util.ArrayList;
import java.util.Vector;

import progark.a15.R;
import progark.a15.model.GameLayer;
import progark.a15.model.PlayerSprite;
import progark.a15.model.SpriteFactory;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
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
	//Difficulty set
	private int difficulty;
	//Point counter. Defined by achieved height and bonuses picked up.
	private int points=0;
	
	public void init(Resources resources,int difficulty) {
		this.res = resources;
		//Give spriteFactory access to the game resources
		SpriteFactory.getInstance().setResources(resources);
		
		
		this.difficulty=difficulty;
	}
	public void initGame() {
		layers.add(new GameLayer(false)); //Background layer 1
		layers.add(new GameLayer(false)); //Background layer 2
		layers.add(new GameLayer(true)); //Foreground layer (player, obstacles, enemies,)
		layers.get(0).addSprite(SpriteFactory.getInstance().getMountains());
		layers.get(2).addSprite(SpriteFactory.getInstance().getGround());
		
		//Make player
		player=SpriteFactory.getInstance().getPlayer();
		player.setPointListener(this);
		layers.get(2).addSprite(player);
	}
	
	/*
	 * This is called every PaintThread.delay ms. (70ms atm)
	 */
	public void update(float dt) {
		//TODO: Randomly generate bonuses and obstacles?
		//TODO: Obstacles and bonuses as a function of achieved height?
		
		//Player stops when hitting screen sides.
		if(player.getPosition().left<0)
			player.move(-player.getPosition().left, 0);
		if(player.getPosition().right>this.screenSize.x)
			player.move(screenSize.x-player.getPosition().right, 0);
		
		for(GameLayer l : layers)
			l.update(dt);
	}	
	
	/*
	 * Draw is synchronized. Called about as often as the update()
	 */
	public void draw(Canvas canvas) {
		//Player is below screen. Game over.
		if(player.getPosition().top>canvas.getClipBounds().bottom) {
			
		}
		//Player is in the top half of the screen. Move clip bounds up (Camera always follows player)
		else if(player.getPosition().bottom<canvas.getClipBounds().centerY()) {
			//Move all layers a nudge down!
			float dy = canvas.getClipBounds().centerY()-player.getPosition().bottom;
			//Background layer moves slower than the rest -> Parallax mapping.
			layers.get(0).move(0, dy*0.1f);
			layers.get(1).move(0, dy);
			layers.get(2).move(0, dy);
			
			//Move Layers to compensate
		}
		for(GameLayer l : layers)
			l.draw(canvas);
	}

	/*
	 * Touch handler sent from view. Only thing controlled in this view is the player.
	 */
	public void onTouchDown(MotionEvent event) {
		Log.d("TOUCH","touchdown");
		//On touch, calculate acceleration vector.
		player.accelerate(event.getHistoricalX(event.getHistorySize()-1)-screenSize.x/2,
						  event.getHistoricalY(event.getHistorySize()-1)-screenSize.y);		
	}
	public void onTouchUp(MotionEvent event) {
		Log.d("TOUCH","touchup");
		//Player now starts falling again.
		player.decelerate();
	}
	
	public void setScreenSize(int w, int h) {
		Log.d("SCREENSIZE","Set to: "+w+" x "+h);
		screenSize.set(w, h);
		// Pass id of background image supposed to fill the screen here!
		SpriteFactory.getInstance().setScalation(R.drawable.backgroundplain,w,h);
		//All resource initalization is now complete. Make game!
		this.initGame();
	}
	
	//Generic method for adding points
	public void addPoints(int points) {
		this.points+=points;
	}
	//PlayerSprite needs to check difficulty to calculate magnitude of bonuses collected.
	public int getDifficulty() { return difficulty; }
	
	
}
