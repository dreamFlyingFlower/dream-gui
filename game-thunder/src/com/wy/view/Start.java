package com.wy.view;

import java.awt.Color;
import java.awt.Graphics;

public class Start {
	int x;

	int y;

	int vy;

	int width;

	Color color;

	static int num_Star = 20;

	public Start(int x, int y, int vy, int width, Color color) {
		this.x = x;
		this.y = y;
		this.vy = vy;
		this.width = width;
		this.color = color;
	}

	public boolean death() {
		// y>500证明已经不可见即死亡
		return y > 500;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		// 使用当前颜色填充外接指定矩形框的椭圆
		g.fillOval(x, y, width, width);
	}

	public void move() {
		y += vy;
	}
}