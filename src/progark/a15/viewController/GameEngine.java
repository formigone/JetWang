package progark.a15.viewController;

import java.util.ArrayList;
import java.util.Vector;

import progark.a15.R;
import progark.a15.model.BackgroundSprite;
import progark.a15.model.BonusType;
import progark.a15.model.CollectableSprite;
import progark.a15.model.GameLayer;
import progark.a15.model.PlayerSprite;
import progark.a15.model.SpriteFactory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class GameEngine {
	private Resources res;
	private Context context;
	//Game layers
	private ArrayList<GameLayer> layers = new ArrayList<GameLayer>();
	//Player
	private PlayerSprite player;
	//Screen size
	private Point screenSize = new Point(0,0);
	//Difficulty set
	private int difficulty;
	//Point counter. Defined by achieved height and bonuses picked up.
	private int points=1337;
	//Height measuring
	private float height=1;
	//black painter below to clear the screen before the game is rendered Maybe remove this if background sprites covers all?
	private Paint backPaint = new Paint();
	
	//Paints for the HUD

	private Paint fuelFill = new Paint();
	private BackgroundSprite fuelFillBg;
	private Paint pointsPaint = new Paint();
	private Paint pointsPaint2;
	
	// PlayerType
	private int playerType; 
	
	public void init(Resources resources) {
		this.res = resources;
		//Give spriteFactory access to the game resources
		SpriteFactory.getInstance().setResources(resources);
		
		fuelFill.setStyle(Style.FILL);
		
		pointsPaint.setAntiAlias(true);
		pointsPaint.setStrokeWidth(7);
		pointsPaint.setColor(Color.YELLOW);
		pointsPaint.setStyle(Style.FILL);
		pointsPaint2 = new Paint(pointsPaint);
		pointsPaint2.setColor(Color.BLACK);
		pointsPaint2.setStyle(Style.STROKE);
		
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	// The GameView uses this method to pass data with difficulty and playerType
	public void setGameSettings(Bundle gameSettings) {
		this.difficulty = gameSettings.getInt("difficulty");
		this.playerType = gameSettings.getInt("playerType");
	}
	
	// Used to detect which character should be drawn on screen
	public int getPlayerType() {
		return this.playerType;
	}
	
	public void initGame() {
		layers.add(new GameLayer(false)); //Background layer 1
		layers.add(new GameLayer(false)); //Background layer 2
		layers.add(new GameLayer(true)); //Foreground layer (player, obstacles, enemies,)
		layers.get(0).addSprite(SpriteFactory.getInstance().getMountains());
		layers.get(2).addSprite(SpriteFactory.getInstance().getGround());
		//Make player
		player=SpriteFactory.getInstance().getPlayer(getPlayerType());
		player.setPointListener(this);
		layers.get(2).addSprite(player);
		//Make fuel fill background
		fuelFillBg = SpriteFactory.getInstance().makeFuelFillBackground();
		pointsPaint.setTextSize(40*SpriteFactory.getInstance().getScalation().y);
		pointsPaint2.setTextSize(40*SpriteFactory.getInstance().getScalation().y);
		//Make some clouds. We'll make all at once. REMEMBER: Up is negative numbers!
		/*
		 * TODO: Precalculate all bonuses here!
		 * Arrange them so that the bottommost ones (highest y-value) is added first! 
		 * Layers will terminate sprite iteration at first sprite above screen bounds, for efficiency.
		 */
		for(int i=300;i>-18000;i-=100){
			//Tweak math.random threshold to adjust number of clouds. smaller number->fewer clouds
			if(Math.random()<0.2) {
				BackgroundSprite cloud = SpriteFactory.getInstance().makeCloud();
				cloud.move((float)(screenSize.x*Math.random()), i);
				layers.get(1).addSprite(cloud);
			}
		}
		
		//Adding stars. All at once.
		for(int i=-18000;i>-36000;i-=100){
			//Tweak math.random threshold to adjust number of stars. smaller number->fewer stars
			if(Math.random()<0.1) {
				BackgroundSprite star = SpriteFactory.getInstance().makeStar();
				star.move((float)(screenSize.x*Math.random()), i);
				layers.get(1).addSprite(star);
			}
		}
		
		//Adding fuelcans.
		//TODO: Fuel cans _MUST_ be a function of achieved height, as they should become more sparse as one ascends.
				for(int i=300;i>-36000;i-=20){
					//Tweak math.random threshold to adjust number of fuel cans. smaller number->fewer fuel cans
					if(Math.random()<(0.1+i/360000)){//*BonusType.BONUS_OCCURRENCE.getMagnitude(difficulty)) {
						if(Math.random()>0.3/BonusType.BONUS_OCCURRENCE.getMagnitude(difficulty)){
						CollectableSprite fuelcan = SpriteFactory.getInstance().makeFuel();
						fuelcan.move((float)(screenSize.x*Math.random()), i);
						layers.get(2).addSprite(fuelcan);
						}
						else{
						CollectableSprite wastecan = SpriteFactory.getInstance().makeWaste();
						wastecan.move((float)(screenSize.x*Math.random()), i);
						layers.get(2).addSprite(wastecan);	
						}
					}
				}
	}
	
//	private int nextLevel = 0;
//		
//	/**
//	 * Generate new fuel cans as altitude increases
//	 */
//	public void generateFuel()
//	{
//		//heightLevel = roundInteger((int)height, 10000);
//		int heightLevel = (int)height/5000;
//		if (heightLevel == nextLevel)
//		{
//			for (int i=(int)height+5000; i>-(int)height+10000; i-=100)
//			{
//				if (Math.random() < 0.2)
//				{
//					CollectableSprite fuelcan = SpriteFactory.getInstance().makeFuel();
//					fuelcan.move((float)(screenSize.x*Math.random()), i);
//					layers.get(2).addSprite(fuelcan);
//					Log.e("FUEL", "HEEEEEEEEEEEEEEEEEER");
//				}
//			}
//			nextLevel++;
//			Log.i("LEVEL", "Level "+nextLevel+" reached");
//		}
//	}
//	
//	/**
//	 * Rounds the number to be a factor of a given number
//	 * @param number
//	 * @param round
//	 * @return
//	 */
//	public int roundInteger(int number, int factor)
//	{
//		int temp = number*factor;
//		temp = temp+(factor/2);
//		temp = temp/factor;
//		return temp*factor;		
//	}
		
		
		
	
	
	/*
	 * This is called every PaintThread.delay ms. (70ms atm)
	 */
	public void update(float dt) {
		fuelFill.setARGB(255, (int)(255*(1-player.getFuelLeftPerc())), (int)(255*player.getFuelLeftPerc()), 0);
		//generateFuel();		
		//Player stops when hitting screen sides.
		if(player.getPosition().left<0) {
			player.move(-player.getPosition().left+1, 0);
			player.setSpeed(-player.getSpeed().x, player.getSpeed().y);
		}

		if(player.getPosition().right>this.screenSize.x) {
			player.move(screenSize.x-player.getPosition().right-1, 0);
			player.setSpeed(-player.getSpeed().x, player.getSpeed().y);
		}
		
		//Update all the game layers
		for(GameLayer l : layers)
			l.update(dt);
	}	
	
	/*
	 * Draw is synchronized. Called about as often as the update()
	 */
	public void draw(Canvas canvas) {		
		//Background color is function of achieved height. 
		float function = 1-0.00005f*height <0 ? 0 : 1-0.00005f*height;
		
		
		backPaint.setARGB(255, (int)(50*function), (int)(174*function), (int)(245*function));
		canvas.drawRect(canvas.getClipBounds(), backPaint);
		//Player is below screen. Game over.
		if(player.getPosition().top>canvas.getClipBounds().bottom) {
			
			player.setSpeed(0, 0);
			player.setAcceleration(0, 0);
			
		}
		//Player is in the top half of the screen. Move clip bounds up (Camera always follows player)
		else if(player.getPosition().bottom<canvas.getClipBounds().centerY()) {
			//Move all layers a nudge down!
			float dy = canvas.getClipBounds().centerY()-player.getPosition().bottom;
			//Increment height!
			this.height+=dy;
			//Increment points?
			this.addPoints((int)(dy*10));
			
			//Background layer moves slower than the rest -> Parallax mapping.
			layers.get(0).move(0, dy*0.08f);
			layers.get(1).move(0, dy);
			layers.get(2).move(0, dy);
			
			//Move Layers to compensate
		}
		for(GameLayer l : layers)
			l.draw(canvas);
		
		/*
		 * Draw some HUD
		 */
		//FIXME  fjerner for ordens skyld//  Log.d("FUEL",player.getFuelLeftPerc()+"");
		
		fuelFillBg.draw(canvas);
		//Get scalation for terseness
		PointF scl = SpriteFactory.getInstance().getScalation();
		
		
		canvas.drawRect(30*scl.x, 60*scl.y, (float)(player.getFuelLeftPerc()*690)*scl.x, 90*scl.y, fuelFill);
		canvas.drawText(Integer.toString(points), 570*scl.x, 40*scl.y, pointsPaint2);
		canvas.drawText(Integer.toString(points), 570*scl.x, 40*scl.y, pointsPaint);
	}

	/*
	 * Touch handler sent from view. Only thing controlled in this view is the player.
	 */
	public void onTouchDown(MotionEvent event) {
		Log.d("TOUCH","touchdown");
		//On touch, calculate acceleration vector.
		player.accelerate(event.getX()-screenSize.x/2,
						  event.getY()-screenSize.y);		
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
