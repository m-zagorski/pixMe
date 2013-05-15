package com.example.entities;

import android.graphics.Canvas;

public interface Monsters {
	
	public void onDraw(Canvas canvas);
	public int getX();
	public int getY();
	public void setMonsterTurn(boolean to);
	public void framing();
	public void update();
	public void setPlayerDest(int x, int y);
	public boolean isAttacking();
	public boolean getMonsterTurn();
	public long doDamage();
	public void getDamage(long damage);
	public long getHealth();
	public boolean isMonsterAlive();
	public long getMaxHealth();

}

