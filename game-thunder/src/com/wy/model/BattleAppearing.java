package com.wy.model;

import java.awt.Graphics;

import com.wy.view.GamePanel;

public class BattleAppearing {

	static float x = 205F;

	static float y = 572F;

	private static boolean sound = true;

	public BattleAppearing() {
	}

	public static void draw(Graphics g) {
		if (sound) {
			sound = false;
		}
		if (y > 428F)
			g.drawImage(GamePanel.heroImage, (int) x, (int) y, null);
		else if (y <= 428F) {
			GamePanel.AppearingFlag = false;
			x = 205F;
			y = 572F;
			sound = true;
		}
		y -= 2.0F;
	}

}
