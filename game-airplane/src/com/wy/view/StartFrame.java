package com.wy.view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class StartFrame extends JPanel {
	private static final long serialVersionUID = 9158805858745581422L;

	public static final int WIDTH = 400;

	public static final int HEIGHT = 654;

	public static BufferedImage background;

	public static BufferedImage start;

	public static BufferedImage pause;

	public static BufferedImage gameover;

	public static BufferedImage airplane;

	public static BufferedImage bee;

	public static BufferedImage bullet;

	public static BufferedImage hero0;

	public static BufferedImage hero1;

	public static AudioClip music;

	public static final int START = 0;

	public static final int RUNNING = 1;

	public static final int PAUSE = 2;

	public static final int GAME_OVER = 3;

	private int state = 0;

	private Hero hero = new Hero();

	private Bullet[] bullets = {};

	private FlyingObject[] flyings = {};

	private Timer timer;

	private int intervel = 10;

	static {
		try {
			background = ImageIO.read(StartFrame.class.getResource("background.png"));
			start = ImageIO.read(StartFrame.class.getResource("start.png"));
			pause = ImageIO.read(StartFrame.class.getResource("pause.png"));
			gameover = ImageIO.read(StartFrame.class.getResource("gameover.png"));
			airplane = ImageIO.read(StartFrame.class.getResource("airplane.png"));
			bee = ImageIO.read(StartFrame.class.getResource("bee.png"));
			bullet = ImageIO.read(StartFrame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(StartFrame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(StartFrame.class.getResource("hero1.png"));

			URL musicPath = StartFrame.class.getResource("game_music.wav");
			music = Applet.newAudioClip(musicPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if (type == 0) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

	int flyEnteredIndex = 0;

	/**
	 * 敌人
	 */
	public void enterAction() {
		flyEnteredIndex++;
		if (flyEnteredIndex % 40 == 0) {
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}

	public void stepAction() {
		hero.step();
		int num = time1 / 15;
		for (int i = 0; i < flyings.length; i++) {
			for (int j = 0; j <= num; j++) {
				flyings[i].step();
			}
		}
		for (int i = 0; i < bullets.length; i++) {
			for (int j = 0; j <= num / 2; j++) {
				bullets[i].step();
			}
		}
	}

	int shootIndex = 0;

	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) {
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}

	/**
	 * 删除越界飞行物
	 */
	public void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (!f.outOfBounds()) {
				flyingLives[index] = f;
				index++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);

		index = 0;
		Bullet[] bulletsLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet bs = bullets[i];
			if (!bs.outOfBounds()) {
				bulletsLives[index] = bs;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);

	}

	int score = 0;

	static double time = 0.00;

	static int time1 = 0;

	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) {
			bang(bullets[i]);
		}
	}

	/**
	 * 一个子弹与所有敌人相撞
	 * 
	 * @param b
	 */
	public void bang(Bullet b) {
		int index = -1;
		for (int i = 0; i < flyings.length; i++) {
			if (flyings[i].shootBy(b)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			FlyingObject one = flyings[index];
			if (one instanceof Enemy) {
				Enemy e = (Enemy) one;
				score += e.getScore();
			}
			if (one instanceof Award) {
				Award a = (Award) one;
				int type = a.getType();
				switch (type) {
					case Award.DOUBLE_FIRE:
						hero.addDoubleFire();
						break;
					case Award.LIFE:
						hero.addLife();
						break;
				}
			}
			// 被撞敌人与flyings数组中的最后一个元素交换
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = t;
			// 缩容，删除随后一个元素---即被撞的对象
			flyings = Arrays.copyOf(flyings, flyings.length - 1);
		}
	}

	public void checkGameOverAction() {
		if (isGameOver()) {
			state = GAME_OVER;
			time1 = 0;
			time = 0;
		}
	}

	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) {
			if (hero.hit(flyings[i])) {
				hero.subtractLife();
				hero.setDoubleFire(0);

				// 相撞之后，交换缩容
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
			}
		}
		return hero.getLife() <= 0;
	}

	// 启动执行代码
	public void action() {

		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {
					// 运行状态下执行
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}

			public void mouseClicked(MouseEvent e) {
				switch (state) {
					case PAUSE:
						state = RUNNING;
						music.stop();
						music.loop();
						break;
					case RUNNING:
						state = PAUSE;
						music.stop();
						break;
					case START:
						state = RUNNING;
						music.stop();
						music.loop();
						break;
					case GAME_OVER:
						hero = new Hero();
						flyings = new FlyingObject[0];
						bullets = new Bullet[0];
						score = 0;
						state = START;
						music.stop();
						break;
				}
			}

			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}

			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		// 处理鼠标操作事件
		this.addMouseListener(l);
		// 处理鼠标移动事件
		this.addMouseMotionListener(l);

		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if (state == RUNNING) {
					enterAction();
					stepAction();
					shootAction();
					outOfBoundsAction();
					bangAction();
					time = time + 0.01;
					time1 = (int) time;
					checkGameOverAction();
				}
				repaint();
			}
		}, intervel, intervel);
	}

	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScore(g);
		paintState(g);
	}

	public void paintState(Graphics g) {
		switch (state) {
			case START:
				g.drawImage(start, 0, 0, null);
				break;
			case PAUSE:
				g.drawImage(pause, 0, 0, null);
				break;
			case GAME_OVER:
				g.drawImage(gameover, 0, 0, null);
				break;

		}
	}

	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}

	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}

	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}

	}

	public void paintScore(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("SCORE: " + score, 20, 25);
		g.drawString("LIFE: " + hero.getLife(), 20, 45);
		g.drawString("TIME:" + time1, 20, 65);
	}
}