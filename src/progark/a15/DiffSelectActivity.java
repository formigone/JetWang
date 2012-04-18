package progark.a15;

import progark.a15.model.Globals;
import progark.a15.model.PlayerType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class DiffSelectActivity extends Activity {
	private RadioGroup diffRadioGroup;
	private RadioGroup charRadioGroup;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffselect);
        
        diffRadioGroup = (RadioGroup) findViewById(R.id.diff_radiogroup);
        charRadioGroup = (RadioGroup) findViewById(R.id.char_radiogroup);
        
        Button startButton = (Button) findViewById(R.id.start_button);
        
        startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Check if both difficulty and character are selected
				int difficulty = -1;
				int character = -1;
				PlayerType playerType;
				
				switch (diffRadioGroup.getCheckedRadioButtonId()) {
				case R.id.easy_radio:
					difficulty = 0;
					break;
				case R.id.medium_radio:
					difficulty = 1;
					break;
				case R.id.hard_radio:
					difficulty = 2;
				}
				
				switch (charRadioGroup.getCheckedRadioButtonId()) {
				case R.id.first_char_radio:
					character = 0;
					playerType = PlayerType.PLAYER_GREEN;
					break;
				case R.id.second_char_radio:
					character = 1;
					playerType = PlayerType.PLAYER_RED;
					break;
				default:
					playerType = PlayerType.PLAYER_GREEN;
					break;
				}
				
				if (difficulty != -1 && character != -1) {
					Globals.setDifficulty(difficulty);
					Globals.setPlayerType(playerType);
					
					Intent intent = new Intent(DiffSelectActivity.this, GameActivity.class);
					DiffSelectActivity.this.startActivity(intent);
				}
			}
		});
        
    }
}