package logomaniac.main;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextSwitcher;
import android.widget.TextView;

public class QuizActivity extends Activity {
	
	private TextView top_name;
	private TextSwitcher top_info;
	private MaBaseSQLite bdd;
	private Model_categorie categorie;
	private Util_SwitcherText switcher;
	private GridView gridview;
	private Adapter_logo adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        gridview = (GridView) findViewById(R.id.grille);
        
        Bundle bundle = this.getIntent().getBundleExtra("BUNDLE");
        if(bundle!=null) {
        	Button back = (Button) findViewById(R.id.quiz_back);
        	back.setOnClickListener(new Listener_back(this));
        	
        	top_name = (TextView) findViewById(R.id.top_name);
        	top_info = (TextSwitcher) findViewById(R.id.top_info);
        	TextView anim1 = new TextView(this);
            anim1.setTextColor(Color.WHITE);
            top_info.addView(anim1);
            TextView anim2 = new TextView(this);
            anim2.setTextColor(Color.WHITE);
        	top_info.addView(anim2);

        	Animation in = AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_in);
            Animation out = AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_out);
            
        	top_info.setInAnimation(in);
        	top_info.setOutAnimation(out);

        	bdd = new MaBaseSQLite(this);
        	bdd.open();
        	
        	categorie = (Model_categorie) bundle.getSerializable("CAT");
        	gridview.setAdapter(adapter=new Adapter_logo(this, bdd.getLogosFromCategorie(categorie)));

        	top_name.setText(categorie.getTitre());
        	String first = String.valueOf(bdd.getScoreFromCategorie(categorie));
        	top_info.setText(first);
        	String second =  bdd.getNbLogoTrouveFromCategorie(categorie) + "/" + bdd.getNbLogoFromCategorie(categorie);
        	
        	bdd.close();
        	Timer t = new Timer();
        	t.schedule(switcher = new Util_SwitcherText(top_info, first, second), 0, 4000);

        }
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	bdd.open();
    	String first = String.valueOf(bdd.getScoreFromCategorie(categorie)) + " " + this.getString(R.string.pts);
        String second = bdd.getNbLogoTrouveFromCategorie(categorie) + "/" + bdd.getNbLogoFromCategorie(categorie);
    	adapter.setLogos(bdd.getLogosFromCategorie(categorie));
        bdd.close();
    	
    	top_name.setText(categorie.getTitre());
    	switcher.setFirst(first);
    	switcher.setSecond(second);
    	
    	gridview.setAdapter(adapter);
    }
    
}
