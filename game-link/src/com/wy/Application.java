package com.wy;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.wy.view.MainFrame;

/**
 * @apiNote 连连看,需要重构,FIXME
 * @author ParadiseWY
 * @date 2019年10月7日 下午10:57:09
 */
public class Application {

	public static void main(String[] args) {
		// SwingUtilities
		EventQueue.invokeLater(() -> {
			MainFrame thisClass = new MainFrame();
			thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			thisClass.setVisible(true);
		});
	}
}