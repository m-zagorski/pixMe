package com.example.pixme;

import com.example.combat.OneCharacterMaps;
import com.example.gamedata.GameSharedPreferences;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Tawern extends Activity {
	ImageButton backButton=null;
	ImageButton fightButton=null;
	ImageButton shopButton=null;
	ImageButton skillButton=null;
	GameSharedPreferences appPrefs;
	MediaPlayer tawernMusic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tawern);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	
		backButton = (ImageButton) findViewById(R.id.backButton);
		fightButton = (ImageButton) findViewById(R.id.fightButton);
		shopButton = (ImageButton) findViewById(R.id.shopButton);
		skillButton =  (ImageButton) findViewById(R.id.skillButton);
	    tawernMusic = MediaPlayer.create(this, R.raw.menu);
	    
	    backButton.setBackgroundResource(R.drawable.empty_shop);
	    fightButton.setBackgroundResource(R.drawable.empty_shop);
	    shopButton.setBackgroundResource(R.drawable.empty_shop);
	    skillButton.setBackgroundResource(R.drawable.empty_shop);
		
		Context context = getApplicationContext();
		appPrefs = new GameSharedPreferences(context);
		
		
	    if(appPrefs.getMusicStatus().equals("yes")){
	        tawernMusic.start();
	        tawernMusic.setLooping(true);
		    }
		
		String data[]=appPrefs.characterInfo();
		String description="Level: "+data[1]+"\nExperience: "+data[2]+"\nMoney: "+data[3];
	
		
	  	backButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(Tawern.this, MainScreen.class));
            }
            });
		
	  	fightButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(Tawern.this, OneCharacterMaps.class));
            }
            });
	  	shopButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(Tawern.this, Shop.class));
            }
            });
	  	skillButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(Tawern.this, Sanctuary.class));
            }
            });
		
		
	}
	@Override
	protected void onPause(){
		super.onPause();
		tawernMusic.release();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tawern, menu);
		return true;
	}

}
