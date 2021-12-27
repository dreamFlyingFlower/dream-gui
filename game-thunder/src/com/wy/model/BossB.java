package com.wy.model;

import com.wy.view.GamePanel;

public class BossB extends Boss {

	private boolean flag;

	public BossB(Battle battle) {
		super(battle, GamePanel.bossImageB);
		_battle = battle;
		x = 50F;
		y = -250F;
		power = 1500;
		powerMax = power;
		vx = 0.0F;
		vy = 2.0F;
		flag = true;
	}

	public void move() {
		if (counter < 150)
			y += vy;
		else if (counter >= 150) {
			if (counter == 150)
				vx = 1.0F;
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
				GamePanel.addList(new MoveHomingBullet(x + WIDTH / 2.0F, y + HEIGHT, 2.0F, 2.0F,
						_battle, GamePanel.HomingBulletImage));
				tamaIntCount = 50;
			}
		} else if (counter % 2000 < 1000) {
			if (tamaIntCount <= 0 && counter % 2000 > 600) {

				GamePanel.addList(new EnemyBeam(x + WIDTH / 2.0F + 80F, y + HEIGHT, _battle, this));
				if (counter % 30 == 15) {
					GamePanel.addList(
							new MoveAimingBullet(x + WIDTH / 2.0F + 50F, y + HEIGHT, _battle));
					GamePanel.addList(
							new MoveAimingBullet((x + WIDTH / 2.0F) - 50F, y + HEIGHT, _battle));
				}
				GamePanel.addList(
						new EnemyBeam((x + WIDTH / 2.0F) - 80F, y + HEIGHT, _battle, this));
				tamaIntCount = 2;
			}
		} else if (counter % 2000 < 1500) {
			if (tamaIntCount <= 0 && counter % 2000 > 1100) {
				if (flag)
					GamePanel.addList(new CircleBullets(x + WIDTH / 2.0F, y + HEIGHT, flag));
				else
					GamePanel.addList(new CircleBullets(x + WIDTH / 2.0F, y + HEIGHT, flag));
				flag = !flag;
				tamaIntCount = 50;
			}
		} else if (counter % 2000 < 2000 && tamaIntCount <= 0 && counter % 2000 > 1600) {
			GamePanel.addList(new RandomNBullet(x + WIDTH / 2.0F, y + HEIGHT, 0.0F, 2.0F, _battle));
			tamaIntCount = 25;
		}
		counter++;
	}

	public boolean checkHit(Role chara) {
		if ((chara instanceof BattleShot) && x - 40F > chara.x - WIDTH
				&& x + 40F < chara.x + chara.WIDTH && y - 50F > chara.y - HEIGHT
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

}
