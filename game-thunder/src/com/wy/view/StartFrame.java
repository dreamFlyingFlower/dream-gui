package com.wy.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * @apiNote 主要界面
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public StartFrame() {
		setTitle("飞机大战");
		setResizable(false);
		GamePanel main = new GamePanel();
		add(main, "Center");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		pack();
	}
}