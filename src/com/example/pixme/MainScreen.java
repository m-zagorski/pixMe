package com.example.pixme;



import com.example.combat.OneCharacterMaps;
import com.example.gamedata.GameDatabase;
import com.example.gamedata.GameSharedPreferences;
import com.example.gamedata.Item;
import com.example.gamedata.Map;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainScreen extends Activity {
	GameSharedPreferences appPrefs;
	GameDatabase gameDatabase=null;
	MediaPlayer mainMusic;
	
	ImageButton musicButton;
	int length;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		Button loadgame = (Button) findViewById(R.id.load_game);  
	    Button newgame = (Button) findViewById(R.id.new_game); 
	    Button credits = (Button) findViewById(R.id.credits); 
	    Button exit = (Button) findViewById(R.id.exit); 
	    musicButton= (ImageButton) findViewById(R.id.musicButton);
	    musicButton.setBackgroundResource(R.drawable.empty_shop);
	    
		Context context = getApplicationContext();
	    appPrefs = new GameSharedPreferences(context);

	    
	    if(appPrefs.getMusicStatus().equals("yes")){
	    	musicButton.setImageResource(getResources().getIdentifier("music_on", "drawable", getPackageName()));	
	    }
	    else{
	    	musicButton.setImageResource(getResources().getIdentifier("music_off", "drawable", getPackageName()));	
	    }

	    Typeface font = Typeface.createFromAsset(getAssets(), "bloodthirsty.ttf");  
	    loadgame.setTypeface(font); 
	    newgame.setTypeface(font);
	    credits.setTypeface(font);
	    exit.setTypeface(font);
	    
	    mainMusic = MediaPlayer.create(this, R.raw.menu);
	    if(appPrefs.getMusicStatus().equals("yes")){
        mainMusic.start();
        mainMusic.setLooping(true);
	    }
	    
	    //DATABASE INITIALIZATION
	    gameDatabase=new GameDatabase(this);
		gameDatabase.open();
		
		if(appPrefs.ifFirstLaunch()){
		fillDatabase();
		appPrefs.changeFirstLaunch();
		}
		
		gameDatabase.close();	    
	    //--
	    
	    
	    
	    
	    if(appPrefs.saveExsists().equals("false")){
	    loadgame.setTextColor(Color.GRAY);
	    loadgame.setEnabled(false);
	    }
	    
	    
	    loadgame.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(MainScreen.this, Tawern.class));
            }
        });	    
	    newgame.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(MainScreen.this, ClassChoose.class));
            }
        });
	    credits.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(MainScreen.this, Tawern.class));
            }
        });
	    exit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	finish();
            	System.exit(0);
            }
        });
		musicButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(appPrefs.getMusicStatus().equals("yes")){
            		musicButton.setImageResource(getResources().getIdentifier("music_off", "drawable", getPackageName()));
            		appPrefs.updateMusic();
            		mainMusic.pause();
            		length=mainMusic.getCurrentPosition();
            	}
            	else{
            		musicButton.setImageResource(getResources().getIdentifier("music_on", "drawable", getPackageName()));	
            		appPrefs.updateMusic();
            		mainMusic.seekTo(length);
            		mainMusic.start();
            	}
            }
            });
		
	    

		
		
	}       

	
	private void fillDatabase(){
		gameDatabase.insertItem(new Item("attack", "poison_sword", "15", "0", "5", "1500"));
		gameDatabase.insertItem(new Item("defense", "golden_shield", "10", "2", "0", "1000"));
		
		gameDatabase.insertItem(new Item("attack", "avalanche_sword", "3", "1", "0", "300"));
		gameDatabase.insertItem(new Item("attack", "bat_staff", "5", "2", "0", "200"));
		gameDatabase.insertItem(new Item("attack", "beam_staff", "3", "1", "0", "100"));
		gameDatabase.insertItem(new Item("attack", "bloody_scythe", "25", "25", "0", "300000"));
		gameDatabase.insertItem(new Item("attack", "bloody_sword", "10", "3", "0", "100000"));
		gameDatabase.insertItem(new Item("attack", "bloody_vest", "15", "4", "0", "4000"));
		gameDatabase.insertItem(new Item("attack", "blue_sword", "12", "2", "0", "5000"));
		gameDatabase.insertItem(new Item("attack", "bone_staff", "11", "1", "0", "100000"));
		gameDatabase.insertItem(new Item("attack", "bronze_scythe", "7", "4", "0", "40000"));
		gameDatabase.insertItem(new Item("attack", "burning_blade", "9", "3", "0", "10005"));
		gameDatabase.insertItem(new Item("attack", "cains_staff", "20", "5", "0", "50000"));
		gameDatabase.insertItem(new Item("attack", "choosen_one", "21", "9", "0", "1000000"));
		gameDatabase.insertItem(new Item("attack", "cold_big_sword", "24", "4", "0", "5912"));
		gameDatabase.insertItem(new Item("attack", "cold_sword", "22", "5", "0", "50000"));
		gameDatabase.insertItem(new Item("attack", "cosmic_sword", "25", "8", "0", "90000"));
		gameDatabase.insertItem(new Item("defense", "damaged_boots", "29", "0", "3", "3000"));
		gameDatabase.insertItem(new Item("defense", "dark_hands", "11", "0", "5", "50000"));
		gameDatabase.insertItem(new Item("attack", "death_bringer", "5", "4", "0", "40000"));
		gameDatabase.insertItem(new Item("defense", "death_cape", "20", "0", "10", "200000"));
		gameDatabase.insertItem(new Item("defense", "death_chest", "20", "0", "10", "200000"));
		gameDatabase.insertItem(new Item("attack", "death_staff", "20", "10", "0", "200000"));
		gameDatabase.insertItem(new Item("attack", "death_sword", "20", "10", "0", "200000"));
		gameDatabase.insertItem(new Item("defense", "demon_boots", "22", "0", "9", "220000"));
		gameDatabase.insertItem(new Item("attack", "demons_axe", "22", "9", "0", "220000"));
		gameDatabase.insertItem(new Item("defence", "demons_hands", "22", "0", "8", "220000"));
		gameDatabase.insertItem(new Item("attack", "double_blade", "5", "4", "0", "100"));
		gameDatabase.insertItem(new Item("defense", "fallen_angel_chest", "45", "15", "15", "9251289"));
		gameDatabase.insertItem(new Item("attack", "fallen_blade", "40", "5", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "fallen_skeletan_sword", "35", "19", "0", "82914"));
		gameDatabase.insertItem(new Item("attack", "fast_sword", "7", "6", "0", "20005"));
		gameDatabase.insertItem(new Item("attack", "fire_axe", "8", "8", "0", "300035"));
		gameDatabase.insertItem(new Item("attack", "fire_devastator", "10", "4", "0", "10040"));
		gameDatabase.insertItem(new Item("attack", "fire_sword", "12", "5", "0", "10000"));
		gameDatabase.insertItem(new Item("attack", "golden_bow", "11", "6", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "hatchet", "1", "1", "0", "50"));
		gameDatabase.insertItem(new Item("attack", "ice_crusher", "15", "2", "0", "10050"));
		gameDatabase.insertItem(new Item("attack", "ice_kings_sword", "20", "3", "0", "1200"));
		gameDatabase.insertItem(new Item("attack", "icy_blade", "11", "2", "0", "1050"));
		gameDatabase.insertItem(new Item("attack", "kings_staff", "12", "1", "0", "5000"));
		gameDatabase.insertItem(new Item("defense", "knight_greavers", "15", "0", "5", "106600"));
		gameDatabase.insertItem(new Item("defense", "knights_hands", "16", "0", "4", "10020"));
		gameDatabase.insertItem(new Item("attack", "large_red_staff", "29", "3", "0", "10400"));
		gameDatabase.insertItem(new Item("defense", "light_boots", "22", "0", "5", "10009"));
		gameDatabase.insertItem(new Item("attack", "lightning_staff", "11", "4", "0", "10100"));
		gameDatabase.insertItem(new Item("attack", "lightning_sword", "12", "5", "0", "122000"));
		gameDatabase.insertItem(new Item("attack", "lying_bow", "7", "7", "0", "100220"));
		gameDatabase.insertItem(new Item("defense", "magic_angel_chest", "45", "20", "20", "900000"));
		gameDatabase.insertItem(new Item("defense", "magic_demonic_chest", "50", "50", "50", "1000000"));
		gameDatabase.insertItem(new Item("attack", "magic_bone_blade", "50", "55", "0", "1000000"));
		gameDatabase.insertItem(new Item("attack", "majestic_axe", "5", "4", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "majestic_sword", "2", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "mallet", "10", "5", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "marvelous_hand", "11", "0", "6", "1000"));
		gameDatabase.insertItem(new Item("attack", "medusas_sword", "12", "6", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "missle_staff", "13", "2", "0", "50"));
		gameDatabase.insertItem(new Item("attack", "molten_axe", "1", "6", "0", "10005"));
		gameDatabase.insertItem(new Item("attack", "molten_bow", "2", "1", "0", "20"));
		gameDatabase.insertItem(new Item("attack", "molten_staff", "4", "3", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "molten_sword", "5", "5", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "monster_gloves", "10", "0", "5", "10000"));
		gameDatabase.insertItem(new Item("attack", "moss_axe", "10", "1", "0", "50"));
		gameDatabase.insertItem(new Item("defense", "moss_boots", "10", "0", "5", "104400"));
		gameDatabase.insertItem(new Item("attack", "moss_bow", "10", "5", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "moss_sword", "10", "3", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "normal_green_staff", "11", "2", "0", "777000"));
		gameDatabase.insertItem(new Item("attack", "normal_magic_blade", "5", "3", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "normal_staff", "9", "1", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "old_staff", "1", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "overflamed_blade", "4", "4", "0", "100550"));
		gameDatabase.insertItem(new Item("defense", "poison_hand", "12", "0", "5", "1000555"));
		gameDatabase.insertItem(new Item("attack", "poison_staff", "14", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "poison_bow", "10", "3", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "purple_snake_staff", "6", "1", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "rainbow_bow", "20", "5", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "red_hammer", "21", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "santas_boots", "24", "0", "24", "240000"));
		gameDatabase.insertItem(new Item("attack", "scythe", "1", "20", "0", "2000000"));
		gameDatabase.insertItem(new Item("attack", "skeletan_bow", "2", "4", "0", "4000"));
		gameDatabase.insertItem(new Item("attack", "skull_staff", "10", "6", "0", "7344"));
		gameDatabase.insertItem(new Item("attack", "snake_sword", "2", "3", "0", "300"));
		gameDatabase.insertItem(new Item("attack", "standard_bow", "1", "1", "0", "100"));
		gameDatabase.insertItem(new Item("attack", "taste_ball", "1", "2", "0", "150"));
		gameDatabase.insertItem(new Item("attack", "unholy_burning_blade", "10", "8", "0", "50000"));
		gameDatabase.insertItem(new Item("attack", "unsubsequel_sword", "10", "4", "0", "15100"));
		gameDatabase.insertItem(new Item("attack", "water_axe", "2", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "water_bow", "5", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "water_staff", "15", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "weed_bow", "6", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("attack", "weed_kings_bow", "7", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "white_angel_chest", "15", "15", "15", "150000"));
		gameDatabase.insertItem(new Item("attack", "wooden_sword", "1", "1", "0", "50"));
		
		gameDatabase.insertMap(new Map("green_land", "1", "20", "102"));
		gameDatabase.insertMap(new Map("green_land", "2", "25", "150"));
		gameDatabase.insertMap(new Map("green_land", "3", "30", "160"));
		gameDatabase.insertMap(new Map("green_land", "4", "35", "176"));
		gameDatabase.insertMap(new Map("haunted_forest2","1", "50", "200"));
		gameDatabase.insertMap(new Map("haunted_forest2","2", "40", "220"));
		gameDatabase.insertMap(new Map("haunted_forest2","3", "45", "225"));
		gameDatabase.insertMap(new Map("haunted_forest2","4", "50", "300"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("granade_black", "normal", "150", "50", "2", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("hydra", "normal", "200", "55", "5", "9 1 0 0 8 6"));
		
		
		gameDatabase.insertMonster(new com.example.gamedata.Monster("armored_turtle", "boss", "500", "90", "90", "14 1 3 3 6 13"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("balloon", "normal", "200", "40", "5", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("black_dragon", "normal", "400", "50", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("blood_minotaur", "normal", "300", "40", "4", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("blue_dragon", "normal", "500", "40", "5", "9 0 1 1 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("bomb", "normal", "250", "60", "0", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("cloud", "normal", "310", "30", "5", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("dark_behemoth", "normal", "400", "40", "5", "9 1 0 0 9 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("dark_goblin", "normal", "100", "10", "0", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("darkdra", "normal", "250", "25", "2", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("deep_demon", "normal", "300", "35", "5", "9 1 0 0 9 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("demon", "normal", "350", "40", "6", "9 1 0 0 9 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("goblin", "normal", "150", "10", "0", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("gold_dragon", "normal", "500", "40", "10", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("green_dragon", "shielding", "200", "20", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("lightdra", "normal", "200", "40", "5", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("mega_death", "boss", "1000", "1000", "50", "10 0 3 3 6 2"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("minotaur", "normal", "150", "20", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("purple_dragon", "normal", "150", "30", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("red_dragon", "normal", "200", "25", "2", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("redcap", "normal", "100", "15", "0", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("shizel", "boss", "400", "50", "5", "10 0 4 5 7 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("tigre", "boss", "2000", "30", "30", "4 0 1 2 1 2"));
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		mainMusic.release();
	}

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	     return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}

}
