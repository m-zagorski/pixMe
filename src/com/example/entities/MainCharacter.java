package com.example.entities;

import java.util.Random;

import com.example.combat.OneCharacterMaps.OurView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class MainCharacter implements Characters{
	private int x,y;
	private int playerStartX, playerStartY;
	private double xSpeed, ySpeed;
	private int height, width;
	private Bitmap icon;
	private OurView ov;
	private int currentFrame =0;
	private int direction=0;
	private int destX, destY;
	private State state=State.ONE;
	private int help=0;
	
	private int frameSpeed=0;
	
	private boolean doAttack=false;
	private boolean attack=false;
	
	
	
	
	//CHARACTER VARIABLES
	private int level;
	private long health;
	private int armor;
	private int damage;
	//--
	
	private enum State { ONE, TWO, THREE}


	

	public MainCharacter(OurView ourView, Bitmap bitmap, int x, int y, int level,String charClass) {

		this.level=level;
		icon= bitmap;
		ov=ourView;
		height= icon.getHeight()/10;
		width=icon.getWidth()/4;
		this.x=x;
		playerStartX=x;
		this.y=y;
		playerStartY=y;
		xSpeed = 0;
		ySpeed = 0;
		destX = 0;
		destY=0;
		initializeStats(charClass);
	}
	
	private void initializeStats(String charClass){
		if(charClass.equals("barbarian")){
			health=300;
			damage=50;
			armor=10;
			health+=health*(0.4*level);
			damage+=damage*(0.3*level);
			armor+= 2*level;
			if(armor>60) { armor=60; }
		}
		else if(charClass.equals("hunter")){
			health=250;
			damage=60;
			armor=5;
			health+=health*(0.3*level);
			damage+=damage*(0.4*level);
			armor+= 1*level;
			if(armor>60) { armor=60; }
		}
		else if(charClass.equals("wizard")){
			health=200;
			damage=70;
			armor=1;
			health+=health*(0.2*level);
			damage+=damage*(0.6*level);
		}
	}

	public void onDraw(Canvas canvas) {

	if(doAttack){update();}
	else {if(health>0){direction=0;} else{ direction=2;} framing();}
	int srcX=currentFrame * width;
	int srcY=direction * height;	
	Rect src=new Rect(srcX,srcY,srcX+width,srcY+height);
	Rect dst=new Rect(x,y,x+width, y+height);
	canvas.drawBitmap(icon, src, dst, null);	
	}
	
	
	
	public void setMonsterDest(int x, int y){
		destX=x-x%5;
		destY=y-y%5;
	}



public int getX(){
	return x;
}
public int getY(){
	return y;
}

public void setAttack(boolean to){
	doAttack=to;
}

public boolean done(){
	return doAttack;
}

	

private void framing(){
	frameSpeed++;
	if(frameSpeed==10){currentFrame=++currentFrame%4; frameSpeed=0;}
}

public boolean isAttacking(){
	return attack;
}

public long doDamage(){
	//Random r=new Random();
	int a=new Random().nextInt(4)+1;
	
	
	return (long) (damage*(a*0.4));

}

public long getHealth(){
	return health;
}

public void getDamage(long damage){
	health-=damage;
}


private void update(){
	if(state==State.ONE){
		direction=4;
		framing();
if(x<destX-width){
	xSpeed=2;
	if(y<destY){ ySpeed=2;} 
	else if(y>destY){ ySpeed=-2;}
	else{ ySpeed=0;}
}
else{
	xSpeed=0;
	state=State.TWO;
}}
	else if(state==State.TWO){
		direction=6;
		framing();
		attack=true;
		help++;
		if(help==40){state=State.THREE; help=0; attack=false;}
	}
	else{
		direction=5;
		framing();
	if(x>playerStartX){
		xSpeed=-2;
		if(y>playerStartY){
			ySpeed=-2;
		}
		else if(y<playerStartY){
			ySpeed=2;
		}
		else{ ySpeed=0;}
	}
	else{
		state=State.ONE;
		doAttack=false;
	}
		
	}
	
	
x+=xSpeed;
y+=ySpeed;
}


	
	
	
}