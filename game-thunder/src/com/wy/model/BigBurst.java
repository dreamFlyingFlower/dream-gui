package com.wy.model;

import java.awt.Graphics;

import com.wy.view.GamePanel;

public class BigBurst extends Thread {
	private float x;

	private float y;

	private int count;

	public BigBurst(float x, float y) {
		this.x = x;
		this.y = y;
		count = 0;
		start();
	}

	public void run() {
		do {
			count++;
			if (count == 16)
				return;
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	public void draw(Graphics g) {
		if (count < 16)
			g.drawImage(GamePanel.largeBurstImage[count], (int) x, (int) y, null);
	}
}