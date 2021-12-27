package com.wy.view;

import java.util.Random;

/**
 * @apiNote 飞行物,包括飞机和敌机
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class Airplane extends FlyingObject implements Enemy {
	// 敌机走步的步数
	private int speed = 2;

	public Airplane() {
		image = StartFrame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(StartFrame.WIDTH - this.width);
		y = -this.height;

	}

	public int getScore() {
		return 5;
	}

	public void step() {
		y += speed;
	}

	public boolean outOfBounds() {
		// 敌机的y坐标大于窗口的高
		return this.y > StartFrame.HEIGHT;
	}
}