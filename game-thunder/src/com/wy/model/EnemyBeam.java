package com.wy.model;

import com.wy.view.GamePanel;

public class EnemyBeam extends EnemyShot {

	public EnemyBeam(float x, float y, Battle ziki, Enemy enemy) {
		super(x, y, GamePanel.enemyBeamImage);
		this.ziki = ziki;
		this.enemy = enemy;
		vx = 0.0F;
		vy = 3F;
	}

	public void move() {
		x += vx + enemy.vx;
		y += vy + enemy.vy;
		if (x + WIDTH < 0.0F || x > (float) app.getWidth() || y + HEIGHT < 0.0F
				|| y > (float) app.getHeight())
			dead();
	}

	Battle ziki;

	Enemy enemy;
}