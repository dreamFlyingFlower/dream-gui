package com.wy.controller;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import com.wy.config.Props;
import com.wy.listener.GameListener;
import com.wy.listener.SnakeListener;
import com.wy.model.Food;
import com.wy.model.Ground;
import com.wy.model.Snake;
import com.wy.view.GamePanel;

import lombok.Getter;
import lombok.Setter;

/**
 * 控制游戏中所有的对象
 * @author ParadiseWY
 * @date 2019年10月2日 下午9:04:31
 */
@Getter
@Setter
public class Controller extends KeyAdapter implements SnakeListener {

	private Ground ground;

	private Snake snake;

	private Food food;

	private GamePanel gamePanel;

	private JLabel gameInfoLabel;

	private boolean playing;

	private int map;

	private Set<GameListener> listeners = new HashSet<GameListener>();

	public Controller(Snake snake, Food food, Ground ground, GamePanel gamePanel) {
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		this.gamePanel = gamePanel;
		if (ground != null && food != null)
			food.setLocation(ground.getFreePoint());
		this.snake.addSnakeListener(this);
	}

	public Controller(Snake snake, Food food, Ground ground, GamePanel gamePanel, JLabel gameInfoLabel) {

		this(snake, food, ground, gamePanel);
		this.setGameInfoLabel(gameInfoLabel);

		if (gameInfoLabel != null)
			gameInfoLabel.setText(getNewInfo());
	}

	/**
	 * 按键处理事件.up上,down下,left左,right右,enter或space暂停/继续,page up加速,page down减速,y重新开始
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_Y && !playing)
			return;
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			if (snake.isPause()) {
				snake.changePause();
				for (GameListener l : listeners)
					l.gameContinue();
			}
			snake.changeDirection(Snake.UP);
			break;
		case KeyEvent.VK_DOWN:
			if (snake.isPause()) {
				snake.changePause();
				for (GameListener l : listeners)
					l.gameContinue();
			}
			snake.changeDirection(Snake.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			if (snake.isPause()) {
				snake.changePause();
				for (GameListener l : listeners)
					l.gameContinue();
			}
			snake.changeDirection(Snake.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			if (snake.isPause()) {
				snake.changePause();
				for (GameListener l : listeners)
					l.gameContinue();
			}
			snake.changeDirection(Snake.RIGHT);
			break;
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			snake.changePause();
			for (GameListener l : listeners)
				if (snake.isPause())
					l.gamePause();
				else
					l.gameContinue();
			break;
		case KeyEvent.VK_PAGE_UP:
			snake.speedUp();
			break;
		case KeyEvent.VK_PAGE_DOWN:
			snake.speedDown();
			break;
		case KeyEvent.VK_Y:
			if (!isPlaying())
				newGame();
			break;
		}

		// 重新显示
		if (gamePanel != null)
			gamePanel.redisplay(ground, snake, food);
		// 更新提示
		if (gameInfoLabel != null)
			gameInfoLabel.setText(getNewInfo());
	}

	/**
	 * 蛇的移动事件
	 */
	public void snakeMoved() {
		if (food != null && food.isSnakeEatFood(snake)) {
			// 若蛇吃到食物,增加身体,再重新丢一个食物
			snake.eatFood();
			food.setLocation(ground == null ? food.getNew() : ground.getFreePoint());

		} else if (ground != null && ground.isSnakeEatRock(snake)) {
			// 吃到食物,肯定没吃到石头;如果吃到石头或身体,则gameover
			stopGame();
		}
		if (snake.isEatBody())
			stopGame();
		if (gamePanel != null)
			gamePanel.redisplay(ground, snake, food);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(getNewInfo());
	}

	/**
	 * 开始一个新的游戏
	 */
	public void newGame() {

		if (ground != null) {
			switch (map) {
			case 2:
				ground.clear();
				ground.generateRocks2();
				break;
			default:
				ground.init();
				break;
			}
		}
		playing = true;

		snake.reNew();
		for (GameListener l : listeners)
			l.gameStart();
	}

	/**
	 * 结束游戏
	 */
	public void stopGame() {
		if (playing) {
			playing = false;
			snake.dead();
			for (GameListener l : listeners)
				l.gameOver();
		}
	}

	/**
	 * 暂停游戏
	 */
	public void pauseGame() {
		snake.setPause(true);
		for (GameListener l : listeners)
			l.gamePause();
	}

	/**
	 * 继续游戏
	 */
	public void continueGame() {
		snake.setPause(false);
		for (GameListener l : listeners)
			l.gameContinue();
	}

	/**
	 * 得到最新的提示信息
	 */
	public String getNewInfo() {
		if (!snake.isLive())
			// 提示Y重新开始游戏
			return " ";
		else
			return new StringBuffer().append("提示:").append("速度").append(snake.getSpeed()).toString() + " 毫秒/格";
	}

	/**
	 * 添加监听器
	 * @param l
	 */
	public synchronized void addGameListener(GameListener l) {
		if (l != null)
			this.listeners.add(l);
	}

	/**
	 * 移除监听器
	 * @param l
	 */
	public synchronized void removeGameListener(GameListener l) {
		if (l != null)
			this.listeners.remove(l);
	}

	/**
	 * 蛇吃到食物后触发的其他事件,可根据需求自定义
	 */
	public void snakeEatFood() {
		System.out.println("吃到食物");
	}

	public void setGameInfoLabel(JLabel gameInfoLabel) {
		this.gameInfoLabel = gameInfoLabel;
		this.gameInfoLabel.setSize(Props.WIDTH * Props.CELL_WIDTH, 20);
		this.gameInfoLabel.setFont(new Font("宋体", Font.PLAIN, 12));
		gameInfoLabel.setText(this.getNewInfo());
	}

	public boolean isPausingGame() {
		return snake.isPause();
	}
}