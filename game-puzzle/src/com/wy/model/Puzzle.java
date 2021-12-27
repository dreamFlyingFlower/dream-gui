package com.wy.model;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.wy.view.CardFrame;
import com.wy.view.ShowImage;

public class Puzzle extends CardFrame {
	private static final long serialVersionUID = 1L;

	private boolean start;

	private int fWidth = this.getWidth();

	private int fHeight = this.getHeight();

	private GameOver gOver;

	private boolean index = true;

	public Puzzle() {
		start = false;
		ImageIcon icon = new ImageIcon("icon/OK.png");
		this.setSize(200 + fWidth, 200 + fHeight);
		this.setIconImage(icon.getImage());
		this.setVisible(true);
	}

	private void startGame() {
		if (start) {
			return;
		}
		start = true;
		updateMenuBegin();
	}

	public void endGame() {
		updateMenuBegin();
		initMenuBackground();
		start = false;
		JOptionPane.showMessageDialog(null, "Time:" + gOver.getTime() + "s\n" + "Step:" + gOver.getStep());
		Grades g = new Grades(this);
		g.set((int) gOver.getTime(), gOver.getStep());
	}

	protected void FrameLostFocus() {
		if (start && index) {
			nextCard();
			if (gOver != null) {
				gOver.pause();
			}
			index = false;
		}
	}

	protected void FrameGetFocus() {
		nextCard();
		if (gOver != null) {
			gOver.pause();
		}
		index = true;
	}

	public void menuNewClick() {
		Split sp = Split.get();
		BufferedImage[][] image;
		if (!sp.set(getFilename()) || (image = sp.divid(getLevel())) == null) {
			JOptionPane.showMessageDialog(null, "File " + getFilename() + " not exists!\nPlease select again~");
			return;
		}

		startGame();
		this.setSize(fWidth, fHeight);
		this.setVisible(true);
		int len = Split.level[getLevel()];
		int row = image.length;
		int cal = image[0].length;

		gOver = new GameOver(this);
		JButton[][] button = new JButton[row][cal];
		Matrix matrix = new Matrix(button, panel[0], len, gOver);
		matrix.init(image);

		this.setSize(cal * len + fWidth, row * len + fHeight);
		this.setVisible(true);
	}

	public void menuGradesClick() {
		Grades g = new Grades(this);
		g.show();
	}

	public void menuShowClick() {
		new ShowImage(getFilename());
	}

	public void menuExitClick() {
		System.exit(0);
	}

	public void menuHelpClick() {
		String help0 = "Move the small pictures to make it become the original image.\n\n";
		String help1 = "(1)touch the small picture around the blank block to make it move toward to the blank block.";
		JOptionPane.showMessageDialog(null, help0 + help1);
	}

	public void menuAboutClick() {
		String version = "Version: 0.0.2 beta 1\n";
		String author = "Author: Write by ZJGSU redraiment\n";
		String email = "E-mail: redraiment@126.com";
		JOptionPane.showMessageDialog(null, version + author + email);
	}
}