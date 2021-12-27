package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.TooManyListenersException;

import com.wy.config.Props;
import com.wy.listener.ShapeListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 图形类
 * @author ParadiseWY
 * @date 2019年10月1日 下午5:51:49
 */
@Getter
@Setter
public class Shape {

	/**
	 * 变形,旋转
	 */
	public static final int ROTATE = 5;

	/**
	 * 上移
	 */
	public static final int UP = 1;

	/**
	 * 下移
	 */
	public static final int DOWN = 2;

	/**
	 * 左移
	 */
	public static final int LEFT = 3;

	/**
	 * 右移
	 */
	public static final int RIGHT = 4;

	/**
	 * 监听器组
	 */
	protected ShapeListener listener;

	protected int[][] body;

	/**
	 * 当前显示的状态
	 */
	protected int status;

	/**
	 * 图形的真实高度
	 */
	protected int height;

	/**
	 * 左上角的位置
	 */
	protected int left;

	/**
	 * 左上角的位置
	 */
	protected int top;

	/**
	 * 下落的速度
	 */
	protected int speed;

	/**
	 * 生命
	 */
	protected boolean life;

	/**
	 * 暂停状态
	 */
	protected boolean pause;

	protected boolean swift;

	protected int swiftSpeed = Props.SWIFT_SPEED;

	protected Thread shapeThread, swiftThread;

	/**
	 * 颜色
	 */
	protected Color color = Color.BLUE;

	/**
	 * 指定图形的类型,状态
	 * @param body 类型
	 * @param status 状态
	 */
	public Shape(int[][] body, int status) {
		super();
		this.body = body;
		this.status = status;
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++)
				if (isMember(x, y, false))
					height = y + 1;
		init();
	}

	/**
	 * 初始化位置,速度
	 */
	public void init() {
		life = true;
		pause = false;
		swift = false;
		left = Props.WIDTH / 2 - 2;
		top = 0 - height;
		speed = Props.CURRENT_SPEED;
	}

	/**
	 * 图形变化,旋转
	 */
	public void rotate() {
		status = (status + 1) % body.length;
	}

	public void moveUp() {
		top--;
	}

	/**
	 * 向下移动
	 */
	public void moveDown() {
		top++;
	}

	/**
	 * 向左移动
	 */
	public void moveLeft() {
		left--;
	}

	/**
	 * 向右移动
	 */
	public void moveRight() {
		left++;
	}

	/**
	 * 驱动图形定时下落的内部类
	 * @author ParadiseWY
	 * @date 2019年10月1日 下午5:56:11
	 */
	protected class ShapeDriver implements Runnable {

		/**
		 * 驱动图形定时下落
		 */
		public void run() {
			if (listener == null)
				throw new RuntimeException("请先注册ShapeListener");

			while (life && listener.isShapeMoveDownable(Shape.this)) {
				if (!swift) {
					if (!pause) {
						moveDown();
						// 触发下落事件
						listener.shapeMovedDown(Shape.this);
					}
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			life = false;
		}
	}

	/**
	 * 显示
	 */
	public void drawMe(Graphics g) {
		// 游戏结束就不画了,针对游戏结束时的最后一个图形
		if (!life)
			return;
		g.setColor(color);
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if (getFlagByPoint(status, x, y))
					drawUnit(g, (left + x) * Props.CELL_WIDTH, (top + y) * Props.CELL_HEIGHT, Props.CELL_WIDTH,
							Props.CELL_HEIGHT);
	}

	/**
	 * 产生图形
	 */
	public void drawUnit(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 相对坐标(x,y)是否是图形中的带你
	 */
	protected boolean getFlagByPoint(int status, int x, int y) {
		return body[status][y * 4 + x] == 1;
	}

	/**
	 * 指定的位置是否是图形的一部分
	 * @param x 图形的x坐标
	 * @param y 图形的y坐标
	 * @param isRotate 是否旋转了
	 * @return
	 */
	public boolean isMember(int x, int y, boolean isRotate) {
		return getFlagByPoint(isRotate ? (status + 1) % body.length : status, x, y);
	}

	/**
	 * 加速
	 */
	public void speedUp() {
		if (speed > Props.SPEED_STEP)
			speed -= Props.SPEED_STEP;
		Props.CURRENT_SPEED = speed;
	}

	/**
	 * 减速
	 */
	public void speedDown() {
		speed += Props.SPEED_STEP;
		Props.CURRENT_SPEED = speed;
	}

	/**
	 * 更改暂停状态,若是暂停状态,则继续下落;若正在下落,则暂停
	 */
	public void changePause() {
		this.pause = !this.pause;
	}

	/**
	 * 添加监听器,将会启动驱动图形下落的线程
	 * @param l
	 */
	public void addShapeListener(ShapeListener l) {
		if (l == null || this.listener == l)
			return;
		if (this.listener != null)
			throw new RuntimeException(new TooManyListenersException());
		this.listener = l;

		start();
	}

	protected void start() {
		shapeThread = new Thread(new ShapeDriver());
		shapeThread.start();
	}

	/**
	 * 结束图形定时下落的线程
	 */
	public synchronized void die() {
		this.life = false;
	}

	/**
	 * 直接下落到最底
	 * @param swift
	 */
	public void setSwift(boolean swift) {
		if (this.swift == swift)
			return;
		this.swift = swift;
		if (this.swift) {
			swiftThread = new Thread(new ShapeSwiftDriver());
			swiftThread.start();
		}
	}

	protected class ShapeSwiftDriver implements Runnable {

		public void run() {
			while (swift && life) {
				if (listener == null)
					throw new RuntimeException("请先注册 ShapeListener");
				if (listener.isShapeMoveDownable(Shape.this)) {
					if (!pause) {
						moveDown();
						// 触发下落事件
						listener.shapeMovedDown(Shape.this);
					}
					try {
						Thread.sleep(swiftSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					life = false;
				}
			}
		}
	}
}