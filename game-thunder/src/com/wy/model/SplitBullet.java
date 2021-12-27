package com.wy.model;

import com.wy.view.GamePanel;

public class SplitBullet extends EnemyShot {
	private float x_ziki;

	private float y_ziki;

	private float x_enemy;

	private float y_enemy;

	private float distance;

	private float speed;

	private Battle ziki;

	private int counter;

	public SplitBullet(float x, float y, Battle ziki) {
		super(x, y, GamePanel.splitBulletImage);
		speed = 3F;
		x_ziki = ziki.x;
		y_ziki = ziki.y;
		x_enemy = x;
		y_enemy = y;
		distance = (float) Math.sqrt(
				(x_ziki - x_enemy) * (x_ziki - x_enemy) + (y_ziki - y_enemy) * (y_ziki - y_enemy));
		if (distance != 0.0F) {
			vx = ((x_ziki - x_enemy) / distance) * speed;
			vy = ((y_ziki - y_enemy) / distance) * speed;
		} else {
			vx = 0.0F;
			vy = speed;
		}
		counter = 0;
	}

	public void move() {
		x += vx;
		y += vy;
		if (counter == 50) {
			GamePanel.addList(
					new NBullets(x, y, (int) ((double) vx / 2D), (int) ((double) vy / 2D), ziki));
			dead();
		}
		if (x + WIDTH < 0.0F || x > (float) app.getWidth() || y + HEIGHT < 0.0F
				|| y > (float) app.getHeight())
			dead();
		counter++;
	}

}
