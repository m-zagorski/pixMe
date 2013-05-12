package com.example.pixme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.example.gamedata.GameDatabase;
import com.example.gamedata.GameSharedPreferences;
import com.example.gamedata.Item;
import com.example.gamedata.Map;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Shop extends Activity {
	GameDatabase items=null;
	Cursor itemsCursor;
	GameSharedPreferences appPrefs;
	ArrayList<Item> allItems = new ArrayList<Item>();
	ArrayList<Item> shopItems= new ArrayList<Item>();

	
	final Context context=this;
	ImageButton shopOne=null;
	ImageButton shopTwo=null;
	ImageButton shopThree=null;
	ImageButton shopFour=null;
	ImageButton shopFive=null;
	ImageButton shopSix=null;
	ImageButton shopSeven=null;
	

	
	ImageButton backButton=null;
	ImageView bs=null;
	AlertDialog alertDialog=null;
	TextView moneyStatus=null;
	ImageView moneyIcon=null;
	
	MediaPlayer bsmithSpeech;
	
	int playerLevel=0;
	//int playerMoney=0;
	
	
	private String randomItem(){
		Random r= new Random();
		String icon = allItems.get(r.nextInt(allItems.size())).getIcon();
		return icon;
	}
	
	
	private void fillShop(){
		while(shopItems.size()<=7){
			Item temp= allItems.get(new Random().nextInt(allItems.size()));
			if(Integer.parseInt(temp.getLevel())<playerLevel+10) {
				shopItems.add(temp);
			}
		}
	}
	
	
	private String createDescription(Item item){
		String description="";
		description="Attack: "+item.getDamage()+"\nDefense: "+item.getArmor()+"\nRequired level: "+item.getLevel()+"\nCost: "+item.getBuy();
		return description;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		
		/*
		for(int i=1; i<=7; i++)
		{
		ImageButton shopSlot=(ImageButton)findViewById(getResources().getIdentifier("shop"+String.valueOf(i), "id", getPackageName()));
		//shopSlot.setImageResource(getResources().getIdentifier(shopItems.get(1).getIcon(), "drawable", getPackageName()));
		shopSlot.setBackgroundResource(R.drawable.empty_shop);
		

		}
		*/
		
		items=new GameDatabase(this);
		items.open();
		appPrefs = new GameSharedPreferences(context);
		playerLevel= Integer.parseInt(appPrefs.characterLevel());
		//playerMoney= Integer.parseInt(appPrefs.characterMoney());
		fillItemsList();
		fillShop();
		
		bsmithSpeech = MediaPlayer.create(this, (getResources().getIdentifier("bsmith0"+new Random().nextInt(9), "raw", getPackageName())));
	    if(appPrefs.getMusicStatus().equals("yes")){
	        bsmithSpeech.start();
	      
		    }
		
		shopOne = (ImageButton) findViewById(R.id.shop1);
		shopTwo = (ImageButton) findViewById(R.id.shop2);
		shopThree = (ImageButton) findViewById(R.id.shop3);
		shopFour = (ImageButton) findViewById(R.id.shop4);
		shopFive = (ImageButton) findViewById(R.id.shop5);
		shopSix = (ImageButton) findViewById(R.id.shop6);
		shopSeven = (ImageButton) findViewById(R.id.shop7);
		
		
		
		bs = (ImageView) findViewById(R.id.bsguy);
		backButton = (ImageButton) findViewById(R.id.backButton);
		moneyIcon = (ImageView) findViewById(R.id.moneyIcon);
		moneyStatus = (TextView) findViewById(R.id.money);

		shopOne.setImageResource(getResources().getIdentifier(shopItems.get(1).getIcon(), "drawable", getPackageName()));
		shopTwo.setImageResource(getResources().getIdentifier(shopItems.get(2).getIcon(), "drawable", getPackageName()));
		shopThree.setImageResource(getResources().getIdentifier(shopItems.get(3).getIcon(), "drawable", getPackageName()));
		shopFour.setImageResource(getResources().getIdentifier(shopItems.get(4).getIcon(), "drawable", getPackageName()));
		shopFive.setImageResource(getResources().getIdentifier(shopItems.get(5).getIcon(), "drawable", getPackageName()));
		shopSix.setImageResource(getResources().getIdentifier(shopItems.get(6).getIcon(), "drawable", getPackageName()));
		shopSeven.setImageResource(getResources().getIdentifier(shopItems.get(7).getIcon(), "drawable", getPackageName()));
		
		//Transparent Backgrounds
		shopOne.setBackgroundResource(R.drawable.empty_shop);
		shopTwo.setBackgroundResource(R.drawable.empty_shop);
		shopThree.setBackgroundResource(R.drawable.empty_shop);
		shopFour.setBackgroundResource(R.drawable.empty_shop);
		shopFive.setBackgroundResource(R.drawable.empty_shop);
		shopSix.setBackgroundResource(R.drawable.empty_shop);
		shopSeven.setBackgroundResource(R.drawable.empty_shop);
	
		backButton.setBackgroundResource(R.drawable.empty_shop);
		bs.setBackgroundResource(R.drawable.empty_shop);
		//--
		 
		
		moneyStatus.setText(appPrefs.characterMoney());
		if(Integer.parseInt(appPrefs.characterMoney())>300000){
			moneyIcon.setImageResource(R.drawable.max_money);
		}
		else if(Integer.parseInt(appPrefs.characterMoney())>250000){
			moneyIcon.setImageResource(R.drawable.platinum_money);
		}
		else if(Integer.parseInt(appPrefs.characterMoney())>100000){
			moneyIcon.setImageResource(R.drawable.gold_money);
		}
		else if(Integer.parseInt(appPrefs.characterMoney())>10000){
			moneyIcon.setImageResource(R.drawable.silver_money);
		}
		else {
			moneyIcon.setImageResource(R.drawable.max_money);
		}

		 
		


	
		
		
		
    	shopOne.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(1));
            }
            });
    	shopTwo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(2));
            }
            });
    	shopThree.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(3));
            }
            });
    	shopFour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(4));
            }
            });
    	shopFive.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(5));
            }
            });
    	shopSix.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(6));
            }
            });
    	shopSeven.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		createDialog(shopItems.get(7));
            }
            });
		
    	backButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
             	startActivity(new Intent(Shop.this, Tawern.class));
            }
            });
		
		
		
		
		
		
		
		items.close();
	}
	
	
	@SuppressWarnings("deprecation")
	private void fillItemsList() {
	    itemsCursor = items.getAllItems();
	    startManagingCursor(itemsCursor);
	    updateItemsList();
	}
	
	@SuppressWarnings("deprecation")
	private void updateItemsList() {
		 itemsCursor.requery();
	    
	    allItems.clear();
	    
	    if( itemsCursor.moveToFirst()) {
	        do {
	            String type =itemsCursor.getString(items.TYPE_COLUMN);
	            String icon =itemsCursor.getString(items.ICON_COLUMN);
	            String level =itemsCursor.getString(items.LEVEL_COLUMN);
	            String armor =itemsCursor.getString(items.ARMOR_COLUMN);
	            String damage =itemsCursor.getString(items.DAMAGE_COLUMN);
	            String buy =itemsCursor.getString(items.BUY_COLUMN);
	            Item newCountry = new Item(type, icon, level, armor, damage, buy);
	            allItems.add(newCountry);
	        } while ( itemsCursor.moveToNext());
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_shop, menu);
		return true;
	}
	
	
	private void createDialog(final Item item){

			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.activity_shop_dialog);

			dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
			


			TextView description = (TextView) dialog.findViewById(R.id.description);
			TextView title = (TextView) dialog.findViewById(R.id.title);
			ImageView icon = (ImageView) dialog.findViewById(R.id.icon);
			
			description.setText(createDescription(item));
			title.setText(item.getIcon());
			icon.setImageResource(getResources().getIdentifier(item.getIcon(), "drawable", getPackageName()));
			//ImageView image = (ImageView) dialog.findViewById(R.id.image);
			//image.setImageResource(R.drawable.ic_launcher);

			Button dialogButtonBuy = (Button) dialog.findViewById(R.id.buttonBuy);
			Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancel);

			dialogButtonCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialogButtonBuy.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(Integer.parseInt(item.getLevel())>playerLevel){
						Toast.makeText(Shop.this, 
								"Your level is too low to buy that item.", 
								Toast.LENGTH_LONG).show();
					}
					else if(Integer.parseInt(item.getBuy())>Integer.parseInt(appPrefs.characterMoney())){
						Toast.makeText(Shop.this, 
								"Not enough money.", 
								Toast.LENGTH_LONG).show();	
					}
					else {
					appPrefs.updateCharacterMoney(Integer.parseInt(item.getBuy()));
					appPrefs.updateBonus(Integer.parseInt(item.getArmor()), Integer.parseInt(item.getDamage()));
					Toast.makeText(Shop.this, 
							"Item bought.", 
							Toast.LENGTH_LONG).show();	
					}
					dialog.dismiss();
					
					
				}
			});

			dialog.show();
	}


	@Override
	protected void onPause(){
		super.onPause();
		bsmithSpeech.release();
	}

}
