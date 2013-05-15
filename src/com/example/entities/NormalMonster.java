package com.example.entities;

import java.util.Random;

import com.example.combat.OneCharacterMaps.OurView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class NormalMonster implements Monsters{
	public 	int x,y;
	private int monsterStartX, monsterStartY;
	private double xSpeed;
	private double ySpeed;
	private int height, width;
	private Bitmap icon;
	private OurView ov;
	private int currentFrame =0;
	private int direction=0;
	public int destX, destY;
	private State state=State.ONE;
	private int frameSpeed=0;
	private int attackLength=0;

	private boolean monsterTurn=false;
	private boolean monsterAttacking=false;
	private boolean monsterAlive=true;


	public long health;
	public long maxHealth;
	public int armor;
	public int damage;
	//private int playerLevel;
	
	String[] coords=null;

	public enum State { ONE, TWO, THREE}

	public NormalMonster(OurView ourView, Bitmap bitmap, int x, int y,String monsterType, long health, int armor, int damage, int playerLevel,String coords) {
		this.damage=damage;
		this.health=health;
		//this.playerLevel=playerLevel;
		this.coords=coords.split(" ");
		icon= bitmap;
		ov=ourView;
		height= icon.getHeight()/Integer.parseInt(this.coords[0]);
		width=icon.getWidth()/4;
		this.x=x;
		monsterStartX=x;
		this.y=y;
		monsterStartY=y;
		xSpeed = 0;
		ySpeed = 0;
		destX = 0;
		destY=0;
		initializeStats(playerLevel,monsterType);

	}

	
	private void initializeStats(int playerLevel, String monsterType){
		if(monsterType.equals("normal")){
			armor+=playerLevel;
			if(armor>45) { armor=45; }
			health+= health*(0.25*playerLevel);
			damage+= damage*(0.3*playerLevel);
		}
		else if(monsterType.equals("shielding")){
			armor+=3*playerLevel;
			if(armor>75) { armor=75; }
			health+= health*(0.4*playerLevel);
			damage+= damage*(0.15*playerLevel);
		}
		else if(monsterType.equals("ranged")){
			health+= health*(0.2*playerLevel);
			damage+= damage*(0.5*playerLevel);
		}
		else if(monsterType.equals("boss")){
			armor+=5*playerLevel;
			if(armor>60) { armor=60;}
			health+= health*(0.5*playerLevel);
			damage+= damage*(0.7*playerLevel);
		}
		maxHealth=health;
	}
	
	
	public long getMaxHealth(){
		return maxHealth;
	}

	public void onDraw(Canvas canvas) {

	if(monsterTurn){update();}
	else {if(health>0){direction=Integer.parseInt(this.coords[1]);} else{ direction=Integer.parseInt(this.coords[5]);} framing();}
	int srcX=currentFrame * width;
	int srcY=direction * height;	
	Rect src=new Rect(srcX,srcY,srcX+width,srcY+height);
	Rect dst=new Rect(x,y,x+width, y+height);
	canvas.drawBitmap(icon, src, dst, null);	
	}




	public void setPlayerDest(int x, int y){
		destX=x-x%5;
		destY=y-y%5;
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

	public void setMonsterTurn(boolean to){
		if(monsterAlive) {monsterTurn=to; }
		else { monsterTurn=false;}
	}

	public boolean getMonsterTurn(){
		return monsterTurn;
	}

	public boolean isAttacking(){
		return monsterAttacking;
	}

	public void framing(){
		frameSpeed++;
		if(frameSpeed==10){currentFrame=++currentFrame%4; frameSpeed=0;}
	}


	public void update(){
		
	if(state==State.ONE){
		direction=Integer.parseInt(this.coords[2]);
		framing();
	if(x>destX+96){
		xSpeed=-2;
		if(y>destY){ ySpeed=-2;}
		else if(y<destY){ ySpeed=2;}
		else{ ySpeed=0;}
	}
	else{ xSpeed=0; 
	state=State.TWO;}
	}
	else if( state==State.TWO){
		direction=Integer.parseInt(this.coords[4]);
		framing();
		monsterAttacking=true;
		attackLength++;
		if(attackLength==40){state=State.THREE; attackLength=0; monsterAttacking=false;}
	}
	else {
		direction=Integer.parseInt(this.coords[3]);
		framing();
		if(x<monsterStartX){
			xSpeed=2;
			if(y>monsterStartY){ ySpeed=-2;}
			else if(y<monsterStartY){ ySpeed=2; }
			else { ySpeed=0;}
		}
		else {
			xSpeed=0;
			state=State.ONE;
			monsterTurn=false;
		}
	}



	x+=xSpeed;
	y+=ySpeed;

	}


	@Override
	public long doDamage() {
		//Random r=new Random();
		int a=new Random().nextInt(4)+1;
		return (long) (damage*(a*0.4));
	}


	@Override
	public void getDamage(long damage) {
		health-=damage;
		if(health<=0){ health=0; monsterAlive=false;}
		
	}


	@Override
	public long getHealth() {
		return health;
	}


	@Override
	public boolean isMonsterAlive() {
		return monsterAlive;
	}




	}
