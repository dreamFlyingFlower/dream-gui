package com.wy.model;

import java.awt.Graphics;

import com.wy.view.GamePanel;

public class Burst extends Thread {
	private float x;

	private float y;

	private int count;

	private int MAX = 0;

	public Burst(float x, float y) {
		this.x = x;
		this.y = y;
		count = 0;
		MAX = GamePanel.burstImage.length;
		start();
	}

	public void run() {
		do {
			count++;
			if (count == MAX)
				return;
			try {
				Thread.sleep(20L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	public void draw(Graphics g) {
		if (count < MAX) {
			g.drawImage(GamePanel.burstImage[count], (int) x, (int) y, null);
		}
	}

}
