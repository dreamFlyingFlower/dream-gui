package com.wy.screenshot;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

/**
 * 屏幕截图
 * 
 * @author 飞花梦影
 * @date 2021-12-25 12:58:18
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class Screenshot {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new StartFrame();
		});
	}

	public static void run() {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new StartFrame();
			}
		});
	}
}