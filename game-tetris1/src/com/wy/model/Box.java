package com.wy.model;

/**
 * @apiNote 方格类，是组成块的基本元素，用自己的颜色来表示块的外观
 * @author ParadiseWY
 * @date 2019年10月10日
 */
public class Box {
	private boolean isColor;

	/**
	 * 方格类的构造函数
	 * 
	 * @param isColor 是不是用前景色来为此方格着色， true前景色，false用背景色
	 */
	public Box(boolean isColor) {
		this.isColor = isColor;
	}

	/**
	 * 此方格是不是用前景色表现
	 * 
	 * @return boolean,true用前景色表现，false用背景色表现
	 */
	public boolean isColorBox() {
		return isColor;
	}

	/**
	 * 设置方格的颜色，
	 * 
	 * @param isColor boolean,true用前景色表现，false用背景色表现
	 */
	public void setColor(boolean isColor) {
		this.isColor = isColor;
	}
}