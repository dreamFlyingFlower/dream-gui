package com.wy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.wy.config.Props;
import com.wy.listener.GroundListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 地面,维护障碍物的信息 可以使用提供的 addObstacle(int, int) 和 addStubbornObstacle(int ,int)
 * 方法添加障碍物 通过setDrawGridding() 方法设置是否画空白(网格) 用 setColorfulSupport() 方法设置是否支持彩色显示
 * 覆盖 drawObstacle(Graphics, int, int, int, int) 方法可以改变障碍物的显示方式 覆盖
 * drawStubbornObstacle(Graphics, int, int, int, int) 方法可以改变不可消除的障碍物的显示方式
 * @author ParadiseWY
 * @date 2019年10月1日 下午3:26:43
 */
@Getter
@Setter
public class Ground {

	/**
	 * 监听器
	 */
	protected Set<GroundListener> listeners = new HashSet<GroundListener>();

	/**
	 * 容器
	 */
	protected UnitType[][] obstacles = new UnitType[Props.WIDTH][Props.HEIGHT];

	/**
	 * 不可消除的障碍物颜色
	 */
	protected Color stubbornObstacleColor = UnitType.STUBBORN_OBSTACLE.getColor();

	/**
	 * 默认网格颜色
	 */
	public static final Color DEFAULT_GRIDDING_COLOR = Color.LIGHT_GRAY;

	/**
	 * 网格的颜色
	 */
	protected Color griddingColor = DEFAULT_GRIDDING_COLOR;

	public static final Color DEFAULT_OBSTACLE_COLOR = UnitType.OBSTACLE.getColor();

	/**
	 * 障碍物颜色
	 */
	protected Color obstacleColor = DEFAULT_OBSTACLE_COLOR;

	public static final Color DEFAULT_FULL_LINE_COLOR = Color.DARK_GRAY;

	/**
	 * 满行的颜色
	 */
	protected Color fullLineColor = DEFAULT_FULL_LINE_COLOR;

	/**
	 * 是否画网格的开关
	 */
	protected boolean drawGridding;

	/**
	 * 是否支持彩色方块
	 */
	protected boolean colorfulSupport;

	/**
	 * 是否还能接受方块
	 */
	protected boolean full;

	protected Random random = new Random();

	public Ground() {
		init();
	}

	/**
	 * 初始化
	 */
	public void init() {
		clear();
		full = false;
	}

	/**
	 * 清空容器
	 */
	public void clear() {
		for (int x = 0; x < Props.WIDTH; x++)
			for (int y = 0; y < Props.HEIGHT; y++)
				obstacles[x][y] = UnitType.BLANK.clone();
	}

	/**
	 * 随机生成一个不可消除的障碍物,这个随机的坐标的y不小于5
	 */
	public void genernateAStubbornStochasticObstacle() {
		Random random = new Random();
		if (Props.HEIGHT < 5)
			return;
		int y = random.nextInt(5) + Props.HEIGHT - 5;
		int x = random.nextInt(Props.WIDTH);
		addStubbornObstacle(x, y);
	}

	/**
	 * 在指定范围内随机生成一些障碍物
	 * @param amount 要生成的数量
	 * @param lineNum 行号,从1开始
	 */
	public void generateSomeStochasticObstacle(int amount, int lineNum) {
		if (lineNum < 1)
			return;
		if (lineNum > Props.HEIGHT)
			lineNum = Props.HEIGHT;
		for (int i = 0; i < amount; i++) {
			int x = random.nextInt(Props.WIDTH);
			int y = random.nextInt(lineNum) + Props.HEIGHT - lineNum;
			obstacles[x][y] = UnitType.OBSTACLE.clone();
			obstacles[x][y].setColor(Props.getRandomColor());
		}
	}

	/**
	 * 将指定的图形变成方块,然后将会调用deleteFullLine方法扫描并删除满行
	 * @param shape 指定的图形
	 */
	public void accept(Shape shape) {

		int left = shape.getLeft();
		int top = shape.getTop();

		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if (left + x < Props.WIDTH && top + y < Props.HEIGHT) {
					if (shape.isMember(x, y, false))
						// 若超出上边界,就是放满了
						if (top + y < 0) {
							full = true;
							for (GroundListener l : listeners)
								l.groundIsFull(this);
						} else {
							// 先变成障碍物
							obstacles[left + x][top + y].cloneProperties(UnitType.OBSTACLE);
							obstacles[left + x][top + y].setColor(colorfulSupport ? shape.getColor() : obstacleColor);
						}
				}
		// 扫描删除满行
		deleteFullLine();
	}

	/**
	 * 扫描并删除满行,并调用deleteFullLine删除满行
	 */
	public void deleteFullLine() {
		// 本次总共消除的行数
		int deletedLineCount = 0;
		// 从最后一行开始,一直到第一行
		for (int y = Props.HEIGHT - 1; y >= 0; y--) {
			boolean isFull = true;
			for (int x = 0; x < Props.WIDTH; x++) {
				if (obstacles[x][y].equals(UnitType.BLANK))
					isFull = false;
			}
			// 若当前行满了
			if (isFull) {
				// 删除满行并且当前扫描号行加1
				deleteLine(y++);
				deletedLineCount++;
			}
		}

		// 若消行了,则触发消行事件
		if (deletedLineCount > 0)
			for (GroundListener l : listeners)
				l.fullLineDeleted(this, deletedLineCount);
	}

	/**
	 * 删除指定的行,该行上面的所有方块整体下移一行
	 * @param lineNum 消除的行号
	 */
	public void deleteLine(int lineNum) {
		// 触发将要消行事件
		for (GroundListener l : listeners)
			l.beforeDeleteFullLine(this, lineNum);

		for (int y = lineNum; y > 0; y--)
			for (int x = 0; x < Props.WIDTH; x++)
				if (!obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE))
					if (obstacles[x][y - 1].equals(UnitType.STUBBORN_OBSTACLE)) {
						obstacles[x][y].cloneProperties(UnitType.BLANK);
						obstacles[x][y].setColor(this.griddingColor);
					} else
						obstacles[x][y].cloneProperties(obstacles[x][y - 1]);
		// 将第一行变成空白
		for (int x = 0; x < Props.WIDTH; x++)
			if (!obstacles[x][0].equals(UnitType.STUBBORN_OBSTACLE))
				obstacles[x][0] = UnitType.BLANK.clone();
	}

	/**
	 * 判断容器是否放满了
	 * @return true是
	 */
	public boolean isFull() {
		return full;
	}

	/**
	 * 根据图形的动作,判断是否会碰到障碍物或不可消除的障碍物,或是否会超出边界
	 * @param shape 需要判断的图形
	 * @param action 图形动作
	 * @return 是否不可做该动作
	 */
	public synchronized boolean isMoveable(Shape shape, int action) {
		int left = shape.getLeft();
		int top = shape.getTop();
		switch (action) {

		case Shape.UP:
			top--;
			break;
		case Shape.DOWN:
			top++;
			break;
		case Shape.LEFT:
			left--;
			break;
		case Shape.RIGHT:
			left++;
			break;
		}

		if (top < 0 - shape.getHeight()) {
			return false;
		}
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)

				// 如果这个位置超出边界又是图形的一部分
				if ((left + x < 0 || left + x >= Props.WIDTH || top + y >= Props.HEIGHT)
						&& shape.isMember(x, y, action == Shape.ROTATE))
					return false;
				else if (top + y < 0)
					continue;
				else {
					// 或者位置不是空白,是障碍物或不可消除的障碍物,或是图形的一部分
					if (shape.isMember(x, y, action == Shape.ROTATE))
						if (!obstacles[left + x][top + y].equals(UnitType.BLANK))
							return false;
				}
		return true;
	}

	/**
	 * 改变指定行的颜色,可做消除钱的效果
	 * @param lineNum 指定行
	 */
	public void changeFullLineColor(int lineNum) {
		for (int x = 0; x < Props.WIDTH; x++)
			obstacles[x][lineNum].setColor(fullLineColor);
	}

	/**
	 * 在指定的位置添加一个障碍物
	 * @param x 格子x坐标
	 * @param y 格子y坐标
	 */
	public void addObstacle(int x, int y) {
		if (x < 0 || x >= Props.WIDTH || y < 0 || y >= Props.HEIGHT)
			throw new RuntimeException("这个位置超出了显示区域 (x:" + x + "  y:" + y + ")");
		obstacles[x][y].cloneProperties(UnitType.OBSTACLE);
	}

	/**
	 * 在指定的位置添加一个不可消除的障碍物
	 * @param x 格子x坐标
	 * @param y 格子y坐标
	 */
	public void addStubbornObstacle(int x, int y) {
		if (x < 0 || x >= Props.WIDTH || y < 0 || y >= Props.HEIGHT)
			throw new RuntimeException("这个位置超出了显示区域 (x:" + x + "  y:" + y + ")");
		obstacles[x][y].cloneProperties(UnitType.STUBBORN_OBSTACLE);
	}

	/**
	 * 显示
	 * @param g
	 */
	public void drawMe(Graphics g) {
		for (int x = 0; x < Props.WIDTH; x++)
			for (int y = 0; y < Props.HEIGHT; y++) {
				if (drawGridding && obstacles[x][y].equals(UnitType.BLANK)) {
					// 画空白/网格,若允许
					g.setColor(griddingColor);
					drawGridding(g, x * Props.CELL_WIDTH, y * Props.CELL_HEIGHT, Props.CELL_WIDTH,
							Props.CELL_HEIGHT);
				} else if (obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE)) {
					// 画不可消除障碍物
					g.setColor(stubbornObstacleColor);
					drawStubbornObstacle(g, x * Props.CELL_WIDTH, y * Props.CELL_HEIGHT, Props.CELL_WIDTH,
							Props.CELL_HEIGHT);
				} else if (obstacles[x][y].equals(UnitType.OBSTACLE)) {
					// 画障碍物
					g.setColor(obstacles[x][y].getColor());
					drawObstacle(g, x * Props.CELL_WIDTH, y * Props.CELL_HEIGHT, Props.CELL_WIDTH,
							Props.CELL_HEIGHT);
				}
			}
	}

	/**
	 * 画一个空白的方法(网格),可以覆盖这个方法改变空白(网格)的显示
	 * @param g 画笔
	 * @param x 格子x坐标
	 * @param y 格子y坐标
	 * @param width 宽
	 * @param height 高
	 */
	public void drawGridding(Graphics g, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	/**
	 * 画一个不可消除的障碍物,可以覆盖这个方法改变不可消除障碍物的显示
	 */
	public void drawStubbornObstacle(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 画一个障碍物的方法,可以覆盖这个方法改变障碍物的显示
	 */
	public void drawObstacle(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 添加监听器,可添加多个
	 * @param l
	 */
	public void addGroundListener(GroundListener l) {
		if (l != null)
			this.listeners.add(l);
	}

	/**
	 * 移除监听器
	 * @param l
	 */
	public void removeGroundListener(GroundListener l) {
		if (l != null)
			this.listeners.remove(l);
	}

	/**
	 * 指定位置是否是不可除的障碍物
	 * @param x 横坐标x
	 * @param y 纵坐标y
	 * @return
	 */
	public boolean isStubbornObstacle(int x, int y) {
		if (x >= 0 && x < Props.WIDTH && y >= 0 && y < Props.HEIGHT)
			return obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}

	/**
	 * 指定位置是否是障碍物
	 */
	public boolean isObstacle(int x, int y) {
		if (x >= 0 && x < Props.WIDTH && y >= 0 && y < Props.HEIGHT)
			return obstacles[x][y].equals(UnitType.OBSTACLE);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}

	/**
	 * 指定位置是否是空白
	 */
	public boolean isBlank(int x, int y) {
		if (x >= 0 && x < Props.WIDTH && y >= 0 && y < Props.HEIGHT)
			return obstacles[x][y].equals(UnitType.BLANK);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}
}