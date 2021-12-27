package com.wy.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import com.wy.view.GamePanel;

public class Battle extends Role {
	private static Battle battle = new Battle();

	private int tamaIntCount;

	private float speed;

	private float oldx;

	private float oldy;

	public static float vx;

	public static float vy;

	public static boolean keygo = false;

	private float tv[] = { -1F, -7F, 0.0F, -8F, 1.0F, -7F };

	public int power;

	public int powerMax;

	public Battle() {
		super(GamePanel.heroImage);
		speed = 3F;
		tamaIntCount = 0;
		setX(((float) app.getWidth() - getWIDTH()) / 2.0F);
		setY((float) app.getHeight() - getHEIGHT() * 2.0F);
		power = 600;
		powerMax = power;
	}

	public static Battle getInstance() {
		return battle;
	}

	public void move() {
		oldx = getX();
		oldy = getY();
		if (Key.left) {
			if (Key.xkey)
				x -= (double) speed / 4D;
			else
				x -= speed;
			if (x <= 0.0F)
				x = 0.0F;
		}
		if (Key.right) {
			if (Key.xkey)
				x += (double) speed / 4D;
			else
				x += speed;
			if (x + WIDTH >= (float) app.getWidth())
				x = (float) app.getWidth() - WIDTH;
		}
		if (Key.down) {
			if (Key.xkey)
				y += (double) speed / 4D;
			else
				y += speed;
			if (y + HEIGHT >= (float) app.getHeight())
				y = (float) app.getHeight() - HEIGHT;
		}
		if (Key.up) {
			if (Key.xkey)
				y -= (double) speed / 4D;
			else
				y -= speed;
			if (y <= 0.0F)
				y = 0.0F;
		}
		vx = x - oldx;
		vy = y - oldy;
		if (tamaIntCount > 0)
			tamaIntCount--;
		if (Key.zkey && tamaIntCount <= 0) {
			for (int i = 0; i < tv.length; i += 2) {
				GamePanel.addList(new BattleBasic(x + WIDTH / 2.0F, y, tv[i], tv[i + 1]));
				tamaIntCount = 8;
			}
		}
		if (Key.xkey && !Key.zkey && tamaIntCount <= 0) {
			GamePanel.addList(new BattleBeam(x + WIDTH / 2.0F, y, 0.0F, -8F));
			tamaIntCount = 2;
		}
		if (Key.space) {
			if (!keygo) {
				GamePanel.skillCount--;
			}
			app.skillAnime();

			if (GamePanel.skillCount < 0) {
				GamePanel.skillCount = 0;
			}
			if (GamePanel.skillCount > 0) {
				for (int i = 0; i < GamePanel.list.size(); i++) {
					Role chara1 = (Role) GamePanel.list.get(i);
					if (!(chara1 instanceof Battle) && chara1.x > 0 && chara1.y > 0
							&& !(chara1 instanceof BossA) && !(chara1 instanceof BossB)
							&& !(chara1 instanceof BossC)) {
						GamePanel.list.remove(i);
					} else if ((chara1 instanceof BossA) || (chara1 instanceof BossB)
							|| (chara1 instanceof BossC)) {
						Boss cb = (Boss) chara1;
						cb.power -= 50;
					}
				}
			}
			keygo = true;
		}

		if (!Key.space) {
			keygo = false;
		}
	}

	public boolean checkHit(Role chara) {
		if ((chara instanceof EnemyA) || (chara instanceof EnemyB) || (chara instanceof EnemyC)
				|| (chara instanceof EnemyShot)) {

			if ((x + WIDTH) - 14F > chara.x && x + 14F < chara.x + chara.WIDTH
					&& (y + HEIGHT) - 12F > chara.y && y + 12F < chara.y + chara.HEIGHT) {
				// 如果碰到敌人,敌人死亡
				chara.dead();
				// 如果碰到子弹血量减少
				if (chara instanceof EnemyBeam) {
					power--;
				}

				power -= 50;
				if (power <= 0) {
					dead();
					// 绘制爆炸图片
					GamePanel.burst = new Burst(x, y);
				}
				return true;
			}
		} else if ((chara instanceof Boss) && (x + WIDTH) - 14F > chara.x + 50F
				&& x + 14F < (chara.x + chara.WIDTH) - 50F && (y + HEIGHT) - 12F > chara.y + 50F
				&& y + 12F < (chara.y + chara.HEIGHT) - 80F) {
			power--;
			if (power <= 0) {
				dead();
				GamePanel.burst = new Burst(x, y);
			}
			return true;
		}
		return false;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return WIDTH;
	}

	public float getHeight() {
		return HEIGHT;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Image getImage() {
		return img;
	}

	public int getPower() {
		return power;
	}

	public int getPowerMax() {
		return powerMax;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void drawPower(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(380, 450, 50, 15);
		g.setColor(Color.red);
		g.fillRect(381, 451, (int) ((50D / (double) (float) powerMax) * (double) power) - 1, 14);
	}

	public void drawSkillCount(Graphics g) {
		g.setColor(Color.white);
		Font font = new Font("宋体", 1, 20);
		g.setFont(font);
		g.drawString("全屏爆破:" + GamePanel.skillCount, 0, 450);
	}
}