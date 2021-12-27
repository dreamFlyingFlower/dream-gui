package com.wy.model;

public class MessBullets extends EnemyShot {
	private float x_ziki;

	private float y_ziki;

	private float x_enemy;

	private float y_enemy;

	private float distance;

	private float speed;

	private int counter;

	public MessBullets(float x, float y, Battle ziki) {
		super(x, y);
		speed = 3F;
		counter = 0;
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
		for (int i = 20; i >= 0; i--) {
			if (counter % 7 == 0)
				vx += i;
			counter++;
		}

	}

}
