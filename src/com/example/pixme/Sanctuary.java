package com.example.pixme;

import java.util.Random;

import com.example.gamedata.GameSharedPreferences;
import com.example.gamedata.Item;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

public class Sanctuary extends Activity {
	GameSharedPreferences appPrefs;
	ImageButton firstSkill=null;
	ImageButton secondSkill=null;
	ImageButton thirdSkill=null;
	
	final Context context=this;
	

	
	final int upgradeCosts[]={ 
			0,
			200,
			300,
			400,
			500,
			1000,
			3000,
			5000,
			7000,
			10000
	};
	final String skillNames[][]={
			{"barbOne", "barbTwo", "barbThree"},
			{"wizOne", "wizTwo", "wizThree"},
			{"hunOne", "hunTwo", "hunThree"}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sanctuary);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		appPrefs = new GameSharedPreferences(context);

		
		firstSkill = (ImageButton) findViewById(R.id.firstSkill);
		secondSkill = (ImageButton) findViewById(R.id.secondSkill);
		thirdSkill = (ImageButton) findViewById(R.id.thirdSkill);
		
		firstSkill.setBackgroundResource(R.drawable.empty_shop);
		secondSkill.setBackgroundResource(R.drawable.empty_shop);
		thirdSkill.setBackgroundResource(R.drawable.empty_shop);
		
		//firstSkill.setImageResource(getResources().getIdentifier("empty_shop", "drawable", getPackageName()));
		//secondSkill.setImageResource(getResources().getIdentifier("empty_shop", "drawable", getPackageName()));
		//thirdSkill.setImageResource(getResources().getIdentifier("empty_shop", "drawable", getPackageName()));
		
	 	firstSkill.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(0);
            }
            });
	 	secondSkill.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(1);
            }
            });
	 	thirdSkill.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(2);
            }
            });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sanctuary, menu);
		return true;
	}
	
	private void createDialog(final int skillID){

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.sanctuary_dialog);

		dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
		


		TextView skillDescription = (TextView) dialog.findViewById(R.id.skillDescription);
		TextView title = (TextView) dialog.findViewById(R.id.skillName);
		TextView skillNextLevel= (TextView) dialog.findViewById(R.id.skillNextLevel);
		title.setText(skillNames[0][skillID]);
		skillDescription.setText("DESCIRPION"+appPrefs.skillLevel(skillID));
		skillNextLevel.setText(""+upgradeCosts[appPrefs.skillLevel(skillID)]);
		
		//ImageView image = (ImageView) dialog.findViewById(R.id.image);
		//image.setImageResource(R.drawable.ic_launcher);

		Button dialogButtonBuy = (Button) dialog.findViewById(R.id.upgrade);
		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.cancel);

		dialogButtonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialogButtonBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(appPrefs.skillLevel(skillID)==10){
					Toast.makeText(Sanctuary.this, 
							"Max skill level.", 
							Toast.LENGTH_LONG).show();		
				}
				else if(upgradeCosts[appPrefs.skillLevel(skillID)]>Integer.parseInt(appPrefs.characterMoney())){
					Toast.makeText(Sanctuary.this, 
							"Not enough money.", 
							Toast.LENGTH_LONG).show();	
				}
				else
				{
					appPrefs.updateCharacterMoney(upgradeCosts[appPrefs.skillLevel(skillID)]);
					if(ifUpgraded(appPrefs.skillLevel(skillID))){
						appPrefs.updateSkill(skillID);
						Toast.makeText(Sanctuary.this, 
								"Upgrade succedded.", 
								Toast.LENGTH_LONG).show();
					}
					else {
						appPrefs.decreaseSkill(skillID); 
						Toast.makeText(Sanctuary.this, 
								"Upgrade failed.", 
								Toast.LENGTH_LONG).show();	
					}
					
					
				}
				dialog.dismiss();
				
				
			}
		});

		dialog.show();
}
	
	
	
	
private boolean ifUpgraded(int skillID){
if(skillID==1){
	if(new Random().nextInt(9)<8){ return true; } else {return false;}
}
else if(skillID==2){
	if(new Random().nextInt(9)<7){ return true; } else {return false;}
}
else if(skillID==3){
	if(new Random().nextInt(9)<6){ return true; } else {return false;}
}
else if(skillID==4){
	if(new Random().nextInt(9)<5){ return true; } else {return false;}
}
else if(skillID==5){
	if(new Random().nextInt(9)<5){ return true; } else {return false;}
}
else if(skillID==6){
	if(new Random().nextInt(9)<4){ return true; } else {return false;}
}
else if(skillID==7){
	if(new Random().nextInt(9)<2){ return true; } else {return false;}
}
else if(skillID==8){
	if(new Random().nextInt(9)<2){ return true; } else {return false;}
}
else if(skillID==9){
	if(new Random().nextInt(9)<1){ return true; } else {return false;}
}
else { return false;}
}
	
	
	

}
