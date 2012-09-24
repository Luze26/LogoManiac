package logomaniac.main;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaBaseSQLite extends SQLiteOpenHelper {
	 
	private SQLiteDatabase bdd;
	
	private static final String TABLE_LOGOS = "table_logos";
	private static final String COL_NAME = "name";
	private static final String COL_IMAGERESOURCE = "imageResource";
	private static final String COL_SCORE = "score";
	private static final String COL_CAT = "cat";
	
	private static final String TABLE_CAT = "table_cat";
	private static final String COL_TITRE = "titre";
	private static final String COL_LOCK = "lock";
	private static final String COL_LOCK_TITRE = "lock_titre";
	
	public MaBaseSQLite(Context context) {
		super(context, "logo.db", null, 13);
	}
	
	public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_LOGOS + " (" + COL_NAME + " INTEGER PRIMARY KEY, " + COL_IMAGERESOURCE + " INTEGER NOT NULL, " + COL_SCORE + " INTEGER NOT NULL, " + COL_CAT + " TEXT NOT NULL REFERENCES " + TABLE_CAT + "(" + COL_TITRE + ")" + ");");
		db.execSQL("CREATE TABLE " + TABLE_CAT + " (" + COL_TITRE + " INTEGER PRIMARY KEY, " + COL_LOCK + " INTEGER NOT NULL, " + COL_LOCK_TITRE + " INTEGER NOT NULL);");
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_LOGOS + ";");
		db.execSQL("DROP TABLE " + TABLE_CAT + ";");
		onCreate(db);
	}
	
	public void open(){
		bdd = this.getWritableDatabase();	
	}
	
	public void close(){
		bdd.close();
	}
	
	public long insertCategorie(Model_categorie categorie){
		ContentValues values = new ContentValues();
		
		values.put(COL_TITRE, categorie.getTitre());
		values.put(COL_LOCK, categorie.getLock_score());
		values.put(COL_LOCK_TITRE, categorie.getLock_titre());
		
		return bdd.insert(TABLE_CAT, null, values);
	}
	
	public long insertLogo(Model_logo logo, Model_categorie categorie){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, logo.getName());
		values.put(COL_IMAGERESOURCE, logo.getImageResource());
		values.put(COL_SCORE, logo.getScore());
		values.put(COL_CAT, categorie.getTitre());
		
		return bdd.insert(TABLE_LOGOS, null, values);
	}
	
	public int updateLogo(Model_logo logo){
		ContentValues values = new ContentValues();
		
		values.put(COL_NAME, logo.getName());
		values.put(COL_IMAGERESOURCE, logo.getImageResource());
		values.put(COL_SCORE, logo.getScore());
		values.put(COL_CAT, logo.getCategorie().getTitre());
		
		return bdd.update(TABLE_LOGOS, values, COL_NAME + " = '" +logo.getName() + "'", null);
	}

	public List<Model_categorie> getCategories() {
		Cursor c = bdd.query(TABLE_CAT, new String[] {COL_TITRE, COL_LOCK, COL_LOCK_TITRE}, null, null, null, null, null);

		List<Model_categorie> categories = new ArrayList<Model_categorie>();

		if(c.moveToFirst()) {
			do {
				Cursor c2 = bdd.query(TABLE_LOGOS, new String[] {COL_SCORE}, COL_CAT + " = '" + c.getString(0) + "'", null, null, null, null);
				int score = 0, total = 0, trouve = 0;
				
				if(c2.moveToFirst()) {
					do {
						if(c2.getInt(0)>1)
							trouve++;
						total++;
						score+=c2.getInt(0);
					} while(c2.moveToNext());
				}
				
				Model_categorie cat = new Model_categorie(c.getInt(0), score, trouve, total, c.getInt(1), c.getInt(2));
				categories.add(cat);
			} while(c.moveToNext());
		}
		
		for(Model_categorie cat : categories) {
			if(cat.getLock_score()!=-1) {
				for(Model_categorie cat2 : categories) {
					if(cat2.getTitre() == cat.getLock_titre()) {
						if(cat2.getScore()<cat.getLock_score()) {
							cat.setLock(true);
						}
						break;
					}
				}
			}
		}
		
		return categories;
	}

	public ArrayList<Model_logo> getLogosFromCategorie(Model_categorie categorie) {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {COL_NAME, COL_IMAGERESOURCE, COL_SCORE, COL_CAT}, COL_CAT + " = '" + categorie.getTitre() + "'", null, null, null, null);
		ArrayList<Model_logo> logos = new ArrayList<Model_logo>();
		
		if(c.moveToFirst()) {
			do {
				Model_logo logo = new Model_logo(c.getInt(0), c.getInt(1), c.getInt(2), categorie);
				logos.add(logo);
			} while(c.moveToNext());
		}
		
		return logos;
	}

	public int getTotalScore() {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {"SUM(" + COL_SCORE + ")"}, null, null, null, null, null);
		
		if(c.moveToFirst())
			return c.getInt(0);
		else
			return 0;
	}

	public int getScoreFromCategorie(Model_categorie categorie) {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {"SUM(" + COL_SCORE + ")"}, COL_CAT + " = '" + categorie.getTitre() + "'", null, null, null, null);
		
		if(c.moveToFirst())
			return c.getInt(0);
		else
			return 0;
	}

	public int getNbLogoTrouveFromCategorie(Model_categorie categorie) {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {COL_NAME}, COL_CAT + " = '" + categorie.getTitre() + "' AND " + COL_SCORE + " >= 2", null, null, null, null);
		return c.getCount();
	}
	
	public int getNbLogoFromCategorie(Model_categorie categorie) {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {COL_NAME}, COL_CAT + " = '" + categorie.getTitre() + "'", null, null, null, null);
		return c.getCount();
	}

	public int getNbLogo() {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {COL_NAME}, null, null, null, null, null);
		return c.getCount();
	}

	public int getNbLogoTrouve() {
		Cursor c = bdd.query(TABLE_LOGOS, new String[] {COL_NAME}, COL_SCORE + " >= 2", null, null, null, null);
		return c.getCount();
	}
	
	public void resetScore() {
		ContentValues values = new ContentValues();
		
		values.put(COL_SCORE, 0);
		
		bdd.update(TABLE_LOGOS, values, null, null);
	}
}