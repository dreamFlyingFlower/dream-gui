package com.wy.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * GUI基本Frame
 * 
 * 创建图形化界面:
 * 
 * <pre>
 * 1.创建frame窗体
 * 2.对窗体进行基本设置.比如大小,位置,布局
 * 3.定义组件.如按钮,菜单等
 * 4.将组件通过窗体的add方法添加到窗体中
 * 5.让窗体显示,通过setVisible(true)
 * </pre>
 * 
 * 事件监听机制的特点:
 * 
 * <pre>
 * 1.事件源:就是awt包或者swing包中的那些图形界面组件
 * 2.事件:每一个事件源都有自己特有的对应事件和共性事件
 * 3.监听器:将可以触发某一个事件的动作(不只一个动作)都已经封装到了监听器中
 * 4.事件处理:每个事件触发之后的处理逻辑
 * </pre>
 * 
 * {@link WindowListener}:windows窗口监听器,可以只重写其中用到的方法,不必全部重写
 * ->{@link WindowAdapter}:实现了WindowListener的所有方法,只需要重写需要的方法即可
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 10:45:02
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class MyFrame01 {

	public static void main(String[] args) {
		Frame f = new Frame("my awt");
		f.setSize(500, 400);
		f.setLocation(300, 200);
		// 设置布局管理
		f.setLayout(new FlowLayout());
		// 设置图标
		f.setIconImage(Toolkit.getDefaultToolkit().createImage("imagePath"));
		Button b = new Button("按钮");
		f.add(b);
		// 添加窗口监听
		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.out.println("我关");
				System.exit(0);
			}

			public void windowActivated(WindowEvent e) {
				System.out.println("我活了");
			}

			public void windowOpened(WindowEvent e) {
				System.out.println("我被打开了,hahahhahah");
			}
		});
		// 设置可见
		f.setVisible(true);
	}
}