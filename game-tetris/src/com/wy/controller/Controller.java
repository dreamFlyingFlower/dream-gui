package com.wy.controller;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import com.wy.config.Props;
import com.wy.listener.GameListener;
import com.wy.listener.GroundListener;
import com.wy.listener.ShapeListener;
import com.wy.model.Ground;
import com.wy.model.Shape;
import com.wy.model.ShapeFactory;
import com.wy.view.GamePanel;

/**
 * 控制器,控制Ground,Snake,Food,负责游戏的逻辑,按键处理事件
 * @author ParadiseWY
 * @date 2019年10月1日 下午3:03:06
 */
public class Controller extends KeyAdapter implements ShapeListener, GroundListener {

	protected Set<GameListener> listeners = new HashSet<GameListener>();

	protected ShapeFactory shapeFactory;

	protected Shape shape;

	protected Ground ground;

	protected GamePanel gamePanel;

	protected JLabel gameInfoLabel;

	/**
	 * 游戏状态
	 */
	protected boolean playing;

	public Controller(ShapeFactory shapeFactory, Ground ground, GamePanel gamePanel) {
		super();
		this.shapeFactory = shapeFactory;
		this.ground = ground;
		this.gamePanel = gamePanel;
	}

	public Controller(ShapeFactory shapeFactory, Ground ground, GamePanel gamePanel, JLabel gameInfoLabel) {

		this(shapeFactory, ground, gamePanel);
		this.setGameInfoLabel(gameInfoLabel);
	}

	/**
	 * 处理键盘按键 LEFT: 左移 RIGHT:右移 DOWN: 下移 UP:变形 PAGE UP: 加速 PAGE DOWN: 减速 Y: 重新开始
	 * ENTER: 暂停/继续
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() != KeyEvent.VK_Y && !playing)
			return;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (isPausingGame()) {
				this.continueGame();
			}
			shape.setSwift(false);

			if (isPlaying() && ground.isMoveable(shape, Shape.LEFT))
				shape.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			if (isPausingGame()) {
				this.continueGame();
			}
			shape.setSwift(false);

			if (isPlaying() && ground.isMoveable(shape, Shape.RIGHT))
				shape.moveRight();
			break;
		case KeyEvent.VK_UP:

			if (isPlaying()) {
				if (!shape.isPause()) {
					if (ground.isMoveable(shape, Shape.ROTATE)) {
						shape.setSwift(false);
						shape.rotate();
					}
				} else {
					if (ground.isMoveable(shape, Shape.UP))
						shape.moveUp();
					else {
						shape.die();
						shape = shapeFactory.getShape(this);
					}
				}
			}

			break;
		case KeyEvent.VK_DOWN:
			if (isPausingGame()) {
				this.continueGame();
			}
			if (isPlaying() && isShapeMoveDownable(shape))
				shape.moveDown();
			break;
		case KeyEvent.VK_PAGE_UP:
			shape.speedUp();
			break;
		case KeyEvent.VK_PAGE_DOWN:
			shape.speedDown();
			break;
		// 反引号,换一个图形
		case KeyEvent.VK_BACK_QUOTE:
			if (isPlaying()) {
				shape.die();
				shape = shapeFactory.getShape(this);
			}
			break;
		case KeyEvent.VK_ENTER:
			if (isPausingGame())
				this.continueGame();
			else
				this.pauseGame();
			break;
		case KeyEvent.VK_Y:
			if (!isPlaying())
				newGame();
			break;
		case KeyEvent.VK_SPACE:

			if (isPlaying() && !isPausingGame())
				shape.setSwift(true);
			break;
		}
		// 重新显示
		gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());
	}

	/**
	 * 询问图形是否可以下落,如果不能下落,就会让图形变成障碍物,同步方法
	 */
	public synchronized boolean isShapeMoveDownable(Shape s) {
		if (shape == null)
			return true;
		if (!playing || shape != s)
			return false;
		if (ground.isMoveable(shape, Shape.DOWN))
			return true;
		shape.die();
		ground.accept(shape);
		if (playing && !ground.isFull()) {
			shape = shapeFactory.getShape(this);
		}
		gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());
		return false;
	}

	/**
	 * 处理图形触发的shapeMovedDown事件,将会重新显示
	 */
	public void shapeMovedDown(Shape s) {
		if (playing && ground != null && shape != null)
			gamePanel.redisplay(ground, shape);
	}

	/**
	 * 开始一个新游戏
	 */
	public void newGame() {
		playing = true;
		ground.init();
		ground.addGroundListener(this);

		Props.CURRENT_SPEED = Props.DEFAULT_SPEED;
		shape = shapeFactory.getShape(this);

		if (playing)
			gamePanel.redisplay(ground, shape);

		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());

		for (GameListener l : listeners)
			l.gameStart();
	}

	/**
	 * 停止当前游戏
	 */
	public void stopGame() {
		if (shape == null)
			return;
		playing = false;
		for (GameListener l : listeners)
			l.gameOver();
	}

	/**
	 * 暂停游戏
	 */
	public void pauseGame() {
		if (shape == null)
			return;
		shape.setPause(true);
		for (GameListener l : listeners)
			l.gamePause();
	}

	/**
	 * 继续游戏
	 */
	public void continueGame() {
		shape.setPause(false);
		for (GameListener l : listeners)
			l.gameContinue();
	}

	/**
	 * 游戏是否在暂停状态
	 */
	public boolean isPausingGame() {
		return shape.isPause();
	}

	/**
	 * 获得游戏的最新提示信息
	 */
	public String getNewInfo() {
		if (!playing || ground.isFull())
			// 按Y开始新游戏
			return " ";
		else
			return new StringBuffer().append("提示").append("速度").append(shape.getSpeed()).append("毫秒/格").toString();
	}

	public ShapeFactory getShapeFactory() {
		return shapeFactory;
	}

	public void setShapeFactory(ShapeFactory shapeFactory) {
		this.shapeFactory = shapeFactory;
	}

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * 处理Ground触发的beforeDeleteFullLine事件会改变满行颜色并暂停一段时间
	 */
	public void beforeDeleteFullLine(Ground ground, int lineNum) {
		ground.changeFullLineColor(lineNum);
		gamePanel.redisplay(ground, shape);
		try {
			Thread.sleep(Props.STAY_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理Ground触发的fullLineDeleted事件,该方法只是打印一句话
	 */
	public void fullLineDeleted(Ground ground, int deletedLineCount) {
		System.out.println("消除了" + deletedLineCount + "行");
	}

	/**
	 * 是否正在游戏
	 * @return
	 */
	public boolean isPlaying() {
		if (playing && !ground.isFull())
			return true;
		return false;
	}

	/**
	 * 得到显示提示信息的组件
	 * @return
	 */
	public JLabel getGameInfoLabel() {
		return gameInfoLabel;
	}

	/**
	 * 设置
	 * @param gameInfoLabel
	 */
	public void setGameInfoLabel(JLabel gameInfoLabel) {
		this.gameInfoLabel = gameInfoLabel;
		this.gameInfoLabel.setSize(Props.WIDTH * Props.CELL_WIDTH, 20);
		this.gameInfoLabel.setFont(new Font("����", Font.PLAIN, 12));
		gameInfoLabel.setText(this.getNewInfo());
	}

	/**
	 * 处理Ground 的 groundIsFull() 事件,将触发游戏结束事件
	 */
	public void groundIsFull(Ground ground) {
		// TODO Auto-generated method stub
		if (playing) {
			playing = false;
			for (GameListener l : listeners)
				l.gameOver();
		}
	}

	/**
	 * 添加监听器,可添加多个
	 * @param l
	 */
	public void addGameListener(GameListener l) {
		if (l != null)
			this.listeners.add(l);
	}

	/**
	 * 移除监听器
	 * @param l
	 */
	public void removeGameListener(GameListener l) {
		if (l != null)
			this.listeners.remove(l);
	}
}