package com.wy;

import java.awt.EventQueue;

import com.wy.server.StartFrame;

/**
 * @apiNote 坦克大战,服务端,启动类
 * @author ParadiseWY
 * @date 2019年10月11日
 */
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new StartFrame();
		});
	}
}