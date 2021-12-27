package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.wy.config.Props;

import lombok.Getter;
import lombok.Setter;

/**
 * 食物
 * @author ParadiseWY
 * @date 2019年10月2日 下午5:14:06
 */
@Getter
@Setter
public class Food extends Point {

	private static final long serialVersionUID = 1L;

	/**
	 * 食物的默认颜色
	 */
	private Color color = new Color(0xcc0033);

	private Random random = new Random();

	public Food() {
		super();
	}

	public Food(Point p) {
		super(p);
	}

	public Point getNew() {
		Point p = new Point();
		p.x = random.nextInt(Props.WIDTH);
		p.y = random.nextInt(Props.HEIGHT);
		return p;
	}

	/**
	 * 蛇是否吃到了食物
	 * @param snake
	 * @return
	 */
	public boolean isSnakeEatFood(Snake snake) {
		return this.equals(snake.getHead());
	}

	/**
	 * 画一个食物
	 * @param g
	 */
	public void drawMe(Graphics g) {
		g.setColor(color);
		drawFood(g, x * Props.CELL_WIDTH, y * Props.CELL_HEIGHT, Props.CELL_WIDTH, Props.CELL_HEIGHT);
	}

	/**
	 * 画食物
	 */
	public void drawFood(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}
}