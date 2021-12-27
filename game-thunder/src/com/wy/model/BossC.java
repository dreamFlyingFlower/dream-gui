package com.wy.model;

import com.wy.view.GamePanel;

public class BossC extends Boss {

	public BossC(Battle battle) {
		super(battle, GamePanel.bossImageC);
		this._battle = battle;
		x = 50F;
		y = -250F;
		power = 2000;
		powerMax = power;
		vx = 0.0F;
		vy = 2.0F;
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
			if (tamaIntCount <= 0) {
				if (counter % 2000 > 100) {
					for (int i = 0; i < 100; i++)
						if (counter % 2 == 0)
							GamePanel.addList(new DirectionalBullet(x + WIDTH / 2.0F, y + HEIGHT,
									10 + i * 10, _battle));
						else
							GamePanel.addList(new DirectionalBullet(x + WIDTH / 2.0F, y + HEIGHT,
									80 + i * 10, _battle));

					GamePanel.addList(
							new MoveAimingBullet(x + WIDTH / 2.0F + 50F, y + HEIGHT, _battle));
					GamePanel.addList(
							new MoveAimingBullet((x + WIDTH / 2.0F) - 50F, y + HEIGHT, _battle));
				}
				tamaIntCount = 90;
			}
		} else if (counter % 2000 < 1000) {
			if (tamaIntCount <= 0 && counter % 2000 > 600) {
				GamePanel
						.addList(new EnemyBeam(x + WIDTH / 2.0F + 100F, y + HEIGHT, _battle, this));
				if (counter % 25 == 0)
					GamePanel.addList(
							new RandomNBullet(x + WIDTH / 2.0F, y + HEIGHT, 0.0F, 2.0F, _battle));
				GamePanel.addList(
						new EnemyBeam((x + WIDTH / 2.0F) - 100F, y + HEIGHT, _battle, this));
				tamaIntCount = 2;
			}
		} else if (counter % 2000 < 1500) {
			if (tamaIntCount <= 0 && counter % 2000 > 1100) {
				GamePanel
						.addList(new MoveAimingBullet(x + WIDTH / 2.0F + 50F, y + HEIGHT, _battle));
				GamePanel.addList(
						new MoveAimingBullet((x + WIDTH / 2.0F) - 50F, y + HEIGHT, _battle));
				GamePanel.addList(new MoveHomingBullet(x + WIDTH / 2.0F, y + HEIGHT, 2.0F, 2.0F,
						_battle, GamePanel.HomingBulletImage));
				tamaIntCount = 80;
			}
		} else if (counter % 2000 < 2000 && tamaIntCount <= 0 && counter % 2000 > 1600) {
			GamePanel.addList(new RandomNBullet(x + WIDTH / 2.0F, y + HEIGHT, 0.0F, 2.0F, _battle));
			GamePanel.addList(new MoveAimingBullet((x + WIDTH / 2.0F) - 50F, y + HEIGHT, _battle));
			GamePanel.addList(new MoveAimingBullet(x + WIDTH / 2.0F + 50F, y + HEIGHT, _battle));
			tamaIntCount = 25;
		}
		counter++;
	}

	public boolean checkHit(Role chara) {
		if ((chara instanceof BattleShot) && x + 50F > chara.x - WIDTH
				&& x - 50F < chara.x + chara.WIDTH && y - 50F > chara.y - HEIGHT
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
