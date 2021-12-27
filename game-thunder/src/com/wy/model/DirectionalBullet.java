package com.wy.model;

public class DirectionalBullet extends EnemyShot {
	private float speed;

	public DirectionalBullet(float x, float y, float theta, Battle ziki) {
		super(x, y);
		speed = 2.0F;
		vx = (float) (Math.cos(0.017453292519943295D * (double) theta) * (double) speed);
		vy = (float) (Math.sin(0.017453292519943295D * (double) theta) * (double) speed);
	}

}