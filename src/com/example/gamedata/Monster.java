package com.example.gamedata;

public class Monster {
private String icon, type, health, damage, armor, coords;

public Monster(String icon, String type, String health, String damage,
		String armor, String coords) {
	super();
	this.icon = icon;
	this.type = type;
	this.health = health;
	this.damage = damage;
	this.armor = armor;
	this.coords = coords;
}

public String getIcon() {
	return icon;
}

public void setIcon(String icon) {
	this.icon = icon;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getHealth() {
	return health;
}

public void setHealth(String health) {
	this.health = health;
}

public String getDamage() {
	return damage;
}

public void setDamage(String damage) {
	this.damage = damage;
}

public String getArmor() {
	return armor;
}

public void setArmor(String armor) {
	this.armor = armor;
}

public String getCoords() {
	return coords;
}

public void setCoords(String coords) {
	this.coords = coords;
}
	
	
}
