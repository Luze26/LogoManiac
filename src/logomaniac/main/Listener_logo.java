package logomaniac.main;

import java.io.Serializable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

public class Listener_logo implements OnClickListener {

	private Context context;
	
	public Listener_logo(Context c) {
		this.context = c;
	}

	@Override
	public void onClick(final View view) {
		Intent logo = new Intent(context, LogoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("LOGO", (Serializable) view.getTag());
		logo.putExtra("BUNDLE", bundle);
		context.startActivity(logo);
	}

}
