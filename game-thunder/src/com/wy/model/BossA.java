package com.wy.model;

import java.awt.Image;

import com.wy.view.GamePanel;

public class BossA extends Boss {

	public static float vx;

	public static float vy;

	public BossA(Battle battle) {
		super(battle, GamePanel.bossImageA);
		this._battle = battle;
		x = 50F;
		y = -250F;
		power = 1000;
		powerMax = power;
		vx = 1.0F;
		vy = 0.0F;
	}

	public void move() {
		if (counter < 150)
			y += 2D;
		else if (counter >= 150) {
			x += vx;
			if (x + WIDTH > (float) app.getWidth())
				vx = -vx;
			if (x < 0.0F)
				vx = -vx;
		}
		checkOutOfScreen();
		if (tamaIntCount > 0)
			tamaIntCount--;
		if (counter % 2000 < 500) {
			if (tamaIntCount <= 0 && counter % 2000 > 100) {
				GamePanel.addList(
						new MoveAimingBullet((x + WIDTH / 2.0F) - 50F, y + HEIGHT, _battle));
				GamePanel
						.addList(new MoveAimingBullet(x + WIDTH / 2.0F + 50F, y + HEIGHT, _battle));
				GamePanel.addList(new MoveAimingBullet(x + WIDTH / 2.0F, y + HEIGHT, _battle));
				tamaIntCount = 30;
			}
		} else if (counter % 2000 < 1000) {
			if (tamaIntCount <= 0 && counter % 2000 > 600) {
				GamePanel.addList(new MoveHomingBullet(x + WIDTH / 2.0F, y + HEIGHT, 2.0F, 2.0F,
						_battle, GamePanel.HomingBulletImage));
				GamePanel.addList(new MoveAimingBullet(x + WIDTH / 2.0F, y + HEIGHT, _battle));
				tamaIntCount = 60;
			}
		} else if (counter % 2000 < 1500) {
			if (tamaIntCount <= 0 && counter % 2000 > 1100) {
				GamePanel.addList(new NBullets(x + WIDTH / 2.0F, y + HEIGHT, 0.0F, 2.0F, _battle));
				GamePanel.addList(new MoveAimingBullet(x + WIDTH / 2.0F, y + HEIGHT, _battle));
				tamaIntCount = 80;
			}
		} else if (counter % 2000 < 2000 && tamaIntCount <= 0 && counter % 2000 > 1600) {
			GamePanel.addList(new CircleBullets(x + WIDTH / 2.0F, y + HEIGHT, true));
			GamePanel.addList(new MoveAimingBullet(x + WIDTH / 2.0F, y + HEIGHT, _battle));
			tamaIntCount = 50;
		}
		counter++;
	}

	public boolean checkHit(Role chara) {
		if ((chara instanceof BattleShot) && x - 20F > chara.x - WIDTH
				&& x + 20F < chara.x + chara.WIDTH && y - 50F > chara.y - HEIGHT
				&& y + 50F < chara.y + chara.HEIGHT) {
			chara.dead();
			power--;
			if (power <= 0) {
				dead();
				GamePanel.largeBurst = new BigBurst(x, y);
			}
			return true;
		} else {
			return false;
		}
	}

	public Image getImage() {
		return img;
	}
}