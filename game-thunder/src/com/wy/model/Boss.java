package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public abstract class Boss extends Enemy {

	protected int powerMax;

	public Boss(Battle battle, Image enemyImage) {
		super(battle, enemyImage);
	}

	public abstract void move();

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return WIDTH;
	}

	public float getHeight() {
		return HEIGHT;
	}

	public int getPower() {
		return power;
	}

	public Image getImage() {
		return img;
	}

	public void drawPower(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(200, 20, 150, 15);
		g.setColor(Color.green);
		g.fillRect(201, 21, (int) ((150D / (double) (float) powerMax) * (double) power) - 1, 14);
	}

	public int getPowerMax() {
		return powerMax;
	}
}