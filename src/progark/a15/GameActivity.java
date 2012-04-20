package progark.a15;

import progark.a15.model.SpriteFactory;
import progark.a15.viewController.GameView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

public class GameActivity extends Activity {
	private GameView gV;
	private Bundle lastExtras;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		gV = (GameView) this.findViewById(R.id.gameView);
		lastExtras = getIntent().getExtras();
		gV.setGameSettings(lastExtras);
	}

	@Override
	public void onBackPressed() {
		//Monitor key presses

		Log.d("BACK BUTTON PUSH","Pausing game and showing dialog");
		//Pause game
		gV.pause();
		//Create pause dialog.
		//TODO: Change setCancelable(false) to true and make it work!
		this.showDialog(1);

	}

	protected Dialog onCreateDialog(int id) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
		builder.setMessage("Game paused")
		.setCancelable(false)
		.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(GameActivity.this, GameActivity.class);
				//insert last known user settings
				intent.putExtras(lastExtras);
				startActivity(intent);
			}
		})
		.setNeutralButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//Start main menu activity
				Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
				startActivity(intent);
			}
		});
		AlertDialog alert = builder.create();
		return alert;
	}

}
