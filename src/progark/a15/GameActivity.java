package progark.a15;

import progark.a15.model.SpriteFactory;
import progark.a15.viewController.GameView;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class GameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        GameView gV = (GameView) this.findViewById(R.id.gameView);
        
        gV.setGameSettings(getIntent().getExtras());
        
    }
}
