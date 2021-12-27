package com.wy.config;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * @apiNote 配置文件
 * @author ParadiseWY
 * @date 2019年10月1日 上午7:16:31
 */
public class Props {

	private static Properties properties = new Properties();

	/**
	 * 配置文件的默认路径
	 */
	private static String CONFIG_FILE = "tetris.ini";

	/**
	 * 一个格子的宽度
	 */
	public static final int CELL_WIDTH;

	/**
	 * 一个格子的高度
	 */
	public static final int CELL_HEIGHT;

	/**
	 * 用格子表示的宽度
	 */
	public static final int WIDTH;

	/**
	 * 用格子表示的高度
	 */
	public static final int HEIGHT;

	/**
	 * 格子落下的初始速度
	 */
	public static final int DEFAULT_SPEED;

	/**
	 * 格子当前的下落速度
	 */
	public static int CURRENT_SPEED;

	/**
	 * 图形快速下落的速度
	 */
	public static final int SWIFT_SPEED;

	/**
	 * 每次加速或减速的幅度
	 */
	public static final int SPEED_STEP;

	/**
	 * 消除满行前暂停效果的初始时间
	 */
	public static final int DEFAULT_STAY_TIME;

	/**
	 * 消除满行前暂停效果的时间
	 */
	public static int STAY_TIME;

	private static Random random = new Random();

	public static final String TITLE_LABEL_TEXT;

	public static final String INFO_LABEL_TEXT;

	private static final Color[] DEFAULT_COLORS = new Color[] { new Color(0x990066), new Color(0x990099),
			new Color(0x330099), new Color(0x663300), new Color(0x009966), new Color(0x003333) };

	public static final List<Color> COMMON_COLORS;

	/**
	 * 返回一个随机的颜色
	 * @return
	 */
	public static Color getRandomColor() {
		return DEFAULT_COLORS[random.nextInt(DEFAULT_COLORS.length)];
	}

	private Props() {
	}

	/**
	 * 初始化
	 */
	static {
		try (InputStream inputStream = Props.class.getClassLoader().getResourceAsStream(CONFIG_FILE);) {
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer temp = null;
		// 初始化参数
		WIDTH = (temp = getIntValue("width")) != null && temp <= 80 && temp >= 10 ? temp : 15;
		HEIGHT = (temp = getIntValue("height")) != null && temp <= 60 && temp >= 10 ? temp : 20;
		DEFAULT_SPEED = CURRENT_SPEED = (temp = getIntValue("speed")) != null && temp >= 10 ? temp : 300;
		SWIFT_SPEED = (temp = getIntValue("swift_speed")) != null && temp >= 0 ? temp : 15;
		SPEED_STEP = (temp = getIntValue("speed_step")) != null && temp >= 1 ? temp : 25;
		DEFAULT_STAY_TIME = STAY_TIME = (temp = getIntValue("stay_time")) != null && temp >= 0 ? temp : 200;
		int defaultCellSize = (temp = getIntValue("cell_size")) != null && temp > 0 && temp <= 100 ? temp : 23;
		CELL_WIDTH = (temp = getIntValue("cell_width")) != null && temp > 0 && temp <= 100 ? temp : defaultCellSize;
		CELL_HEIGHT = (temp = getIntValue("cell_height")) != null && temp > 0 && temp <= 100 ? temp : defaultCellSize;
		String tempStr = null;
		TITLE_LABEL_TEXT = (tempStr = getValue("title")) == null ? "说明" : tempStr;
		INFO_LABEL_TEXT = (tempStr = getValue("info")) == null ? "方向键控制方向, 回车键暂停/继续\\nPAGE UP, PAGE DOWN 加速或减速"
				: tempStr;
		COMMON_COLORS = loadColors();
	}

	/**
	 * 没有某个键位的时候
	 */
	private static Integer getIntValue(String key) {
		if (key == null)
			throw new RuntimeException("key不能为空");
		try {
			return new Integer(properties.getProperty(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static String getValue(String key) {
		try {
			return new String(properties.getProperty(key).getBytes("iso8859-1"));
		} catch (Exception e) {
			return null;
		}
	}

	private static List<Color> loadColors() {
		List<Color> l = new ArrayList<Color>(7);
		for (int i = 0; i < 7; i++) {
			l.add(null);
		}
		Set<Object> set = properties.keySet();
		Iterator<Object> iter = set.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if ("1".equals(key.trim()))
				addColor(l, 0, getValue(key));
			else if ("2".equals(key.trim()))
				addColor(l, 1, getValue(key));
			else if ("3".equals(key.trim()))
				addColor(l, 2, getValue(key));
			else if ("4".equals(key.trim()))
				addColor(l, 3, getValue(key));
			else if ("5".equals(key.trim()))
				addColor(l, 4, getValue(key));
			else if ("6".equals(key.trim()))
				addColor(l, 5, getValue(key));
			else if ("7".equals(key.trim()))
				addColor(l, 6, getValue(key));
		}

		for (int i = 0; i < 7; i++) {
			l.remove(null);
		}

		if (l.size() < 1) {
			for (int i = 0; i < DEFAULT_COLORS.length; i++) {
				l.add(DEFAULT_COLORS[i]);
			}
		} else {
			if (l.size() != 7)
				System.out.println("您一共设置了" + l.size() + " 种有效颜色,建议设置七种");

			return l.subList(0, l.size() > 7 ? 7 : l.size());
		}
		return l;
	}

	private static void addColor(List<Color> l, int index, String str) {
		str = str.trim();
		if (!str.startsWith("0x") || str.length() < 3) {
			System.out.println("颜色设置有误,请检查:" + str + "(key)");
			return;
		}

		try {
			String strRGB = str.substring(2, str.length() >= 8 ? 8 : str.length());
			int rgb = Integer.valueOf(strRGB, 16);
			Color c = new Color(rgb);
			if (c != null) {
				l.add(index, c);
			}
		} catch (Exception e) {
			System.out.println("颜色设置有误,请检查:" + str + "(key)");
			e.printStackTrace();
			return;
		}
	}
}