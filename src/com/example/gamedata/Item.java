package com.example.gamedata;

public class Item {
	private String type, icon, level, armor, damage, buy;

	public Item(String type, String icon, String level, String damage,
			String armor, String buy) {
		this.type = type;
		this.icon = icon;
		this.level = level;
		this.armor = armor;
		this.damage = damage;
		this.buy = buy;

	}

	public String getType() {
		return type;
	}

	public String getIcon() {
		return icon;
	}

	public String getLevel() {
		return level;
	}

	public String getArmor() {
		return armor;
	}

	public String getDamage() {
		return damage;
	}

	public String getBuy() {
		return buy;
	}

}
