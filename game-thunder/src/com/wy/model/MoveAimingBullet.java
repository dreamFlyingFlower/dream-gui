package com.wy.model;

public class MoveAimingBullet extends EnemyShot {

	public int t_loon_framework;

	private float x_ziki;

	private float y_ziki;

	private float x_enemy;

	private float y_enemy;

	private float distance;

	private float speed;

	public MoveAimingBullet(float x, float y, Battle ziki) {
		super(x, y);
		speed = 2.0F;
		x_ziki = ziki.x;
		y_ziki = ziki.y;
		x_enemy = x;
		y_enemy = y;
		distance = (float) Math
				.sqrt((((double) x_ziki + (double) ziki.WIDTH / 2D) - (double) x_enemy)
						* (((double) x_ziki + (double) ziki.WIDTH / 2D) - (double) x_enemy)
						+ (double) ((y_ziki - y_enemy) * (y_ziki - y_enemy)));
		if (distance != 0.0F) {
			vx = (float) (((((double) x_ziki + (double) ziki.WIDTH / 2D) - (double) x_enemy)
					/ (double) distance) * (double) speed);
			vy = ((y_ziki - y_enemy) / distance) * speed;
		} else {
			vx = 0.0F;
			vy = speed;
		}
	}
}