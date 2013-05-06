package com.example.pixme;

import com.example.gamedata.GameSharedPreferences;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ClassChoose extends Activity {
	ImageButton barbarian=null;
	ImageButton wizard=null;
	ImageButton hunter=null;
	Button choose=null;
	TextView class_name=null;
	TextView description=null;
	
	String choosed="barbarian";
	GameSharedPreferences appPrefs;
	
	String descriptions[]= { "DESCRIPTION ONE \n BARBARIAN IS COOL BRO \n", "DESCRIPTION TWO", "DESCRIPTION THRE"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_class_choose);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		barbarian = (ImageButton) findViewById(R.id.barbarian);  
	    wizard = (ImageButton) findViewById(R.id.wizard); 
	    hunter = (ImageButton) findViewById(R.id.hunter); 
		
	    choose = (Button) findViewById(R.id.choose);
	    
	    class_name = (TextView) findViewById(R.id.class_name);
	    description = (TextView) findViewById(R.id.description);
	    
	    Context context = getApplicationContext();
	    appPrefs = new GameSharedPreferences(context);
	    
	    Typeface font = Typeface.createFromAsset(getAssets(), "Fipps-Regular.otf");  
	    choose.setTypeface(font);
	    class_name.setTypeface(font);
	    description.setTypeface(font);
	    
	    barbarian.setBackgroundResource(R.drawable.character);
	    wizard.setBackgroundResource(R.drawable.inactive);
    	hunter.setBackgroundResource(R.drawable.inactive);	
		
		
	    barbarian.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	class_name.setText("-Barbarian-");
            	description.setText(descriptions[0]);
            	choosed="barbarian";
            	barbarian.setBackgroundResource(R.drawable.character);
            	wizard.setBackgroundResource(R.drawable.inactive);
            	hunter.setBackgroundResource(R.drawable.inactive);	
            }
        });	 
	   wizard.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	class_name.setText("-Wizard-");
            	description.setText(descriptions[1]);
            	choosed="wizard";
            	wizard.setBackgroundResource(R.drawable.character);
            	barbarian.setBackgroundResource(R.drawable.inactive);
            	hunter.setBackgroundResource(R.drawable.inactive);	
            }
        });
	   hunter.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
           	class_name.setText("-Hunter-");
           	description.setText(descriptions[2]);
           	choosed="hunter";
            hunter.setBackgroundResource(R.drawable.character);
           	barbarian.setBackgroundResource(R.drawable.inactive);
           	wizard.setBackgroundResource(R.drawable.inactive);	
           }
       });	
	   
	   choose.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	  appPrefs.createSave("true");
        	  appPrefs.createGame(choosed);
        	  startActivity(new Intent(ClassChoose.this, Tawern.class));
           }
       });	
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_class_choose, menu);
		return true;
	}

}
