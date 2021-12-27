package com.wy.swing;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Swing图形化,对AWT进行了相关封装,减少了AWT对内存的消耗
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 11:09:17
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class MySwing {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(300, 100, 500, 400);
		f.setLayout(new FlowLayout());
		JButton but = new JButton("我是一个按钮");
		f.add(but);
		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
	}
}