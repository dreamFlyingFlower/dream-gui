package com.wy.model;

import com.wy.view.GamePanel;

public class CircleBullets extends EnemyShot {

	private float speed;

	private float vxCircle[];

	private float vyCircle[];

	private int tamaNum;

	public CircleBullets(float x, float y, boolean flag) {
		super(x, y);
		speed = 1.0F;
		tamaNum = 24;
		vxCircle = new float[tamaNum];
		vyCircle = new float[tamaNum];
		float rad_step = (float) (6.2831853071795862D / (double) tamaNum);
		float rad;
		if (flag)
			rad = 0.0F;
		else
			rad = (float) ((double) rad_step / 2D);
		for (int i = 0; i < tamaNum;) {
			vxCircle[i] = (float) (Math.cos(rad) * (double) speed);
			vyCircle[i] = (float) (Math.sin(rad) * (double) speed);
			GamePanel.addList(new EnemyShot(x, y, vxCircle[i], vyCircle[i]));
			i++;
			rad += rad_step;
		}

		vx = vxCircle[0];
		vy = vyCircle[0];
	}

}
