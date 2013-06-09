package com.example.gamedata;

public class Map {
	private String name, monstersCount, basegold, baseexperience;

	public Map(String name, String monstersCount, String basegold,
			String baseexperience) {
		super();
		this.name = name;
		this.monstersCount = monstersCount;
		this.basegold = basegold;
		this.baseexperience = baseexperience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMonstersCount() {
		return monstersCount;
	}

	public void setMonstersCount(String monstersCount) {
		this.monstersCount = monstersCount;
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
