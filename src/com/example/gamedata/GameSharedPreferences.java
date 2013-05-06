package com.example.gamedata;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class GameSharedPreferences {

	
	 private static final String USER_PREFS = "USER_PREFS";
	 private SharedPreferences gamePrefs;
	 private SharedPreferences.Editor prefsEditor;

 
	 
	 //MAIN GAME STATUS
	 private String saveExsists="false";
	 private String musicStatus="musicstatus";
	 //--
	 //CHARACTED VARIABLES
	 private String classN="noName";
	 private String level="noLevel";
	 private String experience="noExp";
	 private String money="noMoney";
	 private String attackItems="noAttackItems";
	 private String defenseItems="noDefItems";
	 private String attackBonus="noBonusAttack";
	 private String defenseBonus="noDefenseBonus";
	 private String firstSkillLevel="noFirstLevel";
	 private String secondSkillLevel="noSecondLevel";
	 private String thirdSkillLevel="noThirdLevel";
	 //--
	 
	 //STATISTIC
	 private String spentMoney="soMany";
	 //--
	 
	 public GameSharedPreferences(Context context){
		 this.gamePrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
		 this.prefsEditor = gamePrefs.edit();
	 }

	 public String saveExsists() { return gamePrefs.getString(saveExsists, "false");}	
	 public void createSave(String to) { prefsEditor.putString(saveExsists, to).commit(); }
	 
	 public void createGame(String className){  
	prefsEditor.putString(classN, className).commit();
	prefsEditor.putString(level, "25").commit();
	prefsEditor.putString(experience, "0").commit();
	prefsEditor.putString(money, "500000").commit();
	prefsEditor.putString(attackItems, "0").commit();
	prefsEditor.putString(defenseItems, "0").commit();
	prefsEditor.putString(firstSkillLevel, "1").commit();
	prefsEditor.putString(secondSkillLevel, "1").commit();
	prefsEditor.putString(thirdSkillLevel, "1").commit();
	prefsEditor.putString(spentMoney, "0").commit();
	prefsEditor.putString(defenseBonus, "0").commit();
	prefsEditor.putString(attackBonus, "0").commit();
	 }
	 
	 
	 public String[] characterInfo(){
		 String info[]= new String[4];
		 info[0]=gamePrefs.getString(classN, "noClass");
		 info[1]=gamePrefs.getString(level, "-15");
		 info[2]=gamePrefs.getString(experience, "-50");
		 info[3]=gamePrefs.getString(money, "-555");
		 return info;
	 }
	 
	 
	 public int skillLevel(int id){
		 if (id==0){ return Integer.parseInt(gamePrefs.getString(firstSkillLevel, "-99"));}
		 else if(id==1){return Integer.parseInt(gamePrefs.getString(secondSkillLevel, "-25"));}
		 else{ return Integer.parseInt(gamePrefs.getString(thirdSkillLevel, "-520"));}
	 }
	 
	 
	 public String characterLevel(){
		 return gamePrefs.getString(level, "-15");
	 }
	 
	 public int getCharacterExp(){
		 return Integer.parseInt(gamePrefs.getString(experience, "-152"));
	 }
	  
	 public String getCharacterClass(){
		 return gamePrefs.getString(classN, "noClazz");
	 }
	 
	 public String characterMoney(){
		 return gamePrefs.getString(money, "-555");
	 }
	 
	 public String getMusicStatus(){
		 return gamePrefs.getString(musicStatus, "yes");
	 }
	 public void updateMusic(){
		if(gamePrefs.getString(musicStatus, "asdk").equals("no")){
		prefsEditor.putString(musicStatus, "yes").commit();	
		}
		else
		{
		prefsEditor.putString(musicStatus, "no").commit();		
		}
	 }
	 
	 public void updateCharacterMoney(int value){
		 int current= Integer.parseInt(gamePrefs.getString(money, "-555"));
		 int currentSpent= Integer.parseInt(gamePrefs.getString(spentMoney, "-5555"));
		 current-=value;
		 currentSpent+=value;
		 prefsEditor.putString(money, Integer.toString(current)).commit();
		 prefsEditor.putString(spentMoney, Integer.toString(current)).commit();
	 }
	 
	 public void updateBonus(int defense, int attack){
		 int currentDefense= Integer.parseInt(gamePrefs.getString(defenseBonus, "-500"));
		 int currentAttack= Integer.parseInt(gamePrefs.getString(attackBonus, "-501"));
		 currentDefense+=defense;
		 currentAttack+=attack;
		 prefsEditor.putString(defenseBonus, Integer.toString(currentDefense)).commit();
		 prefsEditor.putString(attackBonus, Integer.toString(currentAttack)).commit();
	 }
	 
	 public void updateSkill(int skillNumber){
	int firtSkillLv = Integer.parseInt(gamePrefs.getString(firstSkillLevel, "-500"));
	int secondSkillLv = Integer.parseInt(gamePrefs.getString(secondSkillLevel, "-500"));
	int thirdSkillLv = Integer.parseInt(gamePrefs.getString(thirdSkillLevel, "50012"));
	if(skillNumber==0){firtSkillLv++;  prefsEditor.putString(firstSkillLevel, Integer.toString(firtSkillLv)).commit();}
	else if(skillNumber==1){secondSkillLv++; prefsEditor.putString(secondSkillLevel, Integer.toString(secondSkillLv)).commit();}
	else {thirdSkillLv++;  prefsEditor.putString(thirdSkillLevel, Integer.toString(thirdSkillLv)).commit();}
	 }
	 
	 
	
	

}

