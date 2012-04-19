package progark.a15;

import progark.a15.model.SpriteFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        //Initialize SpriteFactory!
        SpriteFactory.getInstance().setResources(this.getResources());
        SpriteFactory.getInstance().setScalation(R.drawable.backgroundplain,
        		dm.widthPixels,
        		dm.heightPixels);
        
        
        Button startButton = (Button) findViewById(R.id.new_game_button);
        
        startButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Open the difficulty selection screen
				Intent intent = new Intent(MainMenuActivity.this, DiffSelectActivity.class);
				MainMenuActivity.this.startActivity(intent);
			}
		});
    }
}
