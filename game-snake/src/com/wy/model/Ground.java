package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.wy.config.Props;

import lombok.Getter;
import lombok.Setter;

/**
 * 边界围墙,该类提供了2种石头的布局,默认是第一种,即一圈石头,若想使用第2种,可调用generateRocks2
 * @author ParadiseWY
 * @date 2019年10月2日 下午6:11:10
 */
@Getter
@Setter
public class Ground {

	/**
	 * 存放石头的二位数组
	 */
	private boolean rocks[][] = new boolean[Props.WIDTH][Props.HEIGHT];

	/**
	 * 存放不是石头的随机坐标
	 */
	private Point freePoint = new Point();

	public static final Color DEFAULT_ROCK_COLOR = new Color(0x666666);

	private Color rockColor = DEFAULT_ROCK_COLOR;

	public static final Color DEFAULT_GRIDDING_COLOR = Color.LIGHT_GRAY;

	/**
	 * 网格的颜色
	 */
	private Color griddingColor = DEFAULT_GRIDDING_COLOR;

	private Random random = new Random();

	private boolean drawGridding = false;

	public Ground() {
		init();
	}

	public void init() {
		clear();
		generateRocks();
	}

	/**
	 * 初始化ground,清空石头
	 */
	public void clear() {
		for (int x = 0; x < Props.WIDTH; x++)
			for (int y = 0; y < Props.HEIGHT; y++)
				rocks[x][y] = false;
	}

	/**
	 * 默认生成石头的方法
	 */
	public void generateRocks() {
		for (int x = 0; x < Props.WIDTH; x++)
			rocks[x][0] = rocks[x][Props.HEIGHT - 1] = true;
		for (int y = 0; y < Props.HEIGHT; y++)
			rocks[0][y] = rocks[Props.WIDTH - 1][y] = true;
	}

	/**
	 * 第2种生成石头的方法,该方法中的蛇撞墙不会死
	 */
	public void generateRocks2() {

		for (int y = 0; y < 6; y++) {
			rocks[0][y] = true;
			rocks[Props.WIDTH - 1][y] = true;
			rocks[0][Props.HEIGHT - 1 - y] = true;
			rocks[Props.WIDTH - 1][Props.HEIGHT - 1 - y] = true;
		}
		for (int y = 6; y < Props.HEIGHT - 6; y++) {
			rocks[6][y] = true;
			rocks[Props.WIDTH - 7][y] = true;
		}
	}

	/**
	 * 添加一块石头到指定格子坐标
	 */
	public void addRock(int x, int y) {
		rocks[x][y] = true;
	}

	/**
	 * 蛇是否吃到了石头
	 * @param snake
	 * @return
	 */
	public boolean isSnakeEatRock(Snake snake) {
		return rocks[snake.getHead().x][snake.getHead().y];
	}

	/**
	 * 随机生成一个不是石头的坐标,用来丢食物
	 * @return
	 */
	public Point getFreePoint() {
		do {
			freePoint.x = random.nextInt(Props.WIDTH);
			freePoint.y = random.nextInt(Props.HEIGHT);
		} while (rocks[freePoint.x][freePoint.y]);
		return freePoint;
	}

	/**
	 * 画边界
	 */
	public void drawMe(Graphics g) {
		for (int x = 0; x < Props.WIDTH; x++)
			for (int y = 0; y < Props.HEIGHT; y++) {
				if (rocks[x][y]) {
					g.setColor(rockColor);
					drawRock(g, x * Props.CELL_WIDTH, y * Props.CELL_HEIGHT, Props.CELL_WIDTH, Props.CELL_HEIGHT);
				} else if (drawGridding) {
					g.setColor(griddingColor);
					drawGridding(g, x * Props.CELL_WIDTH, y * Props.CELL_HEIGHT, Props.CELL_WIDTH, Props.CELL_HEIGHT);
				}
			}
	}

	/**
	 * 画石头
	 */
	public void drawRock(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 画网格
	 */
	public void drawGridding(Graphics g, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}
}