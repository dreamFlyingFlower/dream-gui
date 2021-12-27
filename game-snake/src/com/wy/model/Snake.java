package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.wy.config.Props;
import com.wy.listener.SnakeListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 蛇,move方法默认支持撞墙不死.内部类MoveDriver驱动蛇定时移动
 * @author ParadiseWY
 * @date 2019年10月2日 下午6:20:53
 */
@Getter
@Setter
public class Snake {

	public static final int UP = 1;

	public static final int DOWN = -1;

	public static final int LEFT = 2;
	public static final int RIGHT = -2;

	/**
	 * 蛇的节点
	 */
	private LinkedList<Point> body = new LinkedList<Point>();

	/**
	 * 上一次的移动方向
	 */
	private int oldDirection;

	/**
	 * 下一次的移动方法
	 */
	private int newDirection;

	/**
	 * 蛇头的临时坐标
	 */
	private Point head;

	/**
	 * 蛇尾的临时坐标
	 */
	private Point tail;

	/**
	 * 移动速度
	 */
	private int speed;

	/**
	 * 生命
	 */
	private boolean live;

	/**
	 * 是否暂停状态
	 */
	private boolean pause;

	private Set<SnakeListener> listeners = new HashSet<SnakeListener>();

	public static final Color DEFAULT_HEAD_COLOR = new Color(0xcc0033);
	private Color headColor = DEFAULT_HEAD_COLOR;
	public static final Color DEFAULT_BODY_COLOR = new Color(0xcc0033);
	private Color bodyColor = DEFAULT_BODY_COLOR;

	/**
	 * 移动一步,会忽略相反方向
	 */
	public void move() {
		// 忽略相反方向
		if (oldDirection + newDirection != 0)
			oldDirection = newDirection;
		// 将蛇尾作为新的蛇头
		tail = (head = takeTail()).getLocation();
		head.setLocation(getHead());
		switch (oldDirection) {
		case UP:
			head.y--;
			// 撞到上面的墙可以从另一边出来
			if (head.y < 0)
				head.y = Props.HEIGHT - 1;
			break;
		case DOWN:
			head.y++;
			if (head.y == Props.HEIGHT)
				head.y = 0;
			break;
		case LEFT:
			head.x--;
			if (head.x < 0)
				head.x = Props.WIDTH - 1;
			break;
		case RIGHT:
			head.x++;
			if (head.x == Props.WIDTH)
				head.x = 0;
			break;
		}
		body.addFirst(head);
	}

	/**
	 * 驱动蛇定时移动
	 * @author ParadiseWY
	 * @date 2019年10月2日 下午6:25:32
	 */
	private class SnakeDriver implements Runnable {

		public void run() {
			while (live) {
				if (!pause) {
					move();
					for (SnakeListener l : listeners)
						l.snakeMoved();
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 在尾巴上添加一个节点
	 */
	public void eatFood() {
		// 将上一次移动拿掉的节点再加上
		body.addLast(tail.getLocation());
		for (SnakeListener l : listeners)
			l.snakeEatFood();
	}

	/**
	 * �ı䷽��
	 * 
	 * @param direction
	 */
	public void changeDirection(int direction) {
		this.newDirection = direction;
	}

	/**
	 * �õ���ͷ�ڵ�
	 * 
	 * @return
	 */
	public Point getHead() {
		/* �Լ�Լ���ĸ�����ͷ */
		return body.getFirst();
	}

	/**
	 * �õ���β�ͽڵ�
	 * 
	 * @return
	 */
	public Point takeTail() {
		/* ȥ����β�� */
		return body.removeLast();
	}

	/**
	 * �õ��ߵĳ���
	 * 
	 * @return
	 */
	public int getLength() {
		return body.size();
	}

	/**
	 * ���߿�ʼ�˶�<BR>
	 * ����һ���µ��߳�
	 */
	public void begin() {
		new Thread(new SnakeDriver()).start();
	}

	/**
	 * ���߸���, ����ʼ�˶�<BR>
	 * ������ begin() ����
	 */
	public void reNew() {
		init();
		begin();
	}

	/**
	 * ��ʼ���ߵ���Ϣ<BR>
	 * ����, λ��, ����, �ٶ�, ��������ͣ״̬
	 */
	public void init() {
		body.clear();
		/* ��ʼ��λ�����м� */
		int x = Props.WIDTH / 2 - Props.INIT_LENGTH / 2;
		int y = Props.HEIGHT / 2;
		for (int i = 0; i < Props.INIT_LENGTH; i++)
			this.body.addFirst(new Point(x++, y));
		/* ����Ĭ�Ϸ���Ϊ���� */
		oldDirection = newDirection = RIGHT;
		/* ��ʼ���ٶ� */
		speed = Props.SPEED;
		/* ��ʼ����������ͣ״̬ */
		live = true;
		pause = false;
	}

	/**
	 * �Ƿ�Ե��Լ�������<BR>
	 * 
	 * @return ��ͷ�������Ƿ���Լ��������ĳһ�������غ�
	 */
	public boolean isEatBody() {
		/* Ҫ����ͷ�ų�, body.get(0) ����ͷ */
		for (int i = 1; i < body.size(); i++)
			if (getHead().equals(body.get(i)))
				return true;
		return false;
	}

	/**
	 * ���Լ�<BR>
	 * ������ drawHead(Graphics, int, int, int, int) ���� �� drawBody(Graphics, int,
	 * int, int, int) ����
	 * 
	 * @param g
	 */
	public void drawMe(Graphics g) {
		for (Point p : body) {
			/* �������� */
			g.setColor(bodyColor);
			drawBody(g, p.x * Props.CELL_WIDTH, p.y * Props.CELL_HEIGHT,
					Props.CELL_WIDTH, Props.CELL_HEIGHT);
		}
		/* ����ͷ */
		g.setColor(headColor);
		drawHead(g, getHead().x * Props.CELL_WIDTH, getHead().y
				* Props.CELL_HEIGHT, Props.CELL_WIDTH, Props.CELL_HEIGHT);
	}

	/**
	 * ����ͷ, ���Ը�����������ı���ͷ����ʾ
	 * 
	 * @param g
	 * @param x
	 *            �������� x
	 * @param y
	 *            �������� y
	 * @param width
	 *            ���(��λ:����)
	 * @param height
	 *            �߶�(��λ:����)
	 */
	public void drawHead(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * ���ߵ�һ������, ���Ը�����������ı��ߵ�����ڵ����ʾ
	 * 
	 * @param g
	 * @param x
	 *            �������� x
	 * @param y
	 *            �������� y
	 * @param width
	 *            ���(��λ:����)
	 * @param height
	 *            �߶�(��λ:����)
	 */
	public void drawBody(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * �õ���ͷ����ɫ
	 * 
	 * @return
	 */
	public Color getHeadColor() {
		return headColor;
	}

	/**
	 * ������ͷ����ɫ
	 * 
	 * @param headColor
	 */
	public void setHeadColor(Color headColor) {
		this.headColor = headColor;
	}

	/**
	 * �õ����������ɫ
	 * 
	 * @return
	 */
	public Color getBodyColor() {
		return bodyColor;
	}

	/**
	 * �������������ɫ
	 * 
	 * @param bodyColor
	 */
	public void setBodyColor(Color bodyColor) {
		this.bodyColor = bodyColor;
	}

	/**
	 * ��Ӽ�����
	 * 
	 * @param l
	 */
	public synchronized void addSnakeListener(SnakeListener l) {
		if (l == null)
			return;
		this.listeners.add(l);
	}

	/**
	 * �Ƴ�������
	 * 
	 * @param l
	 */
	public synchronized void removeSnakeListener(SnakeListener l) {
		if (l == null)
			return;
		this.listeners.remove(l);
	}

	/**
	 * ����, ����Ϊ Global �����õ� SPEED_STEP <BR>
	 * ����Ч���ٶȷ�Χ֮��(���Ӻ��ٶȴ��� 0����/��)
	 */
	public void speedUp() {
		if (speed > Props.SPEED_STEP)
			speed -= Props.SPEED_STEP;
	}

	/**
	 * ����, ����Ϊ Global �����õ� SPEED_STEP
	 */
	public void speedDown() {
		speed += Props.SPEED_STEP;
	}

	/**
	 * �õ��ߵ��ƶ��ٶ�
	 * 
	 * @return
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * �����ߵ��ƶ��ٶ�
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * ���Ƿ�������
	 * 
	 * @return
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * �������Ƿ�����
	 * 
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * ����������
	 */
	public void dead() {
		this.live = false;
	}

	/**
	 * �Ƿ�����ͣ״̬
	 * 
	 * @return
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * ������ͣ״̬
	 * 
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * ������ͣ״̬<BR>
	 * ������ͣ״̬, ������ƶ�<BR>
	 * �������ƶ�, ����ͣ
	 */
	public void changePause() {
		pause = !pause;
	}

}