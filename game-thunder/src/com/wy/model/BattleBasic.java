package com.wy.model;

import com.wy.view.GamePanel;

public class BattleBasic extends BattleShot {

	public BattleBasic(float x, float y, float vx, float vy) {
		super(x, y, vx, vy, GamePanel.flagImage);
	}
}