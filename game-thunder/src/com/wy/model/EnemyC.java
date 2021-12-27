package com.wy.model;

import com.wy.view.GamePanel;

public class EnemyC extends Enemy {
	private float vx;

	private float vy;

	public EnemyC(float x, float y, Battle battle, int pattern) {
		super(battle, GamePanel.enemyImageC);
		vx = 0.0F;
		vy = 3F;
		this._battle = battle;
		this.x = x;
		this.y = y;
		this.pattern = pattern;
		power = 5;
	}

	public void move() {
		if (pattern == 0) {
			if (counter < 60) {
				x += vx;
				y += vy;
			} else if (counter > 250) {
				x += vx;
				y -= vy;
			}
		} else if (pattern == 1)
			if (counter < 60) {
				x -= vx;
				y += vy;
			} else if (counter > 150) {
				x += vx;
				y -= vy;
			}
		checkOutOfScreen();
		if (tamaIntCount > 0)
			tamaIntCount--;
		if (tamaIntCount <= 0) {
			GamePanel.addList(new SplitBullet((int) ((double) x + (double) WIDTH / 2D),
					(int) (y + HEIGHT), _battle));
			tamaIntCount = 90;
		}
		counter++;
	}
}