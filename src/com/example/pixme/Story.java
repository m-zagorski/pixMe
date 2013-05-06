package com.example.pixme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class Story extends Activity implements OnTouchListener {
	//Intent intent=null;
	Context ctx=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_story);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		TextView note= (TextView) findViewById(R.id.note);  
		//intent=new Intent(getApplicationContext(), ClassChoose.class); 
		Context ctx=getApplicationContext();
		
		Typeface font = Typeface.createFromAsset(getAssets(), "Fipps-Regular.otf");  
		note.setTypeface(font);
		note.setText("Click anywhere to continue...");
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_story, menu);
		return true;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		Intent i = new Intent(ctx, ClassChoose.class);
		ctx.startActivity(i); 
		return false;
	}

}
