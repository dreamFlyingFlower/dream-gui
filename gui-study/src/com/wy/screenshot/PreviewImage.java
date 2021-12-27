package com.wy.screenshot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * @apiNote 预览水印效果
 * @author ParadiseWY
 * @date 2019年10月11日 下午10:09:07
 */
public class PreviewImage extends JDialog {

	private static final long serialVersionUID = 1L;

	public PreviewImage(final BufferedImage buffImg) {
		int imgHeight = buffImg.getHeight();
		int imgWidth = buffImg.getWidth();
		JPanel imgPanel = new JPanel() {// 将截图预览画在面板上
			private static final long serialVersionUID = 6246862165441423926L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;// 二维图形处理
				if (buffImg != null) {
					g2d.drawImage(buffImg, 0, 0, this);
				}
			}
		};
		setSize(imgWidth, imgHeight);
		Common.setCentered(this);// 居中显示
		setUndecorated(true);// 禁用边框修饰
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();// 释放窗体占用的资源
				}
			}
		});
		DragPicListener listener = new DragPicListener(this);// 鼠标事件的处理
		addMouseListener(listener);// 鼠标的监听（点击，松开）
		addMouseMotionListener(listener);// 鼠标的移动和拖放监听器
		add(imgPanel);
		setVisible(true);// 设置可见
	}

	/**
	 * 图片拖动的监听器
	 */
	private class DragPicListener implements MouseInputListener {// 鼠标事件处理
		private JDialog dialog;

		Point point = new Point(0, 0); // 坐标点

		public DragPicListener(JDialog dialog) {
			this.dialog = dialog;
		}

		public void mouseDragged(MouseEvent e) {
			Point newPoint = SwingUtilities.convertPoint(dialog, e.getPoint(), getParent());
			setLocation(getX() + (newPoint.x - point.x), getY() + (newPoint.y - point.y));
			point = newPoint; // 更改坐标点
		}

		public void mousePressed(MouseEvent e) {
			point = SwingUtilities.convertPoint(dialog, e.getPoint(), dialog.getParent()); // 得到当前坐标点
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}
	}
}