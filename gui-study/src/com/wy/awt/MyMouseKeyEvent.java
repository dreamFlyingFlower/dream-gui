package com.wy.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 鼠标按键事件监听
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 10:59:59
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class MyMouseKeyEvent {

	private Frame f;

	private Button but;

	private TextField tf;

	MyMouseKeyEvent() {
		init();
	}

	public void init() {
		f = new Frame("my frame");
		f.setBounds(300, 100, 600, 500);
		f.setLayout(new FlowLayout());
		tf = new TextField(20);
		but = new Button("my button");
		f.add(tf);
		f.add(but);
		myEvent();
		f.setVisible(true);
	}

	private void myEvent() {
		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		tf.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9)) {
					System.out.println(code + ".....是非法的");
					e.consume();
				}
			}
		});

		// 给But添加一个键盘监听
		but.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)
					// System.exit(0);
					System.out.println("ctrl+enter is run");
				// System.out.println(KeyEvent.getKeyText(e.getKeyCode())+"...."+e.getKeyCode());
			}
		});

		/**
		 * 添加动作监听,应用场景就是暂停视频和播放视频
		 */
		but.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("action ok");
			}
		});

		/**
		 * 鼠标操作
		 */
		but.addMouseListener(new MouseAdapter() {

			private int count = 1;

			private int clickCount = 1;

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("鼠标进入到该组件" + count++);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// 默认单击操作
				if (e.getClickCount() == 2) {
					System.out.println("双击动作" + clickCount++);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("鼠标按下操作");
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				System.out.println("滚轮滑动操作");
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println("鼠标移动操作");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("鼠标释放");
			}
		});
	}

	public static void main(String[] args) {
		new MyMouseKeyEvent();
	}
}