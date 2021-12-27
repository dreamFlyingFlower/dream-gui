package com.wy.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wy.config.Props;
import com.wy.model.Ground;
import com.wy.model.Shape;

/**
 * 游戏显示的界面
 * @author ParadiseWY
 * @date 2019年10月1日 上午11:05:54
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image image;

	private Graphics graphics;

	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xcfcfcf);

	/**
	 * 背景颜色
	 */
	protected Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

	public GamePanel() {
		// 设置大小和布局
		this.setSize(Props.WIDTH * Props.CELL_WIDTH, Props.HEIGHT * Props.CELL_HEIGHT);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setFocusable(true);
	}

	/**
	 * 重新显示
	 * @param ground
	 * @param shape
	 */
	public synchronized void redisplay(Ground ground, Shape shape) {
		if (graphics == null) {
			image = createImage(getSize().width, getSize().height);
			if (image != null)
				graphics = image.getGraphics();
		}
		if (graphics != null) {
			graphics.setColor(backgroundColor);
			graphics.fillRect(0, 0, Props.WIDTH * Props.CELL_WIDTH, Props.HEIGHT * Props.CELL_HEIGHT);
			ground.drawMe(graphics);
			if (shape != null)
				shape.drawMe(graphics);
			this.paint(this.getGraphics());
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}

	/**
	 * 得到当前的背景颜色
	 * @return
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * 设置当前的背景颜色
	 * @param backgroundColor
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}