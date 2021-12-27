package com.wy;

import java.awt.EventQueue;

import com.wy.controller.Controller;
import com.wy.game.MainFrame;
import com.wy.model.Ground;
import com.wy.model.ShapeFactory;
import com.wy.view.GamePanel;

/**
 * @apiNote 俄罗斯方块启动类,整个程序需要重构 FIXME
 * @author ParadiseWY
 * @date 2019年10月1日 下午6:12:21
 */
public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Controller controller = new Controller(new ShapeFactory(), new Ground(), new GamePanel());
			MainFrame loginFrm = new MainFrame(controller);
			loginFrm.setVisible(true);
		});
	}
}