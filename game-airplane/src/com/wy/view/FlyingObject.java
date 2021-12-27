package com.wy.view;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected BufferedImage image;

	protected int width;

	protected int height;

	protected int x;

	protected int y;

	// 飞行物走步
	public abstract void step();

	// 越界方法
	public abstract boolean outOfBounds();

	// 敌人被子弹撞
	public boolean shootBy(Bullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.width;
		int y1 = this.y;
		int y2 = this.y + this.height;
		int x = bullet.x;
		int y = bullet.y;
		return x > x1 && x < x2 && y > y1 && y < y2;
	}
}