package progark.a15;

import progark.a15.model.SpriteFactory;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class MainMenuActivity extends Activity {
	LinearLayout root;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		//HighScores scores = new HighScores(this);
		//scores.writeScore("Amateur", 1336);
		//Give spriteFactory resources
		SpriteFactory.getInstance().setResources(this.getResources());
		calculateScreenSize();
		makeHSList();
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
	private void makeHSList() {
		ListView scores = (ListView) this.findViewById(R.id.highScoreList);
		HighScores hs = new HighScores(this);
		Log.d("HSLIST","Starting to generate list of high scores");
		scores.setAdapter(new ArrayAdapter<HighScore>(this, R.layout.highscore_list_item, hs.getTopScores(5)) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Log.d("GETTING","List element");
				View row;
				LayoutInflater mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				if (null == convertView) {
					row = mInflater.inflate(R.layout.highscore_list_item, null);
				} else {
					row = convertView;
				}

				TextView textName = (TextView) row.findViewById(R.id.score_name);
				textName.setText(this.getItem(position).getName());
				TextView textScore = (TextView) row.findViewById(R.id.score_value);
				textScore.setText(this.getItem(position).getScore()+"");
				textName.setTextColor(Color.BLACK);
				textScore.setTextColor(Color.BLACK);
				
				
				Log.d("ADDINGTEXT",this.getItem(position).getName()+":"+this.getItem(position).getScore());
				return row;
			}
		});
		Log.d("ELEMENTS","Number of elements: "+scores.getAdapter().getCount()+"."+hs.getTopScores(5).size());

	}


}
