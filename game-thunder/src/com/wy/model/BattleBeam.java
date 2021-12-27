package com.wy.model;

import com.wy.view.GamePanel;

public class BattleBeam extends BattleShot {

	public BattleBeam(float x, float y, float vx, float vy) {
		super(x, y, vx, vy, GamePanel.zikiBeamImage);
	}

	public void move() {
		if (Key.xkey) {
			x += vx + Battle.vx;
			y += vy + Battle.vy;
		} else {
			x += vx;
			y += vy;
		}
		if (x + WIDTH < 0.0F || x > (float) app.getWidth() || y + HEIGHT < 0.0F
				|| y > (float) app.getHeight())
			dead();
	}
}