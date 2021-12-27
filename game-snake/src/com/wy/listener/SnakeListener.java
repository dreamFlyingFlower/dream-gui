package com.wy.listener;

/**
 * 蛇的监听
 * @author ParadiseWY
 * @date 2019年10月2日 下午9:18:54
 */
public interface SnakeListener {
	/**
	 * 蛇移动
	 */
	void snakeMoved();

	/**
	 * 蛇吃到食物
	 */
	void snakeEatFood();
}