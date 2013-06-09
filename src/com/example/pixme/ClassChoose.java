package com.example.pixme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.gamedata.GameSharedPreferences;

public class ClassChoose extends Activity {
	ImageButton previous = null;
	ImageButton next = null;
	ImageButton choose = null;
	View mlayout;

	String choosed = "barbarian";
	GameSharedPreferences appPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_class_choose);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		next = (ImageButton) findViewById(R.id.next);
		previous = (ImageButton) findViewById(R.id.previous);
		choose = (ImageButton) findViewById(R.id.choose);

		next.setBackgroundResource(R.drawable.empty_shop);
		previous.setBackgroundResource(R.drawable.empty_shop);
		choose.setBackgroundResource(R.drawable.empty_shop);
		mlayout = findViewById(R.id.charactermenu);

		Context context = getApplicationContext();
		appPrefs = new GameSharedPreferences(context);

		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (choosed.equals("barbarian")) {
					choosed = "wizard";
					mlayout.setBackgroundResource(R.drawable.ch_wizard);
				} else if (choosed.equals("wizard")) {
					choosed = "hunter";
					mlayout.setBackgroundResource(R.drawable.ch_hunter);
				} else {
					choosed = "barbarian";
					mlayout.setBackgroundResource(R.drawable.ch_barbarian);
				}

			}
		});
		previous.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (choosed.equals("barbarian")) {
					choosed = "hunter";
					mlayout.setBackgroundResource(R.drawable.ch_hunter);
				} else if (choosed.equals("hunter")) {
					choosed = "wizard";
					mlayout.setBackgroundResource(R.drawable.ch_wizard);
				} else {
					choosed = "barbarian";
					mlayout.setBackgroundResource(R.drawable.ch_barbarian);
				}
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
