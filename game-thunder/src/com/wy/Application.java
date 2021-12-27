package com.wy;

import java.awt.EventQueue;

import com.wy.view.StartFrame;

/**
 * @apiNote 雷电游戏,飞机大战,启动类
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			StartFrame frame = new StartFrame();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}