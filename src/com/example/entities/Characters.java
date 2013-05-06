package com.example.entities;

import android.graphics.Canvas;

public interface Characters {
	public void onDraw(Canvas canvas);
	public void setMonsterDest(int x, int y);
	public int getX();
	public int getY();
	public void setAttack(boolean to);
	public boolean done();
	public boolean isAttacking();
	public long doDamage();
	public long getHealth();
	public void getDamage(long damage);
	
}
