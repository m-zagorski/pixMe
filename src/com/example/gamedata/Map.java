package com.example.gamedata;

public class Map {
	private String name, normalmonsters, rangedmonsters, shieldingmonsters, boss, basegold, baseexperience;

	public Map(String name, String normalmonsters, String rangedmonsters,
			String shieldingmonsters, String boss, String basegold,
			String baseexperience) {
		super();
		this.name = name;
		this.normalmonsters = normalmonsters;
		this.rangedmonsters = rangedmonsters;
		this.shieldingmonsters = shieldingmonsters;
		this.boss = boss;
		this.basegold = basegold;
		this.baseexperience = baseexperience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNormalmonsters() {
		return normalmonsters;
	}

	public void setNormalmonsters(String normalmonsters) {
		this.normalmonsters = normalmonsters;
	}

	public String getRangedmonsters() {
		return rangedmonsters;
	}

	public void setRangedmonsters(String rangedmonsters) {
		this.rangedmonsters = rangedmonsters;
	}

	public String getShieldingmonsters() {
		return shieldingmonsters;
	}

	public void setShieldingmonsters(String shieldingmonsters) {
		this.shieldingmonsters = shieldingmonsters;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public String getBasegold() {
		return basegold;
	}

	public void setBasegold(String basegold) {
		this.basegold = basegold;
	}

	public String getBaseexperience() {
		return baseexperience;
	}

	public void setBaseexperience(String baseexperience) {
		this.baseexperience = baseexperience;
	}

	

}
