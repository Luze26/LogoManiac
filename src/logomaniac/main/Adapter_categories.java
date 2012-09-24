package logomaniac.main;

import java.util.List;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class Adapter_categories implements ListAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Model_categorie> categories;
	
	public Adapter_categories(Context c, List<Model_categorie> list_categories) {
		context = c;
		inflater = LayoutInflater.from(context);
		categories = list_categories;
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		//if(convertView == null) {
			holder = new ViewHolder();
			if(categories.get(position).isLock()) {
				convertView = inflater.inflate(R.layout.itemcategorieslock, null);
				holder.lock = (TextView) convertView.findViewById(R.id.categorie_lock);
			}
			else {
				convertView = inflater.inflate(R.layout.itemcategories, null);
				holder.score = (TextView) convertView.findViewById(R.id.categorie_score);
				holder.avancement = (TextView) convertView.findViewById(R.id.categorie_avancement);
				convertView.setTag(R.categorie.id_name, categories.get(position));
				convertView.setOnClickListener(new Listener_categorie((Activity) context));
			}
			convertView.setTag(R.holder.id_name, holder);
			holder.titre = (TextView) convertView.findViewById(R.id.categorie_titre);
			
			
		/*} else {
			holder = (ViewHolder) convertView.getTag(R.holder.id_name);
		}*/

		holder.titre.setText(categories.get(position).getTitre());
		if(!categories.get(position).isLock()) {
			holder.score.setText(String.valueOf((categories.get(position).getScore())) + " " + context.getText(R.string.pts));
			holder.avancement.setText(categories.get(position).getTrouve() + "/" + categories.get(position).getTotal());
		}
		else {
			holder.lock.setText(categories.get(position).getLock_score() + " " + context.getText(R.string.pts) + " " + context.getText(R.string.unlock_in) + " " + context.getText(categories.get(position).getLock_titre()) + " " + context.getText(R.string.unlock_to));
		}
		
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return categories.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setList(List<Model_categorie> categories) {
		this.categories = categories;
	}
}

class ViewHolder {
	
	TextView titre;
	TextView score;
	TextView avancement;
	TextView lock;
	
}