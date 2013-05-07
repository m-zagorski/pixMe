package com.example.combat;




import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entities.Characters;
import com.example.entities.MainCharacter;
import com.example.entities.Monsters;
import com.example.entities.NormalMonster;
import com.example.gamedata.GameDatabase;
import com.example.gamedata.GameSharedPreferences;
import com.example.gamedata.Item;
import com.example.gamedata.Map;
import com.example.pixme.ClassChoose;
import com.example.pixme.MainScreen;
import com.example.pixme.R;
import com.example.pixme.Shop;

public class OneCharacterMaps extends Activity implements OnTouchListener{
	OurView v;
	Context context;
	
	//COMBAT VARIABLES
	public enum State { PLAYERR, MONSTER}
	public enum Monster { ONE, TWO, THREE, FOUR}
	
	State state=State.PLAYERR;
	Monster whichMonster=Monster.ONE;
	boolean monsterOne=true, monsterTwo=false, monsterThree=false, MonsterFour=false;
	boolean playerAttack=true;
	boolean monsterAttack=true;
	long damage=0;
	
	

	int yPlayer, yMonster;
	int screenWidth, screenHeight;
	int playerLevel;
	
	GameDatabase maps=null;
	GameSharedPreferences appPrefs;
	Cursor mapsCursor;
	Cursor monstersCursor;
	ArrayList<Map> allMaps = new ArrayList<Map>();
	ArrayList<com.example.gamedata.Monster> allMonsters = new ArrayList<com.example.gamedata.Monster>();
	Paint cooldowns;
	Map currentMap=null;
	
	Bitmap backgroundBitmap, playerBitmap, firstMonsterBitmap, secondMonsterBitmap, thirdMonsterBitmap, fourthMonsterBitmap;
	Bitmap firstSkill, secondSkill, thirdSkill;
	int firstSkillCd=1, secondSkillCd=2, thirdSkillCd=3;
	Characters player=null;
	Monsters firstMonster=null, secondMonster=null, thirdMonster=null, fourthMonster=null;
	Monsters attackedMonster=null;
	//--
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context=getApplicationContext();
		v = new OurView(this);
		v.setOnTouchListener(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		appPrefs = new GameSharedPreferences(context);
		playerLevel= Integer.parseInt(appPrefs.characterLevel());
		maps=new GameDatabase(this);
		maps.open();
		fillMapsList();
		fillMonstersList();
		maps.close();
		//BITMAPS LOAD
		//backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.haunted_forest2);
		initializeBitmaps();
		createPaints();
	
		//---
		setContentView(v);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}
	
	private void fillMapsList(){
		    mapsCursor = maps.getAllMaps();
		    startManagingCursor(mapsCursor);
		    updateMapsList();
	}
	private void fillMonstersList(){
	    monstersCursor = maps.getAllMonsters();
	    startManagingCursor(monstersCursor);
	    updateMonstersList();
}
	
	private void updateMapsList(){
		 mapsCursor.requery();
		    
		    allMaps.clear();
		    
		    if( mapsCursor.moveToFirst()) {
		        do {
		            String name = mapsCursor.getString(maps.NAME_COLUMN);
		            String monstersCount = mapsCursor.getString(maps.MONSTERSCOUNT_COLUMN);
		            String gold = mapsCursor.getString(maps.BASEGOLD_COLUMN);
		            String exp = mapsCursor.getString(maps.BASEEXPERIENCE_COLUMN);
		            Map newMap = new Map(name, monstersCount, gold, exp);
		            allMaps.add(newMap);
		        } while ( mapsCursor.moveToNext());
		    }
		}
	
	private void updateMonstersList(){
		 monstersCursor.requery();
		    
		    allMonsters.clear();
		    
		    if( monstersCursor.moveToFirst()) {
		        do {
		            String icon = monstersCursor.getString(maps.MONSTERICON_COLUMN);
		            String type = monstersCursor.getString(maps.MONSTERTYPE_COLUMN);
		            String health = monstersCursor.getString(maps.MONSTERHEALTH_COLUMN);
		            String damage = monstersCursor.getString(maps.MONSTERDAMAGE_COLUMN);
		            String armor = monstersCursor.getString(maps.MONSTERARMOR_COLUMN);
		            String coords = monstersCursor.getString(maps.MONSTERCOORDS_COLUMN);
		            com.example.gamedata.Monster newMonster = new com.example.gamedata.Monster(icon, type, health, damage, armor, coords);
		            allMonsters.add(newMonster);
		        } while ( monstersCursor.moveToNext());
		    }
		}

	
	private void initializeBitmaps(){
		Random r= new Random();
		currentMap = allMaps.get(r.nextInt(allMaps.size()));
		String mapName = currentMap.getName();
		
		
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mapName, "drawable", getPackageName()));
		playerBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.barbarian);
		//firstMonsterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.granade_black);
		//secondMonsterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hydra);
		//thirdMonsterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hydra);
		
		firstSkill = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(appPrefs.getCharacterClass()+"skillfirst", "drawable", getPackageName()));
		secondSkill = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(appPrefs.getCharacterClass()+"skillsecond", "drawable", getPackageName()));
		thirdSkill = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(appPrefs.getCharacterClass()+"skillthird", "drawable", getPackageName()));
		
		 
	}
	

	
	
	private void createPaints(){
		cooldowns = new Paint();
		cooldowns.setStyle(Paint.Style.FILL);
		cooldowns.setAntiAlias(true);
		cooldowns.setTextSize(60);
		cooldowns.setColor(Color.WHITE);
	}
	
	
	
	public class OurView extends SurfaceView implements Runnable{
		
		Thread t=null;
		SurfaceHolder holder;
		boolean isOk=false;
		
		
		public OurView(Context context) {
			super(context);
			holder=getHolder();
		}
		@Override
		public void run() {
			//CREATE ChARS AND MONSTERS

			 DisplayMetrics deviceDisplayMetrics = new DisplayMetrics();
			 getWindowManager().getDefaultDisplay().getMetrics(deviceDisplayMetrics);
			 screenWidth = deviceDisplayMetrics.widthPixels;
			 screenHeight = deviceDisplayMetrics.heightPixels;

			 player=new MainCharacter(this, playerBitmap, 100, 150, playerLevel,appPrefs.getCharacterClass());
			 initializeMonsters(this);
			 
			//firstMonster= new NormalMonster(this, firstMonsterBitmap, 600,200, 1000, 50, 25, 5, 1,"9 0 5 3 8 6", "Normal");
			//secondMonster= new NormalMonster(this, secondMonsterBitmap, 600,400, 1000, 50, 60, 5, 1,"9 1 0 0 8 6", "Normal");
			//thirdMonster=new NormalMonster(this, secondMonsterBitmap, 500,300, 100, 50, 600, 5, 1,"9 1 0 0 8 6", "Normal");
			
			//--
			//ALL THE MAGICs
			while(isOk){
			if(!holder.getSurface().isValid()){
				continue;
			}
			Canvas c= holder.lockCanvas();
			onDraw(c);
			holder.unlockCanvasAndPost(c);	
		}
		}
		private void initializeMonsters(OurView ourView) {
			
			
		
			
			
			//int monstersCount=Integer.parseInt(currentMap.getMonstersCount());
			int monstersCount=4;

			if(monstersCount>0){
			com.example.gamedata.Monster monsterFirst = allMonsters.get(new Random().nextInt(allMonsters.size()));
			firstMonsterBitmap= BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(monsterFirst.getIcon(), "drawable", getPackageName()));
			firstMonster=new NormalMonster(this, firstMonsterBitmap, 600,200,monsterFirst.getType(), Integer.parseInt(monsterFirst.getHealth()), Integer.parseInt(monsterFirst.getArmor()), Integer.parseInt(monsterFirst.getDamage()), playerLevel,monsterFirst.getCoords());
			}
			if(monstersCount>1){
			com.example.gamedata.Monster monsterSecond = allMonsters.get(new Random().nextInt(allMonsters.size()));	
			secondMonsterBitmap= BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(monsterSecond.getIcon(), "drawable", getPackageName()));
			secondMonster=new NormalMonster(this, secondMonsterBitmap, 600,400,monsterSecond.getType(), Integer.parseInt(monsterSecond.getHealth()), Integer.parseInt(monsterSecond.getArmor()), Integer.parseInt(monsterSecond.getDamage()), playerLevel,monsterSecond.getCoords());
			}
			if(monstersCount>2){
			com.example.gamedata.Monster monsterThird = allMonsters.get(new Random().nextInt(allMonsters.size()));
			thirdMonsterBitmap= BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(monsterThird.getIcon(), "drawable", getPackageName())); 
			thirdMonster=new NormalMonster(this, thirdMonsterBitmap, 500,300,monsterThird.getType(), Integer.parseInt(monsterThird.getHealth()), Integer.parseInt(monsterThird.getArmor()), Integer.parseInt(monsterThird.getDamage()), playerLevel,monsterThird.getCoords());}
			if(monstersCount>3) {
			com.example.gamedata.Monster monsterFour = allMonsters.get(new Random().nextInt(allMonsters.size()));
			fourthMonsterBitmap= BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(monsterFour.getIcon(), "drawable", getPackageName()));	
			fourthMonster=new NormalMonster(this, fourthMonsterBitmap, 500,400,monsterFour.getType(), Integer.parseInt(monsterFour.getHealth()), Integer.parseInt(monsterFour.getArmor()), Integer.parseInt(monsterFour.getDamage()), playerLevel,monsterFour.getCoords()); 
			}
			

			
		}
		protected void onDraw(Canvas canvas){	
			//BACKGROUND//
			Paint background = new Paint();
			background.setFilterBitmap(true);
			Rect dest = new Rect(0, 0, getWidth(), getHeight());
			canvas.drawBitmap(backgroundBitmap, null, dest, background);
			//
			
			
			//SKILLS
			/*
			canvas.drawBitmap(firstSkill, (screenWidth/2)-152, screenHeight-90, null);
			canvas.drawBitmap(secondSkill, (screenWidth/2)-54, screenHeight-90, null);
			canvas.drawBitmap(thirdSkill, (screenWidth/2)+44, screenHeight-90, null);
			 */
			
			canvas.drawBitmap(firstSkill, 0, (screenHeight/2)-152, null);
			canvas.drawBitmap(secondSkill, 0, (screenHeight/2)-54, null);
			canvas.drawBitmap(thirdSkill, 0, (screenHeight/2)+44, null);
			//--
			 
			
			
		
			
			
			//HEALTH
			Typeface font = Typeface.createFromAsset(getAssets(), "8.TTF");
			Paint healthNumbers=new Paint();
			healthNumbers.setStyle(Paint.Style.FILL);
			healthNumbers.setAntiAlias(true);
			healthNumbers.setTextSize(25);
			healthNumbers.setTypeface(font);
			healthNumbers.setColor(Color.BLUE);
			canvas.drawText(""+player.getHealth(), player.getX()+30, player.getY(), healthNumbers);
			canvas.drawText(""+firstMonster.getHealth(), firstMonster.getX()+30, firstMonster.getY(), healthNumbers);
			if(checkIfMonsterExists(2)){ canvas.drawText(""+secondMonster.getHealth(), secondMonster.getX()+30, secondMonster.getY(), healthNumbers);}
			if(checkIfMonsterExists(3)){ canvas.drawText(""+thirdMonster.getHealth(), thirdMonster.getX()+30, thirdMonster.getY(), healthNumbers); }
			if(checkIfMonsterExists(4)){ canvas.drawText(""+fourthMonster.getHealth(), fourthMonster.getX()+30, fourthMonster.getY(), healthNumbers); }
	
			//DRAW COOLDOWNS
			canvas.drawText(""+firstSkillCd, 27, screenHeight/2, cooldowns);
			//--
			//--
			
			checkGameStatus();
			

			player.onDraw(canvas);
			firstMonster.onDraw(canvas);
			if(checkIfMonsterExists(2)){ secondMonster.onDraw(canvas); }
			if(checkIfMonsterExists(3)){ thirdMonster.onDraw(canvas); }
			if(checkIfMonsterExists(4)){ fourthMonster.onDraw(canvas); }
			
			
			
			if(state==State.PLAYERR){
			if(player.isAttacking()){
				if(playerAttack){ damage=player.doDamage(); attackedMonster.getDamage(damage); playerAttack=false;}
				canvas.drawText("-"+damage, attackedMonster.getX()+32, yMonster--, healthNumbers);
			}
			else { 
				if(!playerAttack && !player.done()){ state=State.MONSTER; whichMonster=Monster.ONE; monsterAttack=true; firstMonster.setMonsterTurn(true); firstMonster.setPlayerDest(player.getX(), player.getY());}
			}
			}
			else
			{
			 if(whichMonster==Monster.ONE){
			
				if(firstMonster.isAttacking()){
				if(monsterAttack){yPlayer=player.getY(); damage=firstMonster.doDamage(); player.getDamage(damage); monsterAttack=false;}
				canvas.drawText("-"+damage, player.getX()+32, yPlayer--, healthNumbers);
				}
				else {
					if(!firstMonster.getMonsterTurn() && (!monsterAttack || !firstMonster.isMonsterAlive())){whichMonster=Monster.TWO; playerAttack=true; monsterAttack=true;   
					if(checkIfMonsterExists(2)){secondMonster.setMonsterTurn(true); secondMonster.setPlayerDest(player.getX(), player.getY());}}
				}
				 
			 }
			 else if(whichMonster==Monster.TWO){
				if(secondMonster==null){ state=state.PLAYERR; } 
				else{
					
					if(secondMonster.isAttacking()){
						if(monsterAttack){yPlayer=player.getY(); damage=secondMonster.doDamage(); player.getDamage(damage); monsterAttack=false;}
						canvas.drawText("-"+damage, player.getX()+32, yPlayer--, healthNumbers);
						}
						else {
							if(!secondMonster.getMonsterTurn() && (!monsterAttack || !secondMonster.isMonsterAlive())){whichMonster=Monster.THREE; monsterAttack=true;    
							if(checkIfMonsterExists(3)){thirdMonster.setMonsterTurn(true); thirdMonster.setPlayerDest(player.getX(), player.getY());}
							}
						}
		
					
					
				}
			 }
			 else if(whichMonster==Monster.THREE){
				 if(thirdMonster==null){ state=state.PLAYERR; whichMonster=Monster.ONE;} 
				 else{
					
						if(thirdMonster.isAttacking()){
							if(monsterAttack){yPlayer=player.getY(); damage=thirdMonster.doDamage(); player.getDamage(damage); monsterAttack=false;}
							canvas.drawText("-"+damage, player.getX()+32, yPlayer--, healthNumbers);
							}
							else {
								if(!thirdMonster.getMonsterTurn() && (!monsterAttack || !thirdMonster.isMonsterAlive())){whichMonster=Monster.FOUR; monsterAttack=true;    
								if(checkIfMonsterExists(4)){fourthMonster.setMonsterTurn(true); fourthMonster.setPlayerDest(player.getX(), player.getY());}
								}
							}
				
				 }
			 }
			 else if(whichMonster==Monster.FOUR){
				 if(fourthMonster==null){ state=state.PLAYERR; whichMonster=Monster.ONE;} 
				 else{
					 	
						if(fourthMonster.isAttacking()){
							if(monsterAttack){yPlayer=player.getY(); damage=fourthMonster.doDamage(); player.getDamage(damage); monsterAttack=false;}
							canvas.drawText("-"+damage, player.getX()+32, yPlayer--, healthNumbers);
							}
							else {
								if(!fourthMonster.getMonsterTurn() && (!monsterAttack || !fourthMonster.isMonsterAlive())){whichMonster=Monster.ONE; state=state.PLAYERR; monsterAttack=true;    
				
								}
							} 
					 
				 }
			 }
			}
	

			
		
		}
		public void pause(){
		isOk=false;	
		while(true){
			try{
				t.join();
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			break;
		}
		t=null;
		}	
		public void resume(){
		isOk=true;	
		t= new Thread(this);
		t.start();
		}
		
		private void checkGameStatus(){
			if(firstMonster.getHealth()<0){ 
				startActivity(new Intent(context, Shop.class));
				
			}
		}
		
		
	}
	

	
	private boolean checkIfMonsterExists(int monsterID){
		if(monsterID==2){
			if(secondMonster==null) { return false; }
			else { return true;}
		}
		else if(monsterID==3){
			if(thirdMonster==null) { return false; }
			else { return true;}
		}
		else{
			if(fourthMonster==null) { return false; }
			else { return true;}
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(me.getAction()){
		case MotionEvent.ACTION_DOWN:

		//PLAYER
		if(state==State.PLAYERR){
			
		//MONSTERONE
		if((me.getX()>=firstMonster.getX() && me.getX()<=firstMonster.getX()+64) && (me.getY()>=firstMonster.getY() && me.getY()<=firstMonster.getY()+128))
		{
		player.setMonsterDest(firstMonster.getX(), firstMonster.getY());
		player.setAttack(true);
		attackedMonster=firstMonster;
		yMonster=attackedMonster.getY();
		}		
		
		
		if(checkIfMonsterExists(2)){
			if((me.getX()>=secondMonster.getX() && me.getX()<=secondMonster.getX()+64) && (me.getY()>=secondMonster.getY() && me.getY()<=secondMonster.getY()+128))
			{
			player.setMonsterDest(secondMonster.getX(), secondMonster.getY());
			player.setAttack(true);
			attackedMonster=secondMonster;
			yMonster=attackedMonster.getY();
			}		
		}
		
		if(checkIfMonsterExists(3)){
			if((me.getX()>=thirdMonster.getX() && me.getX()<=thirdMonster.getX()+64) && (me.getY()>=thirdMonster.getY() && me.getY()<=thirdMonster.getY()+128))
			{
			player.setMonsterDest(thirdMonster.getX(), thirdMonster.getY());
			player.setAttack(true);
			attackedMonster=thirdMonster;
			yMonster=attackedMonster.getY();
			}		
		}
		
		if(checkIfMonsterExists(4)){
			if((me.getX()>=fourthMonster.getX() && me.getX()<=fourthMonster.getX()+64) && (me.getY()>=fourthMonster.getY() && me.getY()<=fourthMonster.getY()+128))
			{
			player.setMonsterDest(fourthMonster.getX(), fourthMonster.getY());
			player.setAttack(true);
			attackedMonster=fourthMonster;
			yMonster=attackedMonster.getY();
			}		
		}
		}
		//
		
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;
		}
		return true;
	}

}


