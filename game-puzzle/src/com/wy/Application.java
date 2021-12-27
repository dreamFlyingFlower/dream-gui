package com.wy;

import java.awt.EventQueue;

import com.wy.model.Puzzle;

/**
 * @apiNote 拼图游戏,启动类
 * @author ParadiseWY
 * @date 2019年10月9日 下午8:16:34
 */
public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			new Puzzle();
		});
	}
}
