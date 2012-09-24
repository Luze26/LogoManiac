package logomaniac.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LogoActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        
        Bundle bundle = this.getIntent().getBundleExtra("BUNDLE");
        if(bundle!=null) {
        	Model_logo logo = (Model_logo) bundle.getSerializable("LOGO");
	        ImageView logoView = (ImageView) findViewById(R.id.logo_view);
	        logoView.setBackgroundResource(logo.getImageResource());
	        
	        EditText input = (EditText) findViewById(R.id.logo_input);
	        Button check = (Button) findViewById(R.id.logo_check);
	        Button back = (Button) findViewById(R.id.logo_back);
	        Button hint = (Button) findViewById(R.id.logo_hint);
	        
	        MaBaseSQLite bdd = new MaBaseSQLite(this);
	        
	        check.setOnClickListener(new Listener_button(this, logo, 0, input, bdd));
	        back.setOnClickListener(new Listener_button(this, logo, 1, input, bdd));
        }
	}
}
