package com.example.gamedata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class GameSharedPreferences {

	private static final String USER_PREFS = "USER_PREFS";
	private SharedPreferences gamePrefs;
	private SharedPreferences.Editor prefsEditor;

	// MAIN GAME STATUS
	private String saveExsists = "false";
	private String musicStatus = "musicstatus";
	private String firstLaunch = "nofirstLaunch";
	// --
	// CHARACTED VARIABLES
	private String classN = "noName";
	private String level = "noLevel";
	private String experience = "noExp";
	private String money = "noMoney";
	private String attackItems = "noAttackItems";
	private String defenseItems = "noDefItems";
	private String attackBonus = "noBonusAttack";
	private String defenseBonus = "noDefenseBonus";
	private String firstSkillLevel = "noFirstLevel";
	private String secondSkillLevel = "noSecondLevel";
	private String thirdSkillLevel = "noThirdLevel";

	private String wins = "nopezNoWins";
	private String loses = "nopezNoLoses";
	private String combatDescription = "noCombatDescription";
	private String combatTitle = "nopezXs";
	private String combatDesc = "noxTY";
	// --

	// STATISTIC
	private String spentMoney = "soMany";

	// --

	public GameSharedPreferences(Context context) {
		this.gamePrefs = context.getSharedPreferences(USER_PREFS,
				Activity.MODE_PRIVATE);
		this.prefsEditor = gamePrefs.edit();
	}

	public String saveExsists() {
		return gamePrefs.getString(saveExsists, "false");
	}

	public void createSave(String to) {
		prefsEditor.putString(saveExsists, to).commit();
	}

	public void createGame(String className) {
		prefsEditor.putString(classN, className).commit();
		prefsEditor.putString(level, "1").commit();
		prefsEditor.putString(experience, "500").commit();
		prefsEditor.putString(money, "500000").commit();
		prefsEditor.putString(attackItems, "5").commit();
		prefsEditor.putString(defenseItems, "6").commit();
		prefsEditor.putString(firstSkillLevel, "1").commit();
		prefsEditor.putString(secondSkillLevel, "1").commit();
		prefsEditor.putString(thirdSkillLevel, "1").commit();
		prefsEditor.putString(spentMoney, "1000").commit();
		prefsEditor.putString(defenseBonus, "50").commit();
		prefsEditor.putString(attackBonus, "90").commit();
		prefsEditor.putString(wins, "5").commit();
		prefsEditor.putString(loses, "3").commit();
		prefsEditor.putString(combatDescription, "noCombat").commit();
	}

	public String[] characterInfo() {
		String info[] = new String[4];
		info[0] = gamePrefs.getString(classN, "noClass");
		info[1] = gamePrefs.getString(level, "-15");
		info[2] = gamePrefs.getString(experience, "-50");
		info[3] = gamePrefs.getString(money, "-555");
		return info;
	}

	public String[] statisticInfo() {
		String info[] = new String[10];
		info[0] = gamePrefs.getString(level, "-15");
		info[1] = gamePrefs.getString(experience, "-5012");
		info[2] = gamePrefs.getString(money, "-5012");
		info[3] = gamePrefs.getString(attackBonus, "-5012");
		info[4] = gamePrefs.getString(defenseBonus, "-5012");
		info[5] = gamePrefs.getString(wins, "-5012");
		info[6] = gamePrefs.getString(loses, "-5012");
		info[7] = gamePrefs.getString(spentMoney, "-5012");
		info[8] = gamePrefs.getString(attackItems, "-5012");
		info[9] = gamePrefs.getString(defenseItems, "-5012");
		return info;
	}

	public int skillLevel(int id) {
		if (id == 0) {
			return Integer
					.parseInt(gamePrefs.getString(firstSkillLevel, "-99"));
		} else if (id == 1) {
			return Integer.parseInt(gamePrefs
					.getString(secondSkillLevel, "-25"));
		} else {
			return Integer.parseInt(gamePrefs
					.getString(thirdSkillLevel, "-520"));
		}
	}

	public String characterLevel() {
		return gamePrefs.getString(level, "-15");
	}

	public boolean ifFirstLaunch() {
		if (gamePrefs.getString(firstLaunch, "no").equals("no")) {
			return true;
		} else {
			return false;
		}
	}

	public void changeCombatStatus() {
		prefsEditor.putString(combatDescription, "noCombat").commit();
	}

	public void changeFirstLaunch() {
		prefsEditor.putString(firstLaunch, "yes").commit();
	}

	public int getCharacterExp() {
		return Integer.parseInt(gamePrefs.getString(experience, "-152"));
	}

	public String getCharacterClass() {
		return gamePrefs.getString(classN, "noClazz");
	}

	public String characterMoney() {
		return gamePrefs.getString(money, "-555");
	}

	public String getMusicStatus() {
		return gamePrefs.getString(musicStatus, "yes");
	}

	public void updateMusic() {
		if (gamePrefs.getString(musicStatus, "asdk").equals("no")) {
			prefsEditor.putString(musicStatus, "yes").commit();
		} else {
			prefsEditor.putString(musicStatus, "no").commit();
		}
	}

	public void updateCharacterMoney(int value) {
		int current = Integer.parseInt(gamePrefs.getString(money, "-555"));
		int currentSpent = Integer.parseInt(gamePrefs.getString(spentMoney,
				"-5555"));
		current -= value;
		currentSpent += value;
		prefsEditor.putString(money, Integer.toString(current)).commit();
		prefsEditor.putString(spentMoney, Integer.toString(current)).commit();
	}

	public void updateBonus(int defense, int attack) {
		int currentDefense = Integer.parseInt(gamePrefs.getString(defenseBonus,
				"-500"));
		int currentAttack = Integer.parseInt(gamePrefs.getString(attackBonus,
				"-501"));
		currentDefense += defense;
		currentAttack += attack;
		prefsEditor.putString(defenseBonus, Integer.toString(currentDefense))
				.commit();
		prefsEditor.putString(attackBonus, Integer.toString(currentAttack))
				.commit();
	}

	public int[] getCharacterSkills() {
		int skills[] = new int[5];
		skills[0] = Integer.parseInt(gamePrefs.getString(defenseBonus, "-500"));
		skills[1] = Integer.parseInt(gamePrefs.getString(attackBonus, "-500"));
		skills[2] = Integer.parseInt(gamePrefs.getString(firstSkillLevel,
				"-500"));
		skills[3] = Integer.parseInt(gamePrefs.getString(secondSkillLevel,
				"-500"));
		skills[4] = Integer.parseInt(gamePrefs.getString(thirdSkillLevel,
				"-500"));

		return skills;
	}

	public void setWinLose(String state, int money, int experience) {
		int actual;
		prefsEditor.putString(combatDescription, "Combat").commit();
		if (state.equals("lose")) {
			actual = Integer.parseInt(gamePrefs.getString(loses, "-500"));
			actual++;
			prefsEditor.putString(loses, Integer.toString(actual)).commit();
			prefsEditor.putString(combatTitle, "You have lost!").commit();
			prefsEditor
					.putString(combatDesc,
							"You may want to buy\n more items in the shop and then\n try again.")
					.commit();
		} else {
			int goldAlready = Integer.parseInt(gamePrefs.getString(this.money,
					"-500"));
			int expAlready = Integer.parseInt(gamePrefs.getString(
					this.experience, "-500"));
			goldAlready += money;
			expAlready += experience;
			actual = Integer.parseInt(gamePrefs.getString(wins, "-500"));
			actual++;
			levelUp();
			prefsEditor.putString(combatTitle, "You have won!").commit();
			prefsEditor.putString(
					combatDesc,
					"Congratulations! \nGold earned: " + money
							+ "\nExperience earned: " + experience).commit();
			prefsEditor.putString(loses, Integer.toString(actual)).commit();
			prefsEditor.putString(this.money, Integer.toString(goldAlready))
					.commit();
			prefsEditor
					.putString(this.experience, Integer.toString(expAlready))
					.commit();
		}
	}

	private void levelUp() {
		int tempExp = 50;
		for (int i = 1; i <= Integer.parseInt(gamePrefs
				.getString(level, "-500")); i++) {
			tempExp = tempExp * 2;
		}
		if (Integer.parseInt(gamePrefs.getString(experience, "-500")) > tempExp) {
			int currentLv = Integer
					.parseInt(gamePrefs.getString(level, "-500"));
			currentLv++;
			prefsEditor.putString(level, Integer.toString(currentLv)).commit();
		}
	}

	public void addLevel() {
		int currentLv = Integer.parseInt(gamePrefs.getString(level, "-500"));
		currentLv++;
		prefsEditor.putString(level, Integer.toString(currentLv)).commit();
	}

	public String checkIfCombat() {
		return gamePrefs.getString(combatDescription, "noCombat");
	}

	public String getCombatTitle() {
		return gamePrefs.getString(combatTitle, "noPEzs");
	}

	public String getCombatDesc() {
		return gamePrefs.getString(combatDesc, "-500");
	}

	public void updateSkill(int skillNumber) {
		int firtSkillLv = Integer.parseInt(gamePrefs.getString(firstSkillLevel,
				"-500"));
		int secondSkillLv = Integer.parseInt(gamePrefs.getString(
				secondSkillLevel, "-500"));
		int thirdSkillLv = Integer.parseInt(gamePrefs.getString(
				thirdSkillLevel, "50012"));
		if (skillNumber == 0) {
			firtSkillLv++;
			prefsEditor.putString(firstSkillLevel,
					Integer.toString(firtSkillLv)).commit();
		} else if (skillNumber == 1) {
			secondSkillLv++;
			prefsEditor.putString(secondSkillLevel,
					Integer.toString(secondSkillLv)).commit();
		} else {
			thirdSkillLv++;
			prefsEditor.putString(thirdSkillLevel,
					Integer.toString(thirdSkillLv)).commit();
		}
	}

	public void decreaseSkill(int skillNumber) {
		int firtSkillLv = Integer.parseInt(gamePrefs.getString(firstSkillLevel,
				"-500"));
		int secondSkillLv = Integer.parseInt(gamePrefs.getString(
				secondSkillLevel, "-500"));
		int thirdSkillLv = Integer.parseInt(gamePrefs.getString(
				thirdSkillLevel, "50012"));
		if (skillNumber == 0) {
			if (firtSkillLv > 1) {
				firtSkillLv--;
				prefsEditor.putString(firstSkillLevel,
						Integer.toString(firtSkillLv)).commit();
			}
		} else if (skillNumber == 1) {
			if (secondSkillLv > 1) {
				secondSkillLv--;
				prefsEditor.putString(secondSkillLevel,
						Integer.toString(secondSkillLv)).commit();
			}
		} else {
			if (thirdSkillLv > 1) {
				thirdSkillLv--;
				prefsEditor.putString(thirdSkillLevel,
						Integer.toString(thirdSkillLv)).commit();
			}
		}
	}

}
