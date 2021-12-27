package com.wy.screenshot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StartFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton start, cancel;

	private JPanel c;

	private BufferedImage get;

	private JTabbedPane jtp;// 一个放置很多份图片

	private int index;// 一个一直会递增的索引,用于标认图片

	private JRadioButton system;// 系统界面

	/** Creates a new instance of CaptureScreen */
	public StartFrame() {
		super("截取屏幕");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// UIManager管理界面的类
		} catch (Exception e) {
			e.printStackTrace();
		}
		initWindow();
		initOther();
	}

	private void initOther() {
		jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);// JTabbedPane用于切换图片，显示子项
	}

	private void initWindow() {
		start = new JButton("开始截取");
		cancel = new JButton("退出");
		start.addActionListener(this);
		cancel.addActionListener(this);
		JPanel buttonJP = new JPanel();
		c = new JPanel(new BorderLayout());
		JLabel jl = new JLabel("截取屏幕", JLabel.CENTER);// JLable类作用是用来对齐，居中
		jl.setFont(new Font("黑体", Font.BOLD, 40));
		jl.setForeground(Color.RED);
		c.add(jl, BorderLayout.CENTER);// 将jl放入主面板中，并居中
		buttonJP.add(start);
		buttonJP.add(cancel);
		buttonJP.setBorder(BorderFactory.createTitledBorder("公共操作区"));
		JPanel jp = new JPanel();// 放置一个单选按钮的面板
		jp.add(system = new JRadioButton("系统界面", true));
		system.addActionListener(this);
		jp.setBorder(BorderFactory.createTitledBorder("界面风格"));
		JPanel all = new JPanel();
		all.add(jp);
		all.add(buttonJP);
		this.getContentPane().add(c, BorderLayout.CENTER);
		this.getContentPane().add(all, BorderLayout.SOUTH);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);// 设置窗口相对于组件的位置
		this.setVisible(true);// 显示窗口
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void updates() {
		this.setVisible(true);
		if (get != null) {
			// 如果索引是0,则表示一张图片都没有被加入过,
			// 则要清除当前的东西,重新把tabpane放进来
			if (index == 0) {
				c.removeAll();
				c.add(jtp, BorderLayout.CENTER);
			} else {// 否则的话,直接对tabpane添加面板就可以了
					// 就什么都不用做了
			}
			PicPanel pic = new PicPanel(get);
			jtp.addTab("图片" + (++index), pic);
			jtp.setSelectedComponent(pic);
			SwingUtilities.updateComponentTreeUI(c); // 调整LookAndFeel（javax.swing）
		}
	}

	private void doStart() {
		try {
			this.setVisible(false);// 先隐藏窗口
			Thread.sleep(500);// 睡500毫秒是为了让主窗完全不见
			Robot ro = new Robot(); // （通过本地操作）控制鼠标、键盘等实际输入源（java.awt）
									// Robot类中有鼠标移动事件
			Toolkit tk = Toolkit.getDefaultToolkit(); // AWT组件的抽象父类（java.awt）
			// 绑定工具包，将不同界面合并到一起
			Dimension di = tk.getScreenSize();// Dimension封装长和宽
			Rectangle rec = new Rectangle(0, 0, di.width, di.height);// 坐标类，可以创建一个矩形
			BufferedImage bi = ro.createScreenCapture(rec);// 访问图像数据缓冲区
			JFrame jf = new JFrame();
			Temp temp = new Temp(jf, bi, di.width, di.height); // 自定义的Temp类的对象
			jf.getContentPane().add(temp, BorderLayout.CENTER);
			jf.setUndecorated(true);// 使标题一直存在
			jf.setSize(di);
			jf.setVisible(true);
			jf.setAlwaysOnTop(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 公用的处理保存图片的方法
	 */
	public void doSave(BufferedImage get) {
		try {
			if (get == null) {
				JOptionPane.showMessageDialog(this, "图片不能为空!!", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JFileChooser jfc = new JFileChooser(".");
			jfc.addChoosableFileFilter(new GIFfilter());
			jfc.addChoosableFileFilter(new BMPfilter());
			jfc.addChoosableFileFilter(new JPGfilter());
			jfc.addChoosableFileFilter(new PNGfilter());
			int i = jfc.showSaveDialog(this);// 打开保存文件的对话框
			if (i == JFileChooser.APPROVE_OPTION) {// 获得选中的文件对象
				File file = jfc.getSelectedFile();// 获得选中的文件
				String about = "PNG";
				String ext = file.toString().toLowerCase();
				javax.swing.filechooser.FileFilter ff = jfc.getFileFilter();
				if (ff instanceof JPGfilter) {
					if (!ext.endsWith(".jpg")) {
						String ns = ext + ".jpg";
						file = new File(ns);
						about = "JPG";
					}
				} else if (ff instanceof PNGfilter) {
					if (!ext.endsWith(".png")) {
						String ns = ext + ".png";
						file = new File(ns);
						about = "PNG";
					}
				} else if (ff instanceof BMPfilter) {
					if (!ext.endsWith(".bmp")) {
						String ns = ext + ".bmp";
						file = new File(ns);
						about = "BMP";
					}
				} else if (ff instanceof GIFfilter) {
					if (!ext.endsWith(".gif")) {
						String ns = ext + ".gif";
						file = new File(ns);
						about = "GIF";
					}
				}
				if (ImageIO.write(get, about, file)) {
					JOptionPane.showMessageDialog(this, "保存成功！");
				} else
					JOptionPane.showMessageDialog(this, "保存失败！");
			}
		} catch (Exception exe) {
			exe.printStackTrace();
		}
	}

	/**
	 * 公共的处理把当前的图片加入剪帖板的方法
	 */
	public void doCopy(final BufferedImage image) {
		try {
			if (get == null) {
				JOptionPane.showMessageDialog(this, "图片不能为空!!", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			} // java.awt.datatransfer（接口）
			Transferable trans = new Transferable() { // 内部类
				// Transferable接口 不同介质间数据传输

				public DataFlavor[] getTransferDataFlavors() {// 返回一个DataFlavor数组
					return new DataFlavor[] { DataFlavor.imageFlavor };// 指定传输数据时的数据格式为图片数据
				}

				public boolean isDataFlavorSupported(DataFlavor flavor) {// 判断当前传输数据的Transferable对象类型是否满足DataFlavor中的格式
					return DataFlavor.imageFlavor.equals(flavor);
				}

				public Object getTransferData(DataFlavor flavor)// 返回一个Transferable数据传输对象
						throws UnsupportedFlavorException, IOException {
					if (isDataFlavorSupported(flavor))
						return image;
					throw new UnsupportedFlavorException(flavor);
				}
			};

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);// 将Clipboard作为剪切板工具接口
			JOptionPane.showMessageDialog(this, "已复制到系统粘帖板!!");
		} catch (Exception exe) {
			exe.printStackTrace();
			JOptionPane.showMessageDialog(this, "复制到系统粘帖板出错!!", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 处理关闭事件
	private void doClose(Component c) {
		jtp.remove(c);
		c = null;
		System.gc();
	}

	public void actionPerformed(ActionEvent ae) {// 动作事件，按下动作
		Object source = ae.getSource();
		if (source == start) {
			doStart();
		} else if (source == cancel) {
			System.exit(0);
		}
	}

	// 一个内部类,它表示一个面板,一个可以被放进tabpane的面板
	// 也有自己的一套处理保存和复制的方法
	private class PicPanel extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;

		JButton save, copy, close, update;// 表示保存,复制,关闭，编辑的按钮

		BufferedImage get;// 得到的图片

		public PicPanel(BufferedImage get) {
			super(new BorderLayout());
			this.get = get;
			initPanel();
		}

		private void initPanel() {
			save = new JButton("保存(S)");
			copy = new JButton("复制到剪帖板(C)");
			close = new JButton("关闭(X)");
			update = new JButton("编辑(U)");

			save.setMnemonic('S');
			copy.setMnemonic('C');
			close.setMnemonic('X');
			update.setMnemonic('U');

			JPanel buttonPanel = new JPanel();
			buttonPanel.add(copy);
			buttonPanel.add(save);
			buttonPanel.add(close);
			buttonPanel.add(update);

			JLabel icon = new JLabel(new ImageIcon(get));
			this.add(new JScrollPane(icon), BorderLayout.CENTER);
			this.add(buttonPanel, BorderLayout.SOUTH);
			save.addActionListener(this);
			copy.addActionListener(this);
			close.addActionListener(this);
			update.addActionListener(this);

		}

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == save) {
				doSave(get);
			} else if (source == copy) {
				doCopy(get);
			} else if (source == close) {
				get = null;
				doClose(this);
			} else if (source == update) {
				doSave(get);
				this.setVisible(false);
				MainFrame.getInstance();
			}
		}
	}

	// 保存BMP格式的过滤器
	private class BMPfilter extends javax.swing.filechooser.FileFilter {

		public BMPfilter() {
		}

		public boolean accept(File file) {
			if (file.toString().toLowerCase().endsWith(".bmp") || file.isDirectory()) {
				return true;
			} else
				return false;
		}

		public String getDescription() {
			return "*.BMP(BMP图像)";
		}
	}

	// 保存JPG格式的过滤器
	private class JPGfilter extends javax.swing.filechooser.FileFilter {

		public JPGfilter() {
		}

		public boolean accept(File file) {
			if (file.toString().toLowerCase().endsWith(".jpg") || file.isDirectory()) {
				return true;
			} else
				return false;
		}

		public String getDescription() {
			return "*.JPG(JPG图像)";
		}
	}

	// 保存GIF格式的过滤器
	private class GIFfilter extends javax.swing.filechooser.FileFilter {

		public GIFfilter() {
		}

		public boolean accept(File file) {
			if (file.toString().toLowerCase().endsWith(".gif") || file.isDirectory()) {
				return true;
			} else
				return false;
		}

		public String getDescription() {
			return "*.GIF(GIF图像)";
		}
	}

	// 保存PNG格式的过滤器
	private class PNGfilter extends javax.swing.filechooser.FileFilter {

		public boolean accept(File file) {
			if (file.toString().toLowerCase().endsWith(".png") || file.isDirectory()) {
				return true;
			} else
				return false;
		}

		public String getDescription() {
			return "*.PNG(PNG图像)";
		}
	}

	/**
	 * @apiNote 一个临时类,用于显示当前的屏幕图像,鼠标单击,鼠标移动事件监听接口
	 * @author ParadiseWY
	 * @date 2019年10月11日 下午10:11:27
	 */
	private class Temp extends JPanel implements MouseListener, MouseMotionListener {

		private static final long serialVersionUID = 1L;

		private BufferedImage bi;

		private int width, height;

		private int startX, startY, endX, endY, tempX, tempY;

		private JFrame jf;

		// 表示选中的区域
		private Rectangle select = new Rectangle(0, 0, 0, 0);

		// 表示一般情况下的鼠标状态（十字线）
		private Cursor cs = new Cursor(Cursor.CROSSHAIR_CURSOR);

		// 表示当前的编辑状态
		private States current = States.DEFAULT;

		// 表示八个编辑点的区域
		private Rectangle[] rec;

		// 下面四个常量,分别表示谁是被选中的那条线上的端点
		public static final int START_X = 1;

		public static final int START_Y = 2;

		public static final int END_X = 3;

		public static final int END_Y = 4;

		// 当前被选中的X和Y,只有这两个需要改变
		private int currentX, currentY;

		// 当前鼠标移的地点
		private Point p = new Point();

		// 是否显示提示.如果鼠标左键一按,则提示就不再显示了
		private boolean showTip = true;

		public Temp(JFrame jf, BufferedImage bi, int width, int height) {
			this.jf = jf;
			this.bi = bi;
			this.width = width;
			this.height = height;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			initRecs();
		}

		private void initRecs() {
			rec = new Rectangle[8];
			for (int i = 0; i < rec.length; i++) {
				rec[i] = new Rectangle();
			}
		}

		public void paintComponent(Graphics g) {
			g.drawImage(bi, 0, 0, width, height, this);
			g.setColor(Color.RED);
			g.drawLine(startX, startY, endX, startY);
			g.drawLine(startX, endY, endX, endY);
			g.drawLine(startX, startY, startX, endY);
			g.drawLine(endX, startY, endX, endY);
			int x = startX < endX ? startX : endX;
			int y = startY < endY ? startY : endY;
			select = new Rectangle(x, y, Math.abs(endX - startX), Math.abs(endY - startY));
			int x1 = (startX + endX) / 2;
			int y1 = (startY + endY) / 2;
			g.fillRect(x1 - 2, startY - 2, 5, 5);
			g.fillRect(x1 - 2, endY - 2, 5, 5);
			g.fillRect(startX - 2, y1 - 2, 5, 5);
			g.fillRect(endX - 2, y1 - 2, 5, 5);
			g.fillRect(startX - 2, startY - 2, 5, 5);
			g.fillRect(startX - 2, endY - 2, 5, 5);
			g.fillRect(endX - 2, startY - 2, 5, 5);
			g.fillRect(endX - 2, endY - 2, 5, 5);
			rec[0] = new Rectangle(x - 5, y - 5, 10, 10);
			rec[1] = new Rectangle(x1 - 5, y - 5, 10, 10);
			rec[2] = new Rectangle((startX > endX ? startX : endX) - 5, y - 5, 10, 10);
			rec[3] = new Rectangle((startX > endX ? startX : endX) - 5, y1 - 5, 10, 10);
			rec[4] = new Rectangle((startX > endX ? startX : endX) - 5, (startY > endY ? startY : endY) - 5, 10, 10);
			rec[5] = new Rectangle(x1 - 5, (startY > endY ? startY : endY) - 5, 10, 10);
			rec[6] = new Rectangle(x - 5, (startY > endY ? startY : endY) - 5, 10, 10);
			rec[7] = new Rectangle(x - 5, y1 - 5, 10, 10);
			if (showTip) {
				g.setColor(Color.CYAN);
				g.fillRect(p.x, p.y, 170, 20);
				g.setColor(Color.RED);
				g.drawRect(p.x, p.y, 170, 20);
				g.setColor(Color.BLACK);
				g.drawString("请按住鼠标左键不放选择截图区", p.x, p.y + 15);
			}
		}

		// 根据东南西北等八个方向决定选中的要修改的X和Y的座标
		private void initSelect(States state) {
			switch (state) {
			case DEFAULT:
				currentX = 0;
				currentY = 0;
				break;
			case EAST:
				currentX = (endX > startX ? END_X : START_X);
				currentY = 0;
				break;
			case WEST:
				currentX = (endX > startX ? START_X : END_X);
				currentY = 0;
				break;
			case NORTH:
				currentX = 0;
				currentY = (startY > endY ? END_Y : START_Y);
				break;
			case SOUTH:
				currentX = 0;
				currentY = (startY > endY ? START_Y : END_Y);
				break;
			case NORTH_EAST:
				currentY = (startY > endY ? END_Y : START_Y);
				currentX = (endX > startX ? END_X : START_X);
				break;
			case NORTH_WEST:
				currentY = (startY > endY ? END_Y : START_Y);
				currentX = (endX > startX ? START_X : END_X);
				break;
			case SOUTH_EAST:
				currentY = (startY > endY ? START_Y : END_Y);
				currentX = (endX > startX ? END_X : START_X);
				break;
			case SOUTH_WEST:
				currentY = (startY > endY ? START_Y : END_Y);
				currentX = (endX > startX ? START_X : END_X);
				break;
			default:
				currentX = 0;
				currentY = 0;
				break;
			}
		}

		public void mouseMoved(MouseEvent me) {
			doMouseMoved(me);
			initSelect(current); // current：当前状态（state）
			if (showTip) {
				p = me.getPoint();
				repaint();
			}
		}

		// 特意定义一个方法处理鼠标移动,是为了每次都能初始化一下所要选择的区域
		private void doMouseMoved(MouseEvent me) {
			if (select.contains(me.getPoint())) {
				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				current = States.MOVE;
			} else {
				States[] st = States.values();
				for (int i = 0; i < rec.length; i++) {
					if (rec[i].contains(me.getPoint())) {
						current = st[i];
						this.setCursor(st[i].getCursor());
						return;
					}
				}
				this.setCursor(cs);
				current = States.DEFAULT;
			}
		}

		public void mouseExited(MouseEvent me) {// 鼠标离开组件调用
		}

		public void mouseEntered(MouseEvent me) {// 鼠标进入组件调用
		}

		public void mouseDragged(MouseEvent me) {// 鼠标拖动事件
			int x = me.getX();
			int y = me.getY();
			// 分别处理一系列的（光标）状态（枚举值）
			if (current == States.MOVE) {
				startX += (x - tempX);
				startY += (y - tempY);
				endX += (x - tempX);
				endY += (y - tempY);
				tempX = x;
				tempY = y;
			} else if (current == States.EAST || current == States.WEST) {
				if (currentX == START_X) {
					startX += (x - tempX);
					tempX = x;
				} else {
					endX += (x - tempX);
					tempX = x;
				}
			} else if (current == States.NORTH || current == States.SOUTH) {
				if (currentY == START_Y) {
					startY += (y - tempY);
					tempY = y;
				} else {
					endY += (y - tempY);
					tempY = y;
				}
			} else if (current == States.NORTH_EAST || current == States.NORTH_EAST || current == States.SOUTH_EAST
					|| current == States.SOUTH_WEST) {
				if (currentY == START_Y) {
					startY += (y - tempY);
					tempY = y;
				} else {
					endY += (y - tempY);
					tempY = y;
				}
				if (currentX == START_X) {
					startX += (x - tempX);
					tempX = x;
				} else {
					endX += (x - tempX);
					tempX = x;
				}
			} else {
				startX = tempX;
				startY = tempY;
				endX = me.getX();
				endY = me.getY();
			}
			this.repaint();
		}

		public void mousePressed(MouseEvent me) {
			showTip = false;
			tempX = me.getX();
			tempY = me.getY();
		}

		public void mouseReleased(MouseEvent me) {
			if (me.isPopupTrigger()) { // 右键
				if (current == States.MOVE) {
					showTip = true;
					p = me.getPoint();
					startX = 0;
					startY = 0;
					endX = 0;
					endY = 0;
					repaint();
				} else { // 普通情况
					jf.dispose();
					updates();
				}
			}
		}

		public void mouseClicked(MouseEvent me) {// 鼠标点击事件
			if (me.getClickCount() == 2) {
				Point p = me.getPoint();
				if (select.contains(p)) {
					if (select.x + select.width < this.getWidth() && select.y + select.height < this.getHeight()) {
						get = bi.getSubimage(select.x, select.y, select.width, select.height);
						jf.dispose();
						updates();
					} else {
						int wid = select.width, het = select.height;
						if (select.x + select.width >= this.getWidth()) {
							wid = this.getWidth() - select.x;
						}
						if (select.y + select.height >= this.getHeight()) {
							het = this.getHeight() - select.y;
						}
						get = bi.getSubimage(select.x, select.y, wid, het);
						jf.dispose();
						updates();
					}
				}
			}
		}
	}
}

// 一些表示状态的枚举
enum States {

	NORTH_WEST(new Cursor(Cursor.NW_RESIZE_CURSOR)), // 表示西北角
	NORTH(new Cursor(Cursor.N_RESIZE_CURSOR)),
	NORTH_EAST(new Cursor(Cursor.NE_RESIZE_CURSOR)),
	EAST(new Cursor(Cursor.E_RESIZE_CURSOR)),
	SOUTH_EAST(new Cursor(Cursor.SE_RESIZE_CURSOR)),
	SOUTH(new Cursor(Cursor.S_RESIZE_CURSOR)),
	SOUTH_WEST(new Cursor(Cursor.SW_RESIZE_CURSOR)),
	WEST(new Cursor(Cursor.W_RESIZE_CURSOR)),
	MOVE(new Cursor(Cursor.MOVE_CURSOR)),
	DEFAULT(new Cursor(Cursor.DEFAULT_CURSOR));

	private Cursor cs;

	States(Cursor cs) {
		this.cs = cs;
	}

	public Cursor getCursor() {
		return cs;
	}
}