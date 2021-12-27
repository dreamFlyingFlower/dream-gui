package com.wy.model;

import com.wy.view.GamePanel;

public class NBullets extends EnemyShot {
	private float vxNWay[];

	private float vyNWay[];

	private float theta;

	private int n;

	float vx1;

	float vy1;

	public NBullets(float x, float y, float vx0, float vy0, Battle ziki) {
		super(x, y);
		theta = 15F;
		n = 12;
		vxNWay = new float[n];
		vyNWay = new float[n];
		float rad_step = (float) (0.017453292519943295D * (double) theta);
		float rad = (float) (((double) (-n / 2) + 0.5D) * (double) rad_step);
		for (int i = 0; i < n;) {
			float c = (float) Math.cos(rad);
			float s = (float) Math.sin(rad);
			vxNWay[i] = vx0 * c - vy0 * s;
			vyNWay[i] = vx0 * s + vy0 * c;
			GamePanel.addList(new EnemyShot(x, y, vxNWay[i], vyNWay[i]));
			i++;
			rad += rad_step;
		}

		vx = vxNWay[0];
		vy = vyNWay[0];
	}
}