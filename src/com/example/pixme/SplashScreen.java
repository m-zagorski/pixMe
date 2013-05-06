package com.example.pixme;

import com.example.gamedata.GameSharedPreferences;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
	
	
	private boolean mIsBackButtonPressed;
	GameSharedPreferences appPrefs;
	
	private static final int SPLASH_DURATION = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
		Handler handler = new Handler();
		Context context = getApplicationContext();
	    appPrefs = new GameSharedPreferences(context);
	    
        MediaPlayer splashMusic = MediaPlayer.create(this, R.raw.laugh);
	    if(appPrefs.getMusicStatus().equals("yes")){
	        splashMusic.start();
	      
		    }
  
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {

                finish();
                 
                if (!mIsBackButtonPressed) {
                    Intent intent = new Intent(SplashScreen.this, MainScreen.class);
                    SplashScreen.this.startActivity(intent);
               }
                 
            }
 
        }, SPLASH_DURATION);
 
    }
 
    @Override
   public void onBackPressed() {
        mIsBackButtonPressed = true;
        super.onBackPressed();
 
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

}
