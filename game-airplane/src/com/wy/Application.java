package com.wy;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.wy.view.StartFrame;

/**
 * @apiNote
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new JFrame("飞机大战");
			StartFrame game = new StartFrame();
			frame.add(game);
			BufferedImage image;
			try {
				image = ImageIO.read(StartFrame.class.getResource("icon.jpg"));
				frame.setIconImage(image);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}

			frame.setSize(StartFrame.WIDTH, StartFrame.HEIGHT);
			frame.setAlwaysOnTop(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			game.action();
		});
	}
}