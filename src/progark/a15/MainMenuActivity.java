package progark.a15;

import progark.a15.model.SpriteFactory;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class MainMenuActivity extends Activity {
	LinearLayout root;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		//Give spriteFactory resources
		SpriteFactory.getInstance().setResources(this.getResources());
		calculateScreenSize();



		Button startButton = (Button) findViewById(R.id.new_game_button);

		startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Open the difficulty selection screen
				Intent intent = new Intent(MainMenuActivity.this, DiffSelectActivity.class);
				MainMenuActivity.this.startActivity(intent);
			}
		});

	}	

	private void calculateScreenSize() {
		// Get the layout id
		root = (LinearLayout) findViewById(R.id.mainmenu);
		root.post(new Runnable() { 
			public void run() { 
				Rect rect = new Rect(); 
				Window win = getWindow();  // Get the Window
				win.getDecorView().getWindowVisibleDisplayFrame(rect); 
				// Get the height of Status Bar 
				int statusBarHeight = rect.top; 
				// Get the height occupied by the decoration contents 
				int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop(); 
				// Calculate titleBarHeight by deducting statusBarHeight from contentViewTop  
				int titleBarHeight = contentViewTop - statusBarHeight; 
				Log.i("MY", "titleHeight = " + titleBarHeight + " statusHeight = " + statusBarHeight + " contentViewTop = " + contentViewTop); 

				// By now we got the height of titleBar & statusBar
				// Now lets get the screen size
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);   
				int screenHeight = metrics.heightPixels;
				int screenWidth = metrics.widthPixels;
				Log.i("MY", "Actual Screen Height = " + screenHeight + " Width = " + screenWidth);   
				// Now calculate the height that our layout can be set
				// If you know that your application doesn't have statusBar added, then don't add here also. Same applies to application bar also 
				int layoutHeight = screenHeight - (titleBarHeight + statusBarHeight);
				Log.i("MY", "Layout Height = " + layoutHeight);  

				//Initialize SpriteFactory!

				SpriteFactory.getInstance().setScalation(R.drawable.backgroundplain,
						screenWidth,
						layoutHeight);
			} 
		}); 
	}
}
