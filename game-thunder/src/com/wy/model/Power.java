package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;

public class Power {

	private int power;

	private int powerMax;

	public Power(int power) {
		this.power = power;
		powerMax = power;
	}

	public void damage(int dmg) {
		power -= dmg;
	}

	public int getPower() {
		return power;
	}

	public void reflesh() {
		power = powerMax;
	}

	public void drawZikiPower(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(380, 450, 50, 15);
		g.setColor(Color.green);
		g.fillRect(381, 451, (int) ((50D / (double) (float) powerMax) * (double) power) - 1, 14);
	}

	public void drawBossPower(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(200, 20, 150, 15);
		g.setColor(Color.green);
		g.fillRect(201, 21, (int) ((150D / (double) (float) powerMax) * (double) power) - 1, 14);
	}

}
