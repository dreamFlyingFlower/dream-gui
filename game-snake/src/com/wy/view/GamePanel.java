package com.wy.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wy.config.Props;
import com.wy.model.Food;
import com.wy.model.Ground;
import com.wy.model.Snake;

import lombok.Getter;
import lombok.Setter;

/**
 * 游戏显示界面
 * @author ParadiseWY
 * @date 2019年10月2日 下午9:15:28
 */
@Getter
@Setter
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image oimg;

	private Graphics og;

	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xcfcfcf);

	private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

	public GamePanel() {
		this.setSize(Props.WIDTH * Props.CELL_WIDTH, Props.HEIGHT * Props.CELL_HEIGHT);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setFocusable(true);
	}

	/**
	 * 重新显示Ground,Shape
	 * @param ground
	 * @param snake
	 * @param food
	 */
	public synchronized void redisplay(Ground ground, Snake snake, Food food) {
		// 重新显示
		if (og == null) {
			oimg = createImage(getSize().width, getSize().height);
			if (oimg != null)
				og = oimg.getGraphics();
		}
		if (og != null) {
			og.setColor(backgroundColor);
			og.fillRect(0, 0, Props.WIDTH * Props.CELL_WIDTH, Props.HEIGHT * Props.CELL_HEIGHT);
			if (ground != null)
				ground.drawMe(og);
			snake.drawMe(og);
			if (food != null)
				food.drawMe(og);
			this.paint(this.getGraphics());
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(oimg, 0, 0, this);
	}
}