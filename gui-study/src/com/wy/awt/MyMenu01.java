package com.wy.awt;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 菜单事件监听
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 11:02:11
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class MyMenu01 {

	private Frame f;

	private MenuBar mb;

	private Menu m, subMenu;

	private MenuItem closeItem, subItem;

	MyMenu01() {
		init();
	}

	public void init() {
		f = new Frame("my window");
		f.setBounds(300, 100, 500, 600);
		f.setLayout(new FlowLayout());
		mb = new MenuBar();
		m = new Menu("文件");
		subMenu = new Menu("子菜单");
		subItem = new MenuItem("子条目");
		closeItem = new MenuItem("退出");
		subMenu.add(subItem);
		m.add(subMenu);
		m.add(closeItem);
		mb.add(m);
		f.setMenuBar(mb);
		myEvent();
		f.setVisible(true);
	}

	private void myEvent() {

		closeItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new MyMenu01();
	}
}