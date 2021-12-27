package com.wy.listener;

/**
 * 游戏监听器
 * @author ParadiseWY
 * @date 2019年10月1日 下午4:05:51
 */
public interface GameListener {

	/**
	 * 游戏开始
	 */
	void gameStart();

	/**
	 * 游戏结束
	 */
	void gameOver();

	/**
	 * 游戏暂停
	 */
	void gamePause();

	/**
	 * 游戏继续
	 */
	void gameContinue();
}