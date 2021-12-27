package com.wy.model;

import java.awt.Image;

import com.wy.view.GamePanel;

public class EnemyShot extends Hero {

	public EnemyShot(float x, float y) {
		super(x, y, GamePanel.enemyTamaImage);
	}

	public EnemyShot(float x, float y, float vx, float vy) {
		super(x, y, vx, vy, GamePanel.enemyTamaImage);
	}

	public EnemyShot(float x, float y, float vx, float vy, Image image) {
		super(x, y, vx, vy, image);
	}

	public EnemyShot(float x, float y, Image image) {
		super(x, y, image);
	}
}