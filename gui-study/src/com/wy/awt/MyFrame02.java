package com.wy.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 自定义按钮事件监听
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 10:54:58
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class MyFrame02 {

	private Frame f;

	private Button but;

	MyFrame02() {
		init();
	}

	public static void main(String[] args) {
		new MyFrame02();
	}

	public void init() {
		f = new Frame("my frame");
		// 对frame进行基本设置
		f.setBounds(300, 100, 600, 500);
		f.setLayout(new FlowLayout());
		but = new Button("my button");
		// 将组件添加到frame中
		f.add(but);
		// 加载一下窗体上事件
		myEvent();
		// 显示窗体
		f.setVisible(true);
	}

	private void myEvent() {
		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		/**
		 * 想要知道哪个组件具备什么样的监听器,需要查看该组件对象的功能,通过查阅button的描述,发现按钮支持监听addActionListener
		 */
		but.addActionListener(new ActionListener() {

			private int count = 1;

			public void actionPerformed(ActionEvent e) {
				// System.out.println("退出,按钮干的");
				// System.exit(0);
				// f.add(new Button("Button-"+(count++)));
				// f.setVisible(true);
				// f.validate();
				// System.out.println(e.getSource());
				Button b = (Button) e.getSource();
				Frame f1 = (Frame) b.getParent();
				f1.add(new Button("button-" + count++));
				f1.validate();
			}
		});
	}
}