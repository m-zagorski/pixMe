package com.example.pixme;

import com.example.combat.OneCharacterMaps;
import com.example.gamedata.GameSharedPreferences;
import com.example.gamedata.Item;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tawern extends Activity {
	ImageButton backButton=null;
	ImageButton fightButton=null;
	ImageButton shopButton=null;
	ImageButton skillButton=null;
	ImageButton statisticButton=null;
	ImageButton musicButton=null;
	ImageButton levelUp=null;
	GameSharedPreferences appPrefs;
	MediaPlayer tawernMusic;
	MediaPlayer winSound, loseSound;
	
	int length;
	final Context context=this;
	Typeface font;
	
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
		statisticButton = (ImageButton) findViewById(R.id.statisticButton);
		musicButton = (ImageButton) findViewById(R.id.musicButton);
	    tawernMusic = MediaPlayer.create(this, R.raw.slaughter_tavern);
	    levelUp = (ImageButton) findViewById(R.id.levelUp);
	    
	    backButton.setBackgroundResource(R.drawable.empty_shop);
	    fightButton.setBackgroundResource(R.drawable.empty_shop);
	    shopButton.setBackgroundResource(R.drawable.empty_shop);
	   skillButton.setBackgroundResource(R.drawable.empty_shop);
		statisticButton.setBackgroundResource(R.drawable.empty_shop);
		musicButton.setBackgroundResource(R.drawable.empty_shop);
		levelUp.setBackgroundResource(R.drawable.empty_shop);
		
	
		appPrefs = new GameSharedPreferences(context);
		font = Typeface.createFromAsset(getAssets(), "bloodthirsty.ttf");
		
		
	    winSound =  MediaPlayer.create(this, (getResources().getIdentifier(appPrefs.getCharacterClass()+"win", "raw", getPackageName())));
	    loseSound =  MediaPlayer.create(this, (getResources().getIdentifier(appPrefs.getCharacterClass()+"lose", "raw", getPackageName())));
		
		if(appPrefs.checkIfCombat().equals("Combat")){
			if(appPrefs.getCombatTitle().equals("You have won!")){
				winSound.start();
			}
			else {
				loseSound.start();
			}
    		createDialog();
    		}
		
		
		if(Integer.parseInt(appPrefs.characterLevel())<6){
			skillButton.setImageResource(getResources().getIdentifier("empty", "drawable", getPackageName()));	
		}
		
		
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
            	if(Integer.parseInt(appPrefs.characterLevel())>=6){
            		startActivity(new Intent(Tawern.this, Sanctuary.class));
            	}
            }
            });
	  	statisticButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
            });
	  	levelUp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	appPrefs.addLevel();
            }
            });
	  
	  	musicButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(appPrefs.getMusicStatus().equals("yes")){
            		appPrefs.updateMusic();
            		tawernMusic.pause();
            		length=tawernMusic.getCurrentPosition();
            	}
            	else{
            		appPrefs.updateMusic();
            		tawernMusic.seekTo(length);
            		tawernMusic.start();
            	}
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
	
	
	private void createDialog(){

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.win_lose_dialog);

		dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
		


		TextView description = (TextView) dialog.findViewById(R.id.description);
		TextView title = (TextView) dialog.findViewById(R.id.title);
		ImageView icon = (ImageView) dialog.findViewById(R.id.imageView1);
		
		title.setText(appPrefs.getCombatTitle());
		description.setText(appPrefs.getCombatDesc());
		title.setTypeface(font);
		
		if(appPrefs.getCombatTitle().equals("You have won!")){
		icon.setImageResource(R.drawable.win_angel);	
		}
		else {
		icon.setImageResource(R.drawable.lose_angel);	
		}

		Button dialogButtonOk = (Button) dialog.findViewById(R.id.continueButton);
		dialogButtonOk.setTypeface(font);
		dialogButtonOk.setBackgroundResource(R.drawable.empty_shop);
		
		dialogButtonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				appPrefs.changeCombatStatus();
				dialog.dismiss();
			}
		});


		dialog.show();
}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	     return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
	
	
	
}
