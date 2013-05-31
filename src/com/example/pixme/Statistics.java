package com.example.pixme;

import com.example.gamedata.GameSharedPreferences;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Statistics extends Activity {
	
	GameSharedPreferences appPrefs;
	
	ImageButton backButton=null;
	TextView lv=null;
	TextView exp=null;
	TextView money=null;
	TextView attbonus=null;
	TextView defbonus=null;
	TextView wins=null;
	TextView losess=null;
	TextView spentmoney=null;
	TextView attitems=null;
	TextView defitems=null;
	
	Typeface font;
	
	MediaPlayer statisticMusic;
	
	final Context context=this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		font = Typeface.createFromAsset(getAssets(), "kree.ttf");
		
		backButton = (ImageButton) findViewById(R.id.backButton);
		lv = (TextView) findViewById(R.id.level);
		exp = (TextView) findViewById(R.id.experience);
		money = (TextView) findViewById(R.id.gold);
		attbonus = (TextView) findViewById(R.id.attackbonus);
		defbonus = (TextView) findViewById(R.id.defensebonus);
		wins = (TextView) findViewById(R.id.wins);
		losess = (TextView) findViewById(R.id.loses);
		spentmoney = (TextView) findViewById(R.id.spentmoney);
		attitems = (TextView) findViewById(R.id.attackitems);
		defitems = (TextView) findViewById(R.id.defenseitems);
		
		
		lv.setTypeface(font);
		exp.setTypeface(font);
		money.setTypeface(font);
		attbonus.setTypeface(font);
		defbonus.setTypeface(font);
		wins.setTypeface(font);
		losess.setTypeface(font);
		spentmoney.setTypeface(font);
		attitems.setTypeface(font);
		defitems.setTypeface(font);
		
		
		appPrefs = new GameSharedPreferences(context);
		
		
		String[] statinfo = appPrefs.statisticInfo();
		
		lv.setText("Level: "+statinfo[0]);
		exp.setText("Experience: "+statinfo[1]);
		money.setText("Money: "+statinfo[2]);
		attbonus.setText("Attack Bonus: "+statinfo[3]);
		defbonus.setText("Defense Bonus: "+statinfo[4]);
		wins.setText("Total Wins: "+statinfo[5]);
		losess.setText("Total Loses: "+statinfo[6]);
		spentmoney.setText("Spent Money: "+statinfo[7]);
		attitems.setText("Attack items: "+statinfo[8]);
		defitems.setText("Defense items: "+statinfo[9]);
		

		 statisticMusic =  MediaPlayer.create(this, (getResources().getIdentifier("statisticmusic", "raw", getPackageName())));
			if(appPrefs.getMusicStatus().equals("yes")){
		        statisticMusic.start();
		      
			}
			
			
			backButton.setBackgroundResource(R.drawable.empty_shop);
			
			
	    	backButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	             	startActivity(new Intent(Statistics.this, Tawern.class));
	            }
	            });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_statistics, menu);
		return true;
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		statisticMusic.release();
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
