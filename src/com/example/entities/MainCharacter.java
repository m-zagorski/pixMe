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
	private int animatehelp=0;
	
	private int frameSpeed=0;
	
	private boolean doAttack=false;
	private boolean attack=false;
	private boolean firstSkill=false;
	private boolean secondSkill=false;
	private boolean thirdSkill=false;
	private boolean animateSkill=false;
	private boolean playerDead=false;
	
	
	
	
	//CHARACTER VARIABLES
	private String characterClass;
	private int level;
	private long health;
	private long totalHealth;
	private int armor;
	private int damage;
	private int plusDamage;
	private int plusArmor;
	private int firstSkillLevel;
	private int secondSkillLevel;
	private int thirdSkillLevel;
	//--
	
	private enum State { ONE, TWO, THREE}


	

	public MainCharacter(OurView ourView, Bitmap bitmap, int x, int y, int level,String charClass, int[] characterSkills) {

	
		characterClass=charClass;
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
		
		plusArmor=characterSkills[0];
		plusDamage=characterSkills[1];
		firstSkillLevel=characterSkills[2];
		secondSkillLevel=characterSkills[3];
		thirdSkillLevel=characterSkills[4];
		
		initializeStats(charClass);
	}
	
	private void initializeStats(String charClass){
		if(charClass.equals("barbarian")){
			health=300;
			damage=100;
			armor=10;
			health+=health*(0.4*level);
			damage+=damage*(0.3*level);
			totalHealth=health;
			armor+= 2*level;
			if(armor>60) { armor=60; }
		}
		else if(charClass.equals("hunter")){
			health=250;
			damage=110;
			armor=5;
			health+=health*(0.3*level);
			damage+=damage*(0.4*level);
			totalHealth=health;
			armor+= 1*level;
			if(armor>60) { armor=60; }
		}
		else if(charClass.equals("wizard")){
			health=200;
			damage=120;
			armor=1;
			health+=health*(0.2*level);
			damage+=damage*(0.6*level);
			totalHealth=health;
		}
	}

	public void onDraw(Canvas canvas) {
		if(animateSkill){ animate(); }
		else if((doAttack && characterClass.equals("wizard")) || (doAttack && characterClass.equals("hunter")))
			{
		rangedAttack();
			}
	else if(doAttack && characterClass.equals("barbarian")){update();}
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

	

	
public void specialAttack(String which){
	if(which.equals("first")){ firstSkill=true;}
	else if(which.equals("second")) { secondSkill=true; }
	else { thirdSkill= true; }
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

public void startAnimateSkill(){
	animateSkill=true;
}

private void framing(){
	frameSpeed++;
	if(frameSpeed==10){currentFrame=++currentFrame%4; frameSpeed=0;}
}

public boolean isAttacking(){
	return attack;
}

public long doDotDamage(){
	if(characterClass.equals("barbarian")){
		return (long) (damage*(0.15+(secondSkillLevel*0.02)));
		}
	else if(characterClass.equals("wizard")){
		return (long) (damage*(0.3+(secondSkillLevel*0.03)));
	}
	else {
		return 0;
	}
}

public long doDamage(){
if(firstSkill){
if(characterClass.equals("barbarian")){
	return (long) (totalHealth* (0.25+(firstSkillLevel*0.02)));
}
if(characterClass.equals("wizard")){
return (long) (damage*(0.5+(firstSkillLevel*0.05)));	
}
else { 
return (long) (damage*(0.4+(firstSkillLevel*0.04)));
}
}
else if(secondSkill){

	return 0;

	
	
}
else if(thirdSkill){
	if(characterClass.equals("barbarian")){ 
	return (long) (damage*(1.22+(thirdSkillLevel*0.3)));	
	}
	if(characterClass.equals("wizard")){
		return (long) (damage*(1.55+(thirdSkillLevel*0.4)));		
	}
	else{
		return (long) (damage*(1.33+(thirdSkillLevel*0.35)));		
	}
	
	
}
else{
	int a=new Random().nextInt(4)+1;
	return (long) (damage*(a*0.4));
}

}

public long getHealth(){
	return health;
}

public void getDamage(long damage){
	if(characterClass.equals("barbarian") && firstSkill){
	health+=health;
	if(health>totalHealth){ health=totalHealth; }
	}
	else{
	health-=damage;
	if(health<=0){ health=0; playerDead=true;}
	}
}
private void animate(){
	if(characterClass.equals("barbarian")) { direction=1;} 
	else if(characterClass.equals("wizard")) { direction=7;}
	else { direction=8;}
	framing();
	animatehelp++;
	if(animatehelp==40){animatehelp=0; animateSkill=false; secondSkill=false;}
	
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
	
	if(characterClass.equals("barbarian") && thirdSkill) { direction=6;} 
	else if(characterClass.equals("wizard") && thirdSkill) {direction=9; }
	else if(characterClass.equals("hunter") && thirdSkill){ direction=8;}
	else{	direction=6; } 
		framing();
		attack=true;
		help++;
		if(help==40){state=State.THREE; help=0; attack=false; thirdSkill=false;}
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
		firstSkill=false;  thirdSkill=false;
	}
		
	}
	
	
x+=xSpeed;
y+=ySpeed;
}

public void secondSkillDone(){
	secondSkill=false;
}

private void rangedAttack(){


	if(characterClass.equals("wizard") && thirdSkill) {direction=8; }
	else if(characterClass.equals("hunter") && thirdSkill){ direction=7;}
	else{	direction=6; } 
		framing();
		attack=true;
		help++;
		if(help==40){help=0; attack=false; direction=2; doAttack=false;
		firstSkill=false;  thirdSkill=false;}


}


	
	
	
}