package logomaniac.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import uk.ac.shef.wit.simmetrics.*;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

public class Listener_button implements OnClickListener {

	private LogoActivity activity;
	private Model_logo logo;
	private int id;
	private EditText input;
	private MaBaseSQLite bdd;
	
	public Listener_button(LogoActivity logoActivity, Model_logo logo, int id, EditText input, MaBaseSQLite bdd) {
		activity = logoActivity;
		this.logo = logo;
		this.id = id;
		this.input = input;
		this.bdd = bdd;
	}

	@Override
	public void onClick(View view) {
		
		if(id==0) {	//Check
			String value = input.getText().toString();
			value.trim();
			value = value.toLowerCase();

			if(value.length()>0) {
				bdd.open();
				
				String name = activity.getText(logo.getName()).toString();
				name = name.toLowerCase();

				if(value.equals(name)) {
					logo.setScore(3);
					bdd.updateLogo(logo);
					
					showDialog("Perfect !", true);
				}
				else {
					AbstractStringMetric metric = new ChapmanMatchingSoundex();

					float res = metric.getSimilarity(name, value);
						
					if(res>=0.75) {
						logo.setScore(1);
						bdd.updateLogo(logo);
						
						showDialog("Close", false);
					}
					else {
						showDialog("Faux !", false);
					}
				}
				
				bdd.close();
			}
		}
		else if(id==1) { //Back
			activity.finish();
		}

	}

	private void showDialog(String text, final boolean close) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(text)
		       .setCancelable(false)
		       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.dismiss();
		                if(close == true)
		                	activity.finish();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
