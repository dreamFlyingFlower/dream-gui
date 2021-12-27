package com.wy.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类
 * @author ParadiseWY
 * @date 2019年10月2日 下午6:19:41
 */
public class Props {

	private static Properties properties = new Properties();

	/**
	 * 配置文件的名称,需要放在当前环境下
	 */
	private static String CONFIG_FILE = "snake.ini";

	/**
	 * 格子的宽度,单位像素
	 */
	public static final int CELL_WIDTH;

	/**
	 * 格子的高度,单位像素
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
	 * 真实像素宽度,等于格子数*格子宽度
	 */
	public static final int CANVAS_WIDTH;

	/**
	 * 真实像素高度,等于格子数*格子高度
	 */
	public static final int CANVAS_HEIGHT;

	/**
	 * 蛇的初始格子长度,最小为2
	 */
	public static final int INIT_LENGTH;

	/**
	 * 蛇的初始速度,单位毫秒/格
	 */
	public static final int SPEED;

	/**
	 * 蛇每次加速或减速的幅度,单位毫秒/格
	 */
	public static final int SPEED_STEP;

	public static final String TITLE_LABEL_TEXT;

	public static final String INFO_LABEL_TEXT;

	private Props() {
	}

	static {
		try (InputStream inputStream = Props.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
			properties.load(inputStream);
		} catch (Exception e) {
			System.out.println("文件未找到");
		}
		Integer temp = null;
		WIDTH = (temp = getIntValue("width")) != null && temp <= 80 && temp >= 10 ? temp : 35;
		HEIGHT = (temp = getIntValue("height")) != null && temp <= 60 && temp >= 10 ? temp : 20;
		INIT_LENGTH = (temp = getIntValue("init_length")) != null && temp > 1 && temp < WIDTH ? temp : 2;
		SPEED = (temp = getIntValue("speed")) != null && temp >= 10 ? temp : 200;
		SPEED_STEP = (temp = getIntValue("speed_step")) != null && temp >= 1 ? temp : 25;

		int defaultCellSize = (temp = getIntValue("cell_size")) != null && temp > 0 && temp <= 100 ? temp : 20;
		CELL_WIDTH = (temp = getIntValue("cell_width")) != null && temp > 0 && temp <= 100 ? temp : defaultCellSize;
		CELL_HEIGHT = (temp = getIntValue("cell_height")) != null && temp > 0 && temp <= 100 ? temp : defaultCellSize;

		CANVAS_WIDTH = WIDTH * CELL_WIDTH;
		CANVAS_HEIGHT = HEIGHT * CELL_HEIGHT;

		String tempStr = null;
		TITLE_LABEL_TEXT = (tempStr = getValue("title")) == null ? "说明:" : tempStr;
		INFO_LABEL_TEXT = (tempStr = getValue("info")) == null ? "方向键控制方向,回车键暂停/继续,page up加速,page down减速" : tempStr;
	}

	/**
	 * 考虑多种情况,没有某个key和value
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
}