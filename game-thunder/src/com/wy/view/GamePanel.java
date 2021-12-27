package com.wy.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

import com.wy.model.Battle;
import com.wy.model.BigBurst;
import com.wy.model.Boss;
import com.wy.model.Burst;
import com.wy.model.Enemy;
import com.wy.model.Key;
import com.wy.model.Role;
import com.wy.model.StageA;
import com.wy.model.StageB;
import com.wy.model.StageC;

public class GamePanel extends JPanel implements Runnable {

	static final int WIDTH = 450;

	static final int HEIGHT = 500;

	private Thread gameThread;

	public static int gameMode;

	private Image offImage;

	private Graphics g_off;

	public static Image heroImage;

	public static Image enemyImageA;

	public static Image enemyImageB;

	public static Image enemyImageC;

	public static Image enemyImageD;

	public static Image bossImageA;

	public static Image bossImageB;

	public static Image bossImageC;

	public static Image flagImage;

	public static Image zikiBeamImage;

	public static Image enemyBeamImage;

	public static Image enemyTamaImage;

	public static Image burstImage[] = new Image[16];

	public static Image largeBurstImage[] = new Image[16];

	public static Image splitBulletImage;

	public static Image HomingBulletImage;

	public static LinkedList<Role> list;

	public static LinkedList<Role> listTmp;

	public static final int STARTSCENE = 0;

	public static final int STAGE1 = 1;

	public static final int Stage1Clear = 4;

	public static final int InitSTAGE2 = 8;

	public static final int STAGE2 = 2;

	public static final int Stage2Start = 5;

	public static final int Stage2Clear = 6;

	public static final int InitSTAGE3 = 7;

	public static final int STAGE3 = 3;

	public static final int Stage3Clear = 9;

	public static final int Congratulation = 10;

	public static final int GAMEOVERSCENE = 11;

	public static final int READY = 12;

	public static final int APPEARING = 13;

	public static final int CREAR = 14;

	public static final int DISAPPEARING = 15;

	public static final int BOSS_DEATH = 16;

	public static boolean AppearingFlag = true;

	public static boolean DeathSeenAFlag = true;

	public static boolean DeathSeenBFlag = true;

	public static boolean DeathSeenCFlag = true;

	public static Burst burst;

	public static BigBurst largeBurst;

	public int t_loon_framework;

	Battle battle;

	Boss boss;

	public static int time = 0;

	public static int skillCount = 10;

	public StageA stageA;

	public StageB stageB;

	public StageC stageC;

	int current;

	Start star[];

	Random random;

	private int stage;

	private float heroX;

	private float zikiY;

	private float bossX;

	private float bossY;

	private int bossDeathAnimeCount;

	public int skillAnimeCount;

	private static final long serialVersionUID = 1L;

	public GamePanel() {

		offImage = null;
		current = 0;
		// 新建Start类型数组大小为20
		star = new Start[Start.num_Star];
		// 调整窗口尺寸
		setPreferredSize(new Dimension(450, 500));
		Role.app = this;
		list = new LinkedList<>();
		listTmp = new LinkedList<>();
		heroImage = loadImage("image/this.gif");
		enemyImageA = loadImage("image/enemyA.gif");
		enemyImageB = loadImage("image/enemyB.gif");
		enemyImageC = loadImage("image/enemyC.gif");
		enemyImageD = loadImage("image/enemyD.gif");
		bossImageA = loadImage("image/bossA.gif");
		bossImageB = loadImage("image/bossB.gif");
		bossImageC = loadImage("image/bossC.gif");
		flagImage = loadImage("image/ballSilver.gif");
		zikiBeamImage = loadImage("image/beam2.gif");
		enemyBeamImage = loadImage("image/beam3.gif");
		enemyTamaImage = loadImage("image/ballRed.gif");
		// 爆炸图片数组
		for (int i = 0; i < 16; i++)
			burstImage[i] = loadImage("image/burst" + i + ".gif");
		// 蓝色圆形子弹
		splitBulletImage = loadImage("image/ballBlue.gif");
		// 大型爆炸图片数组
		for (int i = 0; i < 16; i++)
			largeBurstImage[i] = loadImage("image/largeBurst" + i + ".gif");
		// 绿色圆形子弹
		HomingBulletImage = loadImage("image/ballGreen.gif");

		gameMode = 0;
		stage = 1;
		random = new Random();
		for (int i = 0; i < Start.num_Star; i++) {
			// Start(int x, int y, int vy, int width, Color color)
			star[i] = new Start(Math.abs(random.nextInt()) * 450, Math.abs(random.nextInt()) * 500,
					5 * Math.abs(random.nextInt()), 5 * Math.abs(random.nextInt()), Color.white);
		}
		addKeyListener(new Key());
		setFocusable(true);
		requestFocus();
		setBackground(Color.black);
		setForeground(Color.white);
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {
		while (gameThread == Thread.currentThread()) {
			// 画黑色的背景
			gameRender();
			if (g_off != null) {
				long RefreshTime = System.currentTimeMillis();
				try {
					Thread.sleep(2L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				switch (gameMode) {
					case 0:
						title();
						break;

					case 1:
						stage1();
						break;

					case 12:
						ready();
						break;

					case 14:
						crear();
						break;

					case 13:
						appearingAnime();
						break;

					case 2:
						stage2();
						break;
					case 3:
						stage3();
						break;

					case 10:
						congratulation();
						break;

					case 11:
						gameOver();
						break;

					case 15:
						disappearing();
						break;

					case 16:
						bossDeathAnime();
						break;
				}
				paintScreen();
				current++;
				while (System.currentTimeMillis() - RefreshTime < 13L)
					;
			}
		}
	}

	private void disappearing() {
		g_off.setColor(Color.white);
		Font font = new Font("宋体", 1, 28);
		g_off.setFont(font);
		g_off.drawString("关卡 " + stage + " 完结", 100, 250);
		battle.setPower(battle.getPowerMax());
		if (zikiY + battle.getHeight() > 0.0F) {
			g_off.drawImage(battle.getImage(), (int) heroX, (int) zikiY, null);
		} else {
			gameMode = 12;
			stage++;
		}
		if (stage == 4) {
			gameMode = 10;
		}
		zikiY -= 2.0F;
	}

	private void crear() {
		if (stage == 1) {
			gameMode = 16;
		} else if (stage == 2) {
			gameMode = 16;
		} else if (stage == 3) {
			gameMode = 16;
		}
	}

	private void bossDeathAnime() {
		if (bossY + boss.getHeight() > 0.0F) {
			g_off.drawImage(boss.getImage(), (int) bossX, (int) bossY, null);
			if (bossDeathAnimeCount % 5 == 0) {
				burst = new Burst(
						(float) ((double) bossX + (double) boss.getWidth() * Math.random()),
						(float) ((double) bossY + (double) boss.getHeight() * Math.random()));
				burst.draw(g_off);
			}
			g_off.drawImage(battle.getImage(), (int) heroX, (int) zikiY, null);
			bossDeathAnimeCount++;
			bossY--;
		} else {
			gameMode = 15;
		}
	}

	public void skillAnime() {
		if (skillAnimeCount % 5 == 0) {
			burst = new Burst((float) (400 * Math.random()), (float) (300 * Math.random()));
			burst.draw(g_off);
		}
		skillAnimeCount++;
	}

	private void stage1() {
		StageA.start();
		gameMain();
	}

	private void stage2() {
		StageB.start();
		gameMain();
	}

	private void stage3() {
		StageC.start();
		gameMain();
	}

	private void congratulation() {
		g_off.setColor(Color.white);
		g_off.drawString("恭喜", 100, 250);
	}

	// 关卡开始的准备画面
	private void ready() {
		bossDeathAnimeCount = 0;
		skillAnimeCount = 0;
		time = 0;
		list.clear();
		listTmp.clear();
		battle = new Battle();
		heroX = battle.getX();
		zikiY = battle.getY() + battle.getHeight() * 4F;
		switch (stage) {
			case 1:
				stageA = new StageA(battle);
				break;

			case 2:
				stageB = new StageB(battle);
				break;

			case 3:
				stageC = new StageC(battle);
				break;
		}
		addList(battle);
		gameMode = 13;
	}

	private void appearingAnime() {
		g_off.setColor(Color.white);
		g_off.drawString("STAGE" + stage, 160, 250);
		if (zikiY < 500F - battle.getHeight() * 2.0F) {
			gameMode = stage;
		} else {
			g_off.drawImage(battle.getImage(), (int) heroX, (int) zikiY, null);
		}
		zikiY -= 2.0F;
	}

	// 显示第一个画面,按回车后进入关卡开始准备界面
	private void title() {
		if (Key.enter) {
			gameMode = 12;
		} else {
			g_off.setColor(Color.white);
			Font font = new Font("华文新魏", 1, 45);
			g_off.setFont(font);
			FontMetrics fontMetrics = getFontMetrics(font);
			g_off.drawString("飞机大战", (450 - fontMetrics.stringWidth("飞机大战")) / 2,
					(500 + fontMetrics.getHeight()) / 2 - 50);
			if (15 <= current % 50)
				g_off.drawString("开始请按 ENTER", (450 - fontMetrics.stringWidth("开始请按 ENTER")) / 2,
						(500 + fontMetrics.getHeight()) / 2 + 100);
		}
	}

	private void gameOver() {
		GamePanel.skillCount = 10;
		if (Key.enter) {
			gameMode = 12;
			stage = 1;
		} else {
			g_off.setColor(Color.white);
			Font font = new Font("黑体", 1, 28);
			g_off.setFont(font);
			FontMetrics fontMetrics = getFontMetrics(font);
			g_off.drawString("Game Over", (450 - fontMetrics.stringWidth("Game Over")) / 2,
					(500 + fontMetrics.getHeight()) / 2 - 50);
			if (15 <= current % 50)
				g_off.drawString("请按 ENTER", (450 - fontMetrics.stringWidth("请按 ENTER")) / 2,
						(500 + fontMetrics.getHeight()) / 2 + 100);
		}
	}

	// 画黑色的背景
	private void gameRender() {
		if (offImage == null) {
			offImage = createImage(450, 500);
			if (offImage == null) {
				return;
			}
			g_off = offImage.getGraphics();
		}
		g_off.setColor(Color.BLACK);
		g_off.fillRect(0, 0, 450, 500);
	}

	public void paintScreen() {
		try {
			Graphics g = getGraphics();
			if (g != null && offImage != null)
				g.drawImage(offImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			if (g != null)
				g.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 整个画面帧֡
	private void gameMain() {
		for (int i = 0; i < Start.num_Star; i++) {
			// 如果敌人死了随机产生一个
			if (star[i].death())
				star[i] = new Start((int) (450D * Math.random()), (int) (500D * Math.random()),
						(int) (4D * Math.random()) + 2, (int) (5D * Math.random()) + 1,
						Color.white);
		}
		for (int i = 0; i < Start.num_Star; i++) {
			star[i].move();
		}

		for (int i = 0; i < Start.num_Star; i++) {
			star[i].draw(g_off);
		}

		// 画主角的血量
		battle.drawPower(g_off);
		battle.drawSkillCount(g_off);
		for (int i = 0; i < list.size(); i++) {
			Role chara1 = (Role) list.get(i);
			for (int j = 0; j < list.size(); j++) {
				Role chara2 = (Role) list.get(j);
				chara1.checkHit(chara2);
			}

		}

		for (int i = 0; i < list.size(); i++) {
			Role chara1 = (Role) list.get(i);
			if (chara1 instanceof Boss) {
				boss = (Boss) chara1;
				if (!boss.isDead()) {
					boss.drawPower(g_off);
				} else {
					bossX = boss.getX();
					bossY = boss.getY();
					heroX = battle.getX();
					zikiY = battle.getY();
					gameMode = 14;
				}
			}
		}

		for (int i = list.size() - 1; i >= 0; i--) {
			Role chara1 = (Role) list.get(i);
			chara1.move();
			chara1.draw(g_off);
		}

		if (burst != null)
			burst.draw(g_off);
		for (int i = 0; i < list.size(); i++) {
			Role chara1 = (Role) list.get(i);
			if (chara1.isDead()) {
				if (chara1 instanceof Enemy)
					chara1.drawBurst(g_off);
				list.remove(i);
			}
		}

		for (int i = 0; i < listTmp.size(); i++) {
			list.add(listTmp.get(i));
		}

		if (battle.isDead()) {
			gameMode = 11;
		}
		listTmp.clear();
		time++;
	}

	public static void addList(Role chara) {
		listTmp.add(chara);
	}

	public int getWidth() {
		return 450;
	}

	public int getHeight() {
		return 500;
	}

	public Image loadImage(String str) {
		InputStream is = null;
		try {
			is = GamePanel.class.getResourceAsStream(str);
		} catch (Exception e) {
			is = null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayByte = null;
		try {
			byte[] bytes = new byte[4096];
			bytes = new byte[is.available()];
			int read;
			while ((read = is.read(bytes)) >= 0) {
				byteArrayOutputStream.write(bytes, 0, read);
			}
			arrayByte = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			System.err.println("Image Loader IQ Exception " + e.getMessage());
			return null;
		} finally {
			try {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}

			} catch (IOException e) {
				System.err.println("Image Close IQ Exception " + e.getMessage());
			}

		}
		Image result = Toolkit.getDefaultToolkit().createImage(arrayByte);
		if (result != null)
			waitImage(result);
		else
			System.out.println("File not found. ( " + str + " )");
		return result;
	}

	/**
	 * 同步方法,使产生图像时间一致
	 * 
	 * @param image
	 */
	private final static void waitImage(Image image) {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			for (int i = 0; i < 100; i++) {
				if (toolkit.prepareImage(image, -1, -1, null))
					return;
				Thread.currentThread();
				Thread.sleep(100L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}