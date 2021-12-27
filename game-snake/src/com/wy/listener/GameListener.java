package com.wy.listener;

/**
 * 游戏监听器
 * @author ParadiseWY
 * @date 2019年10月2日 下午9:17:51
 */
public interface GameListener {

	/**
	 * 开始游戏
	 */
	void gameStart();

	/**
	 * 结束游戏
	 */
	void gameOver();

	/**
	 * 暂停游戏
	 */
	void gamePause();

	/**
	 * 继续游戏
	 */
	void gameContinue();
}