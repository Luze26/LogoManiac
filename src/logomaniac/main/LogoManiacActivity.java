package logomaniac.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;

public class LogoManiacActivity extends Activity {
	
	private Adapter_categories adapter;
	private MaBaseSQLite bdd;
	private ListView listView;
	private TextView top_name;
	private Util_SwitcherText switcher;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listView = (ListView) findViewById(R.id.categories);
        top_name = (TextView) findViewById(R.id.top_name);
        TextSwitcher top_info = (TextSwitcher) findViewById(R.id.top_info);
        TextView anim1 = new TextView(this);
        anim1.setTextColor(Color.WHITE);
        top_info.addView(anim1);
        TextView anim2 = new TextView(this);
        anim2.setTextColor(Color.WHITE);
    	top_info.addView(anim2);
    	
        List<Model_categorie> categories = new ArrayList<Model_categorie>();
        initData(categories);
        listView.setAdapter(adapter = new Adapter_categories(this, categories));
        
        top_name.setText(R.string.categorie);
        bdd.open();
        String first = String.valueOf(bdd.getTotalScore()) + " " + this.getString(R.string.pts);
        top_info.setText(first);
        String second = bdd.getNbLogoTrouve() + "/" + bdd.getNbLogo();
        bdd.close();
        
        Timer t = new Timer();
    	t.schedule(switcher = new Util_SwitcherText(top_info, first, second), 0, 4000);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	bdd.open();
    	List<Model_categorie> categories = bdd.getCategories();
    	String first = String.valueOf(bdd.getTotalScore()) + " " + this.getString(R.string.pts);
        String second = bdd.getNbLogoTrouve() + "/" + bdd.getNbLogo();
    	bdd.close();
    	
    	top_name.setText(R.string.categorie);
    	switcher.setFirst(first);
    	switcher.setSecond(second);
    	
    	adapter.setList(categories);
    	listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logomaniac, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_reset:
            	bdd.open();
            	bdd.resetScore();
            	List<Model_categorie> categories = bdd.getCategories();
            	String first = String.valueOf(bdd.getTotalScore()) + " " + this.getString(R.string.pts);
                String second = bdd.getNbLogoTrouve() + "/" + bdd.getNbLogo();
            	bdd.close();
            	
            	top_name.setText(R.string.categorie);
            	switcher.setFirst(first);
            	switcher.setSecond(second);
            	
            	adapter.setList(categories);
            	
            	listView.setAdapter(adapter);
                return true;
            case R.id.menu_langue:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void initData(List<Model_categorie> categories) {
    	//Init DB
    	bdd = new MaBaseSQLite(this);
    	bdd.open();
    	
    	if(bdd.getCategories().isEmpty()) {
	    	Model_categorie categorie = new Model_categorie(R.string.voitures, 0, 0, 0, -1, -1);
	    	categories.add(categorie);
	    	bdd.insertCategorie(categorie);
	    	
	    	Model_logo logo = new Model_logo (R.string.intel, R.drawable.intel, categorie);
	    	bdd.insertLogo(logo, categorie);
	    	
	    	logo = new Model_logo (R.string.caterpillar, R.drawable.caterpillar, categorie);
	    	bdd.insertLogo(logo, categorie);
	    	
	    	categorie = new Model_categorie(R.string.alcool, 0, 0, 0, -1, -1);
	    	bdd.insertCategorie(categorie);
	    	
	    	categorie = new Model_categorie(R.string.voitures2, 0, 0, 0, 4, R.string.voitures);
	    	bdd.insertCategorie(categorie);
    	}

    	bdd.close();
    }
}