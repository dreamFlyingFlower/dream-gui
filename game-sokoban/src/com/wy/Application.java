package com.wy;

import java.awt.EventQueue;

import com.wy.view.StartFrame;

/**
 * @apiNote 推箱子,启动类
 * @author ParadiseWY
 * @date 2019年10月11日
 */
public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new StartFrame();
		});
	}
}