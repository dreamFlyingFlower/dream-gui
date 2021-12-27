package com.wy.view;

import java.awt.image.BufferedImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hero extends FlyingObject {
	// 命
	private int life;

	// 火力值
	private int doubleFire;

	// 图片数组
	private BufferedImage[] images;

	// 协助图片切换
	private int index;

	public Hero() {
		image = StartFrame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
		life = 3;
		doubleFire = 0;
		images = new BufferedImage[] { StartFrame.hero0, StartFrame.hero1 };
		index = 0;
	}

	public void step() {
		image = images[index++ / 10 % images.length];
	}

	public Bullet[] shoot() {
		int xStep = this.width / 4;
		if (doubleFire > 0) {
			// 双发
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(this.x + 1 * xStep, this.y - 20);
			bullets[1] = new Bullet(this.x + 3 * xStep, this.y - 20);
			// 发射双倍火力，每次减2，实际就是2倍火力的持续时间
			doubleFire -= 2;
			return bullets;
		} else {
			// 单发
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(this.x + 2 * xStep, this.y - 20);
			return bullets;
		}
	}

	public void moveTo(int x, int y) {
		this.x = x - this.width / 2;
		this.y = y - this.height / 2;
	}

	// 英雄机永不越界
	public boolean outOfBounds() {
		return false;
	}

	public void addLife() {
		life++;
	}

	public void addDoubleFire() {
		doubleFire += 40;
	}

	// 活力值清零
	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}

	// 英雄机撞敌人
	public boolean hit(FlyingObject other) {
		int x1 = other.x - this.width / 2;
		int x2 = other.x + other.width + this.width / 2;
		int y1 = other.y - this.height / 2;
		int y2 = other.y + other.height + this.height / 2;
		int hx = this.x + this.width / 2;
		int hy = this.y + this.height / 2;
		return hx > x1 && hx < x2 && hy > y1 && hy < y2;
	}

	public void subtractLife() {
		life--;
	}
}