package progark.a15;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        
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
