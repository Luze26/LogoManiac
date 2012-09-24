package logomaniac.main;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class Listener_back implements OnClickListener {

	private Activity activity;
	
	public Listener_back(Activity quizActivity) {
		activity = quizActivity;
	}

	@Override
	public void onClick(View view) {
		activity.finish();
	}

}
