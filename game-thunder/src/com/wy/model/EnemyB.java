package com.wy.model;

import com.wy.view.GamePanel;

public class EnemyB extends Enemy {

	public EnemyB(float x, float y, Battle battle, int pattern) {
		super(battle, GamePanel.enemyImageB);
		this._battle = battle;
		this.x = x;
		this.y = y;
		vx = 0.0F;
		vy = 2.0F;
		this.pattern = pattern;
		power = 25;
	}

	public void move() {
		if (pattern == 0)
			if (counter < 100)
				y += vy;
			else if (counter > 300)
				y -= vy;
		checkOutOfScreen();
		if (tamaIntCount > 0)
			tamaIntCount--;
		if (counter > 100 && counter < 280 && tamaIntCount <= 0) {
			GamePanel.addList(new EnemyBeam(x + WIDTH / 2.0F, y + HEIGHT, _battle, this));
			tamaIntCount = 2;
		}
		counter++;
	}
}
