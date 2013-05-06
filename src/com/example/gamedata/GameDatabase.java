package com.example.gamedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class GameDatabase {

	//INITIALIZATION
	private static final String DB_NAME = "database.db";
	private static final String DB_TABLE = "items";
	private static final String DB_TABLE_MAPS = "maps";
	private static final String DB_TABLE_MONSTERS = "monsters";
	private static final int DB_VERSION = 1;
	
	
	private SQLiteDatabase db;
	private final Context context;
	private DatabaseHelper myDatabaseHelper;
	//
	
	//VALUES
	public static final String KEY_ID = "id";
	public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	
	public static final String KEY_TYPE = "type";
	public static final String TYPE_OPTIONS = "TEXT NOT NULL";
	public static final int TYPE_COLUMN = 1;
	public static final String KEY_ICON = "icon";
	public static final String ICON_OPTIONS = "TEXT NOT NULL";
	public static final int ICON_COLUMN = 2;
	public static final String KEY_LEVEL = "level";
	public static final String LEVEL_OPTIONS = "INTEGER NOT NULL";
	public static final int LEVEL_COLUMN = 3;
	public static final String KEY_ARMOR = "armor";
	public static final String ARMOR_OPTIONS = "INTEGER NOT NULL";
	public static final int ARMOR_COLUMN = 4;
	public static final String KEY_DAMAGE = "damage";
	public static final String DAMAGE_OPTIONS = "INTEGER NOT NULL";
	public static final int DAMAGE_COLUMN = 5;
	public static final String KEY_BUY = "buy";
	public static final String BUY_OPTIONS = "INTEGER NOT NULL";
	public static final int BUY_COLUMN = 6;
	//--
	
	//MAPS VARIABLES
	public static final String KEY_NAME = "name";
	public static final String NAME_OPTIONS = "TEXT NOT NULL";
	public static final int NAME_COLUMN = 1;
	public static final String KEY_NORMALMONSTERS = "normalmonsters";
	public static final String NORMALMONSTERS_OPTIONS = "TEXT NOT NULL";
	public static final int NORMALMONSTERS_COLUMN = 2;
	public static final String KEY_RANGEDMONSTERS = "rangedmonsters";
	public static final String RANGEDMONSTERS_OPTIONS = "TEXT NOT NULL";
	public static final int RANGEDMONSTERS_COLUMN = 3;
	public static final String KEY_SHIELDINGMONSTERS = "shieldingmonsters";
	public static final String SHIELDINGMONSTERS_OPTIONS = "TEXT NOT NULL";
	public static final int SHIELDINGMONSTERS_COLUMN = 4;
	public static final String KEY_BOSS = "boss";
	public static final String BOSS_OPTIONS = "TEXT NOT NULL";
	public static final int BOSSMONSTERS_COLUMN = 5;
	public static final String KEY_BASEGOLD = "basegold";
	public static final String BASEGOLD_OPTIONS = "TEXT NOT NULL";
	public static final int BASEGOLD_COLUMN = 6;
	public static final String KEY_BASEEXPERIENCE = "baseexperience";
	public static final String BASEEXPERIENCE_OPTIONS = "TEXT NOT NULL";
	public static final int BASEEXPERIENCE_COLUMN = 7;
	//--
	
	//MONSTER VARIABLES
	public static final String KEY_MONSTERICON = "monstericon";
	public static final String MONSTERICON_OPTIONS = "TEXT NOT NULL";
	public static final int MONSTERICON_COLUMN = 1;	
	public static final String KEY_MONSTERTYPE = "monstertype";
	public static final String MONSTERTYPE_OPTIONS = "TEXT NOT NULL";
	public static final int MONSTERTYPE_COLUMN = 2;
	public static final String KEY_MONSTERHEALTH = "monsterhealth";
	public static final String MONSTERHEALTH_OPTIONS = "TEXT NOT NULL";
	public static final int MONSTERHEALTH_COLUMN = 3;
	public static final String KEY_MONSTERDAMAGE = "monsterdamage";
	public static final String MONSTERDAMAGE_OPTIONS = "TEXT NOT NULL";
	public static final int MONSTERDAMAGE_COLUMN = 4;
	public static final String KEY_MONSTERARMOR = "monsterarmor";
	public static final String MONSTERARMOR_OPTIONS = "TEXT NOT NULL";
	public static final int MONSTERARMOR_COLUMN = 5;
	public static final String KEY_MONSTERCOORDS = "monstercoords";
	public static final String MONSTERCOORDS_OPTIONS = "TEXT NOT NULL";
	public static final int MONSTERCOORDS_COLUMN = 6;
	//--
	
	
	//CREATE TABLE ITEMS
	private static final String DB_CREATE = "create table " +
	DB_TABLE + " (" +
	KEY_ID + " " + ID_OPTIONS + ", " +
	KEY_TYPE + " " + TYPE_OPTIONS + ", " +
	KEY_ICON + " " + ICON_OPTIONS + ", " +
	KEY_LEVEL + " " + LEVEL_OPTIONS + ", " +
	KEY_ARMOR + " " + ARMOR_OPTIONS + ", " +
	KEY_DAMAGE + " " + DAMAGE_OPTIONS + ", " +
	KEY_BUY + " " + BUY_OPTIONS +
	");";
	//--
	
	//CREATE TABLE MAPS
	private static final String DB_CREATE_MAPS = "create table " +
	DB_TABLE_MAPS + " (" +
	KEY_ID + " " + ID_OPTIONS + ", " +
	KEY_NAME + " " + NAME_OPTIONS + ", " +
	KEY_NORMALMONSTERS + " " + NORMALMONSTERS_OPTIONS + ", " +
	KEY_RANGEDMONSTERS + " " + RANGEDMONSTERS_OPTIONS + ", " +
	KEY_SHIELDINGMONSTERS + " " + SHIELDINGMONSTERS_OPTIONS + ", " +
	KEY_BOSS + " " + BOSS_OPTIONS + ", " +
	KEY_BASEGOLD + " " + BASEGOLD_OPTIONS + ", " +
	KEY_BASEEXPERIENCE + " " + BASEEXPERIENCE_OPTIONS +
	");";
	//
	
	//CREATE TABLE MONSTERS
	private static final String DB_CREATE_MONSTERS = "create table " +
	DB_TABLE_MONSTERS + " (" +
	KEY_ID + " " + ID_OPTIONS + ", " +
	KEY_MONSTERICON + " " + MONSTERICON_OPTIONS + ", " +
	KEY_MONSTERTYPE + " " + MONSTERTYPE_OPTIONS + ", " +
	KEY_MONSTERHEALTH + " " + MONSTERHEALTH_OPTIONS + ", " +
	KEY_MONSTERDAMAGE + " " + MONSTERDAMAGE_OPTIONS + ", " +
	KEY_MONSTERARMOR + " " + MONSTERARMOR_OPTIONS + ", " +
	KEY_MONSTERCOORDS + " " + MONSTERCOORDS_OPTIONS + 
	");";
	//--
	
	//CONSTRUCTOR
	public GameDatabase(Context _context) {
	    context = _context;
	    myDatabaseHelper = new DatabaseHelper(_context, 
	            DB_NAME, null, DB_VERSION);
	}
	//--
	
	
	//OPEN CONNECTION
	 public GameDatabase open() {
	        db = myDatabaseHelper.getWritableDatabase();
	        return this;
	    }
	 //--
	 
	 //CLOSE CONNECTION
	   public void close(){
	        db.close();
	    }
	 //--
	   
	 //INSERT ITEM
	   public long insertItem(Item item) {
		    ContentValues newItemValues = new ContentValues();
		    newItemValues.put(KEY_TYPE, item.getType());
		    newItemValues.put(KEY_ICON, item.getIcon());
		    newItemValues.put(KEY_LEVEL, item.getLevel());
		    newItemValues.put(KEY_ARMOR, item.getArmor());
		    newItemValues.put(KEY_DAMAGE, item.getDamage());
		    newItemValues.put(KEY_BUY, item.getBuy());
		    return db.insert(DB_TABLE, null, newItemValues);
		}  
	 //--
	   
	 //INSERT MAP
	public long insertMap(Map map){
		ContentValues newItemValues = new ContentValues();
		newItemValues.put(KEY_NAME, map.getName());
		newItemValues.put(KEY_NORMALMONSTERS, map.getNormalmonsters());
		newItemValues.put(KEY_RANGEDMONSTERS, map.getRangedmonsters());
		newItemValues.put(KEY_SHIELDINGMONSTERS, map.getShieldingmonsters());
		newItemValues.put(KEY_BOSS, map.getBoss());
		newItemValues.put(KEY_BASEGOLD, map.getBasegold());
		newItemValues.put(KEY_BASEEXPERIENCE, map.getBaseexperience());
		return db.insert(DB_TABLE_MAPS, null, newItemValues);
	}
	   
	public long insertMonster(Monster monster){
		ContentValues newItemValues = new ContentValues();
		newItemValues.put(KEY_MONSTERICON, monster.getIcon());
		newItemValues.put(KEY_MONSTERTYPE, monster.getType());
		newItemValues.put(KEY_MONSTERHEALTH, monster.getHealth());
		newItemValues.put(KEY_MONSTERDAMAGE, monster.getDamage());
		newItemValues.put(KEY_MONSTERARMOR, monster.getArmor());
		newItemValues.put(KEY_MONSTERCOORDS, monster.getCoords());
		return db.insert(DB_TABLE_MONSTERS, null, newItemValues);
	}
	 //--
	
	//GET ITEM

	   
	   
	   public Item getItem(String icon) {
		   String[] columns = {KEY_ID, KEY_TYPE, KEY_ICON, KEY_LEVEL, KEY_ARMOR, KEY_DAMAGE, KEY_BUY};
		   Cursor cursor = db.query(DB_TABLE, columns, KEY_ICON + "='" + icon + "'",
		   null, null, null, null);
		  Item item=null;
		   if(cursor != null && cursor.moveToFirst()) {
			   item= new Item(cursor.getString(TYPE_COLUMN), 
					     cursor.getString(ICON_COLUMN),
					     cursor.getString(LEVEL_COLUMN),
					     cursor.getString(ARMOR_COLUMN),
					     cursor.getString(DAMAGE_COLUMN),
					     cursor.getString(BUY_COLUMN)
							   );
		   }
		   return item;
	   }
	   
	   
	   
	   public Cursor getAllItems() {
		   String[] columns = {KEY_ID, KEY_TYPE, KEY_ICON, KEY_LEVEL, KEY_ARMOR, KEY_DAMAGE, KEY_BUY};
		    return db.query(DB_TABLE, columns,
		            null, null, null, null, null);
		}
	   
	   
	   public Cursor getAllMaps(){
		   String[] columns = {KEY_ID, KEY_NAME, KEY_NORMALMONSTERS, KEY_RANGEDMONSTERS, KEY_SHIELDINGMONSTERS, KEY_BOSS, KEY_BASEGOLD, KEY_BASEEXPERIENCE};
		   return db.query(DB_TABLE_MAPS, columns, null, null, null, null, null);
	   }
	   
	   public Cursor getAllMonsters(){
		   String[] columns = {KEY_ID, KEY_MONSTERICON, KEY_MONSTERTYPE, KEY_MONSTERHEALTH, KEY_MONSTERDAMAGE, KEY_MONSTERARMOR, KEY_MONSTERCOORDS};
		   return db.query(DB_TABLE_MONSTERS, columns, null, null, null, null, null);
	   }
	//--
	   
	   
	//HELPER CLASS
	private static class DatabaseHelper extends SQLiteOpenHelper {

	    public DatabaseHelper(Context context, String name,
	            CursorFactory factory, int version) {
	        super(context, name, factory, version);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase _db) {
	        _db.execSQL(DB_CREATE);
	        _db.execSQL(DB_CREATE_MAPS);
	        _db.execSQL(DB_CREATE_MONSTERS);
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase _db, int oldVer, int newVer) {
	        _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
	        onCreate(_db);
	        
	      
	    }
	}
	//--
	
}
