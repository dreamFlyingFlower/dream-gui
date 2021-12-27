package com.wy.model;

import java.awt.Image;

import com.wy.view.GamePanel;

public abstract class Enemy extends Role {
	protected int tamaIntCount;

	protected Battle _battle;

	protected int power;

	protected int counter;

	protected int pattern;

	protected float vx;

	protected float vy;

	protected final int pattern0 = 0;

	protected final int pattern1 = 1;

	protected final int pattern2 = 2;

	protected final int pattern3 = 3;

	protected final int pattern4 = 4;

	protected final int pattern5 = 5;

	protected final int pattern6 = 6;

	protected final int pattern7 = 7;

	public Enemy(Battle battle, Image enemyImage) {
		super(enemyImage);
		this._battle = battle;
		tamaIntCount = 0;
		counter = 0;
	}

	public abstract void move();

	public void checkOutOfScreen() {
		if (y - 100F > (float) app.getHeight() || x + WIDTH + 100F < 0.0F
				|| x - 100F > (float) app.getWidth() || y + HEIGHT + 100F < 0.0F)
			dead();
	}

	public boolean checkHit(Role chara) {
		if ((chara instanceof BattleShot) && super.checkHit(chara)) {
			chara.dead();
			power--;
			if (power <= 0) {
				GamePanel.burst = new Burst(x, y);
				dead();
			}
			return true;
		} else {
			return false;
		}
	}
}