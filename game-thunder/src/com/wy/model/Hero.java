package com.wy.model;

import java.awt.Image;

public class Hero extends Role {
	protected float vx;

	protected float vy;

	public Hero(float x, float y, Image tamaImage) {
		super(tamaImage);
		this.setX(x - getWIDTH() / 2.0F);
		this.setY(y - getHEIGHT() / 2.0F);
	}

	public Hero(float x, float y, float vx, float vy, Image tamaImage) {
		super(tamaImage);
		this.setX(x - getWIDTH() / 2.0F);
		this.setY(y - getHEIGHT() / 2.0F);
		this.vx = vx;
		this.vy = vy;
	}

	public void move() {
		setX(getX() + vx);
		setY(getY() + vy);
		if (getX() + getWIDTH() < 0.0F || getX() > (float) app.getWidth()
				|| getY() + getHEIGHT() < 0.0F || getY() > (float) app.getHeight())
			dead();
	}

	public boolean checkHit(Role chara) {
		return false;
	}
}