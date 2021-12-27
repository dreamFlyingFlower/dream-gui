package com.wy.model;

import java.awt.Image;

public class MoveHomingBullet extends EnemyShot {

	private float vx;

	private float vy;

	private float speed;

	private int count;

	float vx1;

	float vy1;

	private Battle ziki;

	public MoveHomingBullet(float x, float y, float vx, float vy, Battle ziki, Image img) {
		super(x, y, img);
		speed = 6F;
		count = 0;
		this.ziki = ziki;
		this.vx = vx;
		this.vy = vy;
	}

	public void move() {
		if (count > 0)
			count--;
		if (count <= 0) {
			float distance = (float) Math
					.sqrt((((double) ziki.getX() + (double) ziki.getWIDTH() / 2D) - (double) getX())
							* (((double) ziki.getX() + (double) ziki.getWIDTH() / 2D)
									- (double) getX())
							+ (double) ((ziki.getY() - getY()) * (ziki.getY() - getY())));
			if (distance != 0.0F) {
				vx1 = (float) (((((double) ziki.getX() + (double) ziki.getWIDTH() / 2D)
						- (double) getX()) / (double) distance) * (double) speed);
				vy1 = ((ziki.getY() - getY()) / distance) * speed;
			} else {
				vx1 = 0.0F;
				vy1 = speed;
			}
			vx = (vx / (float) Math.sqrt(vx * vx + vy * vy)) * speed;
			vy = (vy / (float) Math.sqrt(vx * vx + vy * vy)) * speed;
			vx = (float) ((double) vx * 0.20000000000000001D + (double) vx1 * 0.80000000000000004D);
			vy = (float) ((double) vy * 0.20000000000000001D + (double) vy1 * 0.80000000000000004D);
			count = 50;
		}
		setX(getX() + vx);
		setY(getY() + vy);
		if (getX() + getWIDTH() < 0.0F || getX() > (float) app.getWidth()
				|| getY() + getHEIGHT() < 0.0F || getY() > (float) app.getHeight())
			dead();
	}
}