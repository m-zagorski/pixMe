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
            	startActivity(new Intent(MainScreen.this, Shop.class));
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
		
		gameDatabase.insertItem(new Item("defense", "avalanche_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "bat_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "beam_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "bloody_scythe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "bloody_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "bloody_vest", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "blue_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "bone_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "bronze-scythe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "burning_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "cains_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "choosen_one", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "cold_big_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "cold_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "cosmic_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "damaged_boots", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "dark_hands", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "death_bringer", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "death_cape", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "death_chest", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "death_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "death_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "demon_boots", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "demons_axe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "demons_hands", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "double_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fallen_angel_chest", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fallen_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fallen_skeletan_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fast_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fire_axe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fire_devastator", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "fire_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "golden_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "hatchet", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "ice_crusher", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "ice_kings_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "icy_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "kings_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "knight_greavers", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "knights_hands", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "large_red_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "light_boots", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "lightning_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "lightning_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "lying_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "magic_angel_chest", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "magic_demonic_chest", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "magic_bone_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "majestic_axe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "majestic_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "mallet", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "marvelous_hand", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "medusas_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "missle_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "molten_axe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "molten_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "molten_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "molten_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "monster_gloves", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "moss_axe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "moss_boots", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "moss_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "moss_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "normal_green_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "normal_magic_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "normal_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "old_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "overflamed_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "poison_hand", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "poison_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "poison_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "purple_snake_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "rainbow_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "red_hammer", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "santas_boots", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "scythe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "skeletan_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "skull_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "snake_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "standard_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "taste_ball", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "unholy_burning_blade", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "unsubsequel_sword", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "water_axe", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "water_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "water_staff", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "weed_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "weed_kings_bow", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "white_angel_chest", "10", "2", "0", "1000"));
		gameDatabase.insertItem(new Item("defense", "wooden_sword", "10", "2", "0", "1000"));
		
		gameDatabase.insertMap(new Map("green_land", "1", "50", "102"));
		gameDatabase.insertMap(new Map("green_land", "2", "150", "358"));
		gameDatabase.insertMap(new Map("green_land", "3", "450", "700"));
		gameDatabase.insertMap(new Map("green_land", "4", "700", "1000"));
		gameDatabase.insertMap(new Map("haunted_forest2","1", "90", "162"));
		gameDatabase.insertMap(new Map("haunted_forest2","2", "450", "500"));
		gameDatabase.insertMap(new Map("haunted_forest2","3", "640", "600"));
		gameDatabase.insertMap(new Map("haunted_forest2","4", "800", "900"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("granade_black", "normal", "150", "50", "2", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("hydra", "normal", "200", "55", "5", "9 1 0 0 8 6"));
		
		
		gameDatabase.insertMonster(new com.example.gamedata.Monster("armored_turtle", "boss", "500", "90", "90", "14 1 3 3 6 13"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("balloon", "normal", "200", "40", "5", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("black_dragon", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("blood_minotaur", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("blue_dragon", "normal", "200", "40", "5", "9 0 1 1 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("bomb", "normal", "250", "60", "0", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("cloud", "normal", "200", "40", "5", "9 0 5 3 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("dark_behemoth", "normal", "200", "40", "5", "9 1 0 0 9 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("dark_goblin", "normal", "200", "40", "5", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("darkdra", "normal", "200", "40", "5", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("deep_demon", "normal", "200", "40", "5", "9 1 0 0 9 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("demon", "normal", "200", "40", "5", "9 1 0 0 9 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("goblin", "normal", "200", "40", "5", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("gold_dragon", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("green_dragon", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("lightdra", "normal", "200", "40", "5", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("mega_death", "normal", "200", "40", "5", "10 0 3 3 6 2"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("minotaur", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("purple_dragon", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("red_dragon", "normal", "200", "40", "5", "9 1 0 0 8 9"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("redcap", "normal", "200", "40", "5", "9 1 0 0 8 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("shizel", "boss", "200", "40", "5", "10 0 4 5 7 6"));
		gameDatabase.insertMonster(new com.example.gamedata.Monster("tigre", "normal", "200", "40", "5", "4 0 1 2 1 2"));
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		mainMusic.release();
	}

	
/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	     return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
*/



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}

}
