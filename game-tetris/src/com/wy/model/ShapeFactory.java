package com.wy.model;

import java.awt.Color;
import java.util.Random;

import com.wy.config.Props;
import com.wy.listener.ShapeListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 图形工厂
 * @author ParadiseWY
 * @date 2019年10月1日 下午4:10:05
 */
@Getter
@Setter
public class ShapeFactory {

	/**
	 * 可以产生的图形形状
	 */
	protected static int shapes[][][] = new int[][][] { { { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			{ { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
			{ { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
			{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, } },
			{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			{ { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 } }, };

	protected Random random = new Random();

	/**
	 * 图形的默认颜色
	 */
	public static final Color DEFAULT_SHAPE_COLOR = new Color(0x990066);

	/**
	 * 生产的图形的颜色
	 */
	protected Color defaultShapeColor = DEFAULT_SHAPE_COLOR;

	/**
	 * 是否产生彩色图形
	 */
	protected boolean colorfulShape;

	/**
	 * 生产出随机类型的图形,并且把传过来的监听器注册给它
	 * @param shpaeListener
	 * @return
	 */
	public Shape getShape(ShapeListener shapeListener) {
		int type = random.nextInt(shapes.length);
		Shape shape = new Shape(shapes[type], random.nextInt(shapes[type].length));
		shape.setColor(colorfulShape ? Props.getRandomColor() : defaultShapeColor);
		shape.addShapeListener(shapeListener);
		return shape;
	}
}