package com.wy.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.wy.view.GamePanel;

public class Stage1Start {

	private static int count = 0;

	public Stage1Start() {
	}

	public static void draw(Graphics g) {
		if (count <= 50) {
			g.setColor(Color.white);
			Font font = new Font("黑体", 1, 28);
			g.setFont(font);
			g.drawString("第一关", 100, 250);
		} else if (50 < count && count < 200) {
			g.setColor(Color.white);
			g.drawString("START1", 100, 250);
		} else {
			GamePanel.gameMode = 1;
		}
		count++;
	}

}
