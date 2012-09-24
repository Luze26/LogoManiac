package logomaniac.main;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class Adapter_logo implements ListAdapter {

	private Context context;
	private ArrayList<Model_logo> logos;
	private Drawable valid_logo_icon;
	private Drawable valid_hint_logo_icon;
	private Drawable approx_logo_icon;
	
	public Adapter_logo(Context c, ArrayList<Model_logo> logos) {
		context = c;
		this.logos = logos;
		Resources res = c.getResources();
		valid_logo_icon = res.getDrawable(R.drawable.valid_logo);
		valid_hint_logo_icon = res.getDrawable(R.drawable.valid_hint_logo);
		approx_logo_icon = res.getDrawable(R.drawable.approx_logo);
	}

	@Override
	public int getCount() {	
		return logos.size();
	}

	@Override
	public Object getItem(int position) {
		return logos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout;
		ImageView imageView;
		ImageView iconView;
		
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	layout = new LinearLayout(context);
     		layout.setOrientation(LinearLayout.VERTICAL);
     		layout.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
     		
             
            imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setTag(logos.get(position));
            imageView.setOnClickListener(new Listener_logo(context));
            
            layout.addView(imageView); 
            iconView = new ImageView(context);
            iconView.setLayoutParams(new LinearLayout.LayoutParams(85, 10));
            layout.addView(iconView);
        } else {
            layout = (LinearLayout) convertView;
            imageView = (ImageView) layout.getChildAt(0);
            iconView = (ImageView) layout.getChildAt(1);
        }

        imageView.setImageResource(logos.get(position).getImageResource());

        if(logos.get(position).getScore()!=0) {
        	
        	if(logos.get(position).getScore()==1)
        		iconView.setImageDrawable(approx_logo_icon);
        	else if (logos.get(position).getScore()==2)
        		iconView.setImageDrawable(valid_hint_logo_icon);
        	else
        		iconView.setImageDrawable(valid_logo_icon);
        }
        
        return layout;
    }

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return logos.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setLogos(ArrayList<Model_logo> logosFromCategorie) {
		logos = logosFromCategorie;	
	}

}
