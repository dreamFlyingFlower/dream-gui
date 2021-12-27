package com.wy;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.wy.view.StartFrame;

/**
 * @apiNote 连连看,启动类
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			String lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";// swing外观
			try {
				UIManager.setLookAndFeel(lookAndFeel);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			StartFrame frame = new StartFrame();
			frame.setTitle("连连看");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(100, 100, 560, 430);
			frame.setLocation(440, 100);
			frame.setSize(540, 440);
			frame.setVisible(true);
		});
	}
}