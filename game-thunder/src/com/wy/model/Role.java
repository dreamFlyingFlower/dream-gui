package com.wy.model;

import java.awt.Graphics;
import java.awt.Image;

import com.wy.view.GamePanel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Role {
	public static GamePanel app;

	public Image img;

	public float x;

	public float y;

	public float WIDTH;

	public float HEIGHT;

	public boolean dead;

	protected Role(Image img) {
		this.img = img;
		WIDTH = img.getWidth(app);
		HEIGHT = img.getHeight(app);
		dead = false;
	}

	public void dead() {
		dead = true;
	}

	public abstract void move();

	public boolean checkHit(Role chara) {
		return x > chara.x - WIDTH && x < chara.x + chara.WIDTH && y > chara.y - HEIGHT
				&& y < chara.y + chara.HEIGHT;
	}

	public void draw(Graphics g) {
		g.drawImage(img, (int) x, (int) y, app);
	}

	public void drawBurst(Graphics g) {
		g.drawImage(GamePanel.burstImage[0], (int) x, (int) y, app);
	}
}