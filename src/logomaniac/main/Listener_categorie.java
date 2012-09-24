package logomaniac.main;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Listener_categorie implements OnClickListener {

	private Activity activity;
	
	public Listener_categorie(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View view) {
		Intent quiz = new Intent(activity, QuizActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("CAT", (Serializable) view.getTag(R.categorie.id_name));
		quiz.putExtra("BUNDLE", bundle);
		activity.startActivity(quiz);
	}

}
