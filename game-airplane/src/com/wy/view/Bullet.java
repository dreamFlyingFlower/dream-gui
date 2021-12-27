package com.wy.view;

/**
 * @apiNote 子弹
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class Bullet extends FlyingObject {
	// 子弹走步步数，只有y坐标在变
	private int speed = 3;

	/**
	 * 子弹的坐标随着英雄机的变化而变化
	 * 
	 * @param x
	 * @param y
	 */
	public Bullet(int x, int y) {
		image = StartFrame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}

	public void step() {
		y -= speed;
	}

	public boolean outOfBounds() {
		return this.y < -this.height;
	}
}