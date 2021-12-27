package com.wy.awt;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 菜单事件监听
 * 
 * 制作可以双击执行的jar包:
 * 
 * <pre>
 * 1.将多个类封装到了一个包(package)中
 * 2.定义一个jar包的配置信息
 * 3.定义一个文件a.txt,文件内容内容为:Main-Class:(空格)包名.类名(回车)
 * 4.打jar包:jar -cvfm my.jar a.txt 包名
 * 5.通过winrar程序进行验证,查看该jar的配置文件中是否有自定义的配置信息
 * 6.通过工具--文件夹选项--文件类型--jar类型文件,通过高级,定义该jar类型文件的打开动作的关联程序->jdk\bin\javaw.exe -jar
 * 7.双击打开
 * </pre>
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 11:03:17
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class MyMenu02 {

	private Frame f;

	private MenuBar bar;

	private TextArea ta;

	private Menu fileMenu;

	private MenuItem openItem, saveItem, closeItem;

	private FileDialog openDia, saveDia;

	private File file;

	MyMenu02() {
		init();
	}

	public void init() {
		f = new Frame("my window");
		f.setBounds(300, 100, 650, 600);
		bar = new MenuBar();
		ta = new TextArea();
		fileMenu = new Menu("文件");
		openItem = new MenuItem("打开");
		saveItem = new MenuItem("保存");
		closeItem = new MenuItem("退出");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(closeItem);
		bar.add(fileMenu);
		f.setMenuBar(bar);
		openDia = new FileDialog(f, "我要打开", FileDialog.LOAD);
		saveDia = new FileDialog(f, "我要保存", FileDialog.SAVE);
		f.add(ta);
		myEvent();
		f.setVisible(true);
	}

	private void myEvent() {

		saveItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (file == null) {
					saveDia.setVisible(true);

					String dirPath = saveDia.getDirectory();
					String fileName = saveDia.getFile();
					if (dirPath == null || fileName == null)
						return;
					file = new File(dirPath, fileName);
				}
				try {
					BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
					String text = ta.getText();
					bufw.write(text);
					// bufw.flush();
					bufw.close();
				} catch (IOException ex) {
					throw new RuntimeException();
				}
			}
		});

		openItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();
				String fileName = openDia.getFile();
				// System.out.println(dirPath+"..."+fileName);
				if (dirPath == null || fileName == null)
					return;
				ta.setText("");
				file = new File(dirPath, fileName);
				try {
					BufferedReader bufr = new BufferedReader(new FileReader(file));
					String line = null;
					while ((line = bufr.readLine()) != null) {
						ta.append(line + "\r\n");
					}
					bufr.close();
				} catch (IOException ex) {
					throw new RuntimeException("读取失败");
				}
			}
		});

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
		new MyMenu02();
	}
}