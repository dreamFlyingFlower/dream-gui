package com.wy;

import java.awt.EventQueue;

import javax.swing.JLabel;

import com.wy.controller.Controller;
import com.wy.game.MainFrame;
import com.wy.model.Food;
import com.wy.model.Ground;
import com.wy.model.Snake;
import com.wy.view.GamePanel;

/**
 * @apiNote 贪吃蛇启动类,整个项目需要重构,FIXME
 * @author ParadiseWY
 * @date 2019年10月2日 上午7:22:13
 */
public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MainFrame frame = new MainFrame(
					new Controller(new Snake(), new Food(), new Ground(), new GamePanel(), new JLabel()));
			frame.setVisible(true);
		});
	}
}